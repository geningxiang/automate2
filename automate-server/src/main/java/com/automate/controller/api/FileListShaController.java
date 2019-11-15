package com.automate.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.automate.common.Charsets;
import com.automate.common.ResponseEntity;
import com.automate.common.utils.FileListSha256Util;
import com.automate.controller.BaseController;
import com.automate.entity.ApplicationEntity;
import com.automate.entity.FileListShaEntity;
import com.automate.service.ApplicationService;
import com.automate.service.FileListShaService;
import com.automate.vo.FileListShaSearchVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/8 0:28
 */
@RestController
@RequestMapping("/api")
public class FileListShaController extends BaseController {

    @Autowired
    private FileListShaService fileListShaService;
    
    @Autowired
    private ApplicationService applicationService;

    /**
     * 根据sha256 查找文件列表
     * @param sha256
     * @return
     * {
     *   "data": {
     *     "sha256": "080c1cbbcd9662e4ddd6c8826806dab79cece58edc12c781f982bdf0b158172d",
     *     "fileList": [
     *       [
     *         "WEB-INF/classes/com/automate/build/IBuildHelper.class",
     *         "4a056dd2ed3d981bcde3226a1fc8acb4f48f350b28100387114b4cde345a3422"
     *       ],
     *       [
     *         "WEB-INF/classes/com/automate/build/MyCat.class",
     *         "425743e33ec7799109be0c3a7183f26cf89f81f02c51a62247530f10d7b64f86"
     *       ],...
     *     ]
     *   },
     *   "msg": "",
     *   "status": 200,
     *   "timestamp": 1565614463109
     * }
     */
    @RequestMapping(value = "/fileListSha/{sha256}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<JSONObject> fileListSha(@PathVariable("sha256") String sha256) throws IOException {
        if (StringUtils.isBlank(sha256)) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "sha256 is required");
        }
        Optional<FileListShaEntity> model = fileListShaService.findById(sha256);
        if (!model.isPresent()) {
            return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应的sha1文件树");
        }
        JSONObject data = new JSONObject(2);
        data.put("sha256", sha256);
        data.put("fileList", JSONArray.parseArray(model.get().getFileList()));
        return ResponseEntity.ok(data);

    }

    /**
     * 查询你想要的的 文件sha256列表
     * @param keys
     * 例如 080c1cbbcd9662e4ddd6c8826806dab79cece58edc12c781f982bdf0b158172d,applicationId:1
     * applicationId:1 代表查询应用【1】的当前文件sha256列表
     * @return
     */
    @RequestMapping(value = "/fileListSha/search", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity fileListSearch(String[] keys){
        FileListShaSearchVO[] data;
        if(keys != null && keys.length > 0){
            data = new FileListShaSearchVO[keys.length];
            String key;
            FileListShaSearchVO item;
            for (int i = 0; i < keys.length; i++) {
                key = keys[i];
                if(key.startsWith("applicationId:")){
                    //TODO 权限判断
                    Optional<ApplicationEntity> applicationEntity = applicationService.findById(NumberUtils.toInt(key.substring(14)));
                    if(applicationEntity.isPresent()){
                        try {
                            item = new FileListShaSearchVO(key);
                            List<String[]> fileSha256List = ApplicationService.fileSha256List(applicationEntity.get());
                            String fileList = FileListSha256Util.parseToFileListByArray(fileSha256List);
                            String sha256 = DigestUtils.sha256Hex(fileList);
                            item.setFileList(fileSha256List);
                            item.setSha256(sha256);
                            data[i] = item;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Optional<FileListShaEntity> model = fileListShaService.findById(key);
                    if (model.isPresent()) {
                        item = new FileListShaSearchVO(key);
                        item.setFileList(JSONArray.parseArray(model.get().getFileList()));
                        item.setSha256(model.get().getSha256());
                        data[i] = item;
                    }
                }
            }
        } else {
            data = new FileListShaSearchVO[0];
        }
        return ResponseEntity.ok(data);
    }

    /**
     * 根据sha256 下载文件列表
     * @param sha256
     * @param response
     * @return txt文本文件
     * @throws IOException
     */
    @RequestMapping(value = "/download/fileListSha/txt/{sha256}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void txt(@PathVariable("sha256") String sha256, HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(sha256)) {
            response.sendError(HttpStatus.BAD_REQUEST.value());
            return;
        }

        Optional<FileListShaEntity> model = fileListShaService.findById(sha256);
        if (!model.isPresent()) {
            response.sendError(HttpStatus.NOT_FOUND.value());
            return;
        }

        JSONArray jsonArray = JSONArray.parseArray(model.get().getFileList());
        JSONArray item;
        StringBuilder sb = new StringBuilder(102400);
        for (int i = 0; i < jsonArray.size(); i++) {
            item = jsonArray.getJSONArray(i);
            if (i > 0) {
                sb.append("\n");
            }
            sb.append(item.getString(0)).append("\t").append(item.getString(1));
        }
        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-disposition", "attachment;filename=" + sha256 + ".txt");
        IOUtils.write(sb.toString(), response.getOutputStream(), Charsets.UTF_8);
    }

}
