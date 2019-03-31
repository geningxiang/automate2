package com.automate.controller.api;

import com.automate.build.maven.MavenUtil;
import com.automate.common.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/31 16:45
 */
@RestController
@RequestMapping("/api/mvn")
public class MvnController {

    @RequestMapping(value = "/repository", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<TreeMap<String, Map<String, Map<String, Long>>>> repository() {
        return ResponseEntity.ok(MavenUtil.repositoryTree());

    }
}
