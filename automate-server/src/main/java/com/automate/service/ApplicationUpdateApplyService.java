package com.automate.service;

import com.alibaba.fastjson.JSONArray;
import com.automate.entity.ApplicationUpdateApplyEntity;
import com.automate.entity.ContainerEntity;
import com.automate.entity.ProjectPackageEntity;
import com.automate.repository.ApplicationUpdateApplyRepository;
import com.automate.repository.ContainerRepository;
import com.automate.repository.ProjectPackageRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/11 22:35
 */
@Service
public class ApplicationUpdateApplyService {

    @Autowired
    private ApplicationUpdateApplyRepository applicationUpdateApplyRepository;

    @Autowired
    private ProjectPackageRepository projectPackageRepository;

    @Autowired
    private ContainerRepository containerRepository;

    public Page<ApplicationUpdateApplyEntity> findAll(Pageable pageable) {
        return applicationUpdateApplyRepository.findAll(pageable);
    }

    /**
     * 发布申请
     */
    public void apply(int packageId, int[] containerIds) throws Exception {

        Optional<ProjectPackageEntity> applicationPackageEntity = projectPackageRepository.findById(packageId);
        if (!applicationPackageEntity.isPresent()) {
            throw new IllegalArgumentException("未找到相应的更新包");
        }
        for (int containerId : containerIds) {
            this.apply(applicationPackageEntity.get(), containerId);
        }

    }

    public void apply(ProjectPackageEntity applicationPackageEntity, int containerId) throws Exception {
        Optional<ContainerEntity> containerEntity = containerRepository.findById(containerId);
        if (!containerEntity.isPresent()) {
            throw new IllegalArgumentException("未找到相应的容器");
        }

        List<String[]> fileMd5List = ContainerService.fileMd5List(containerEntity.get());

        ApplicationUpdateApplyEntity model = new ApplicationUpdateApplyEntity();

        model.setProjectId(applicationPackageEntity.getProjectId());
        model.setProjectPackageId(applicationPackageEntity.getId());
        model.setContainerId(containerId);

        String fileList = JSONArray.toJSONString(fileMd5List);
        model.setContainerFileMd5List(fileList);

        model.setContainerFileSha1(DigestUtils.sha1Hex(fileList));

        //TODO
        model.setCreateUserId(0);
        model.setCreateTime(new Timestamp(System.currentTimeMillis()));
        model.setStatus(ApplicationUpdateApplyEntity.Status.APPLY);
        model.setAuditUserId(0);
        applicationUpdateApplyRepository.save(model);
    }

}
