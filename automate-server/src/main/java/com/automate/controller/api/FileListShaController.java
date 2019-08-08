package com.automate.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.automate.common.Charsets;
import com.automate.common.ResponseEntity;
import com.automate.controller.BaseController;
import com.automate.entity.FileListShaEntity;
import com.automate.service.FileListShaService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
