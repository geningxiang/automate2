package com.github.gnx.automate.controller.api;

import com.alibaba.fastjson.JSON;
import com.github.gnx.automate.common.CurrentUser;
import com.github.gnx.automate.common.ResponseEntity;
import com.github.gnx.automate.common.file.FileCompareResult;
import com.github.gnx.automate.common.file.FileInfo;
import com.github.gnx.automate.common.file.FileListComparer;
import com.github.gnx.automate.field.req.FileCompareField;
import com.github.gnx.automate.service.IContainerService;
import com.github.gnx.automate.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/8/2 10:40
 */
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ToolsController {


    @Autowired
    private IProductService productService;

    @Autowired
    private IContainerService containerService;

    @RequestMapping(value = "/tools/file/compare", method = RequestMethod.POST)
    public ResponseEntity<FileCompareResult> fileCompare(CurrentUser currentUser, @RequestBody List<FileCompareField> fileCompareFieldList) throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println(JSON.toJSONString(fileCompareFieldList));

        int sourceSize = fileCompareFieldList.size();

        List<FileInfo>[] fileLists = new List[sourceSize];
        Future<List<FileInfo>>[] futures = new Future[sourceSize];
        for (int i = 0; i < sourceSize; i++) {
            FileCompareField compareField = fileCompareFieldList.get(i);
            if (compareField.getType() == 1) {
                fileLists[i] = productService.getFileInfoListById(compareField.getSourceId());
            } else if (compareField.getType() == 2) {
                futures[i] = this.containerService.getFileInfoList(compareField.getSourceId());
            }
        }

        for (int i = 0; i < sourceSize; i++) {
            if(futures[i] != null){
                //超时 10秒
                fileLists[i] = futures[i].get(30, TimeUnit.SECONDS);
            }
        }

        FileCompareResult result = FileListComparer.compare(true, fileLists);

        return ResponseEntity.ok(result);
    }
}
