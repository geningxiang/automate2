package com.automate.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.automate.common.Charsets;
import com.automate.common.ResponseEntity;
import com.automate.controller.BaseController;
import com.automate.entity.FileListShaEntity;
import com.automate.entity.ProjectPackageEntity;
import com.automate.service.FileListShaService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @RequestMapping(value = "/fileListSha/down/txt/{sha1}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void list(@PathVariable("sha1") String sha1,  HttpServletResponse response) throws IOException {
        if(StringUtils.isBlank(sha1)){
            response.sendError(HttpStatus.BAD_REQUEST.value());
            return;
        }

        Optional<FileListShaEntity> model = fileListShaService.findById(sha1);
        if(!model.isPresent()){
            response.sendError(HttpStatus.NOT_FOUND.value());
            return;
        }

        JSONArray jsonArray = JSONArray.parseArray(model.get().getFileList());
        JSONArray item;
        StringBuilder sb = new StringBuilder(102400);
        for (int i = 0; i < jsonArray.size(); i++) {
            item = jsonArray.getJSONArray(i);
            if(i > 0){
                sb.append("\n");
            }
            sb.append(item.getString(0)).append("\t").append(item.getString(1));
        }
        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-disposition", "attachment;filename=" + sha1 + ".txt");
        IOUtils.write(sb.toString(), response.getOutputStream(), Charsets.UTF_8);

    }
}
