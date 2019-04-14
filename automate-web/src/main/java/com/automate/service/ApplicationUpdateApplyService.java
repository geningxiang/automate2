package com.automate.service;

import com.alibaba.fastjson.JSONArray;
import com.automate.entity.ApplicationPackageEntity;
import com.automate.entity.ApplicationUpdateApplyEntity;
import com.automate.entity.ContainerEntity;
import com.automate.repository.ApplicationPackageRepository;
import com.automate.repository.ApplicationUpdateApplyRepository;
import com.automate.repository.ContainerRepository;
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
    private ApplicationPackageRepository applicationPackageRepository;

    @Autowired
    private ContainerRepository containerRepository;

    public Page<ApplicationUpdateApplyEntity> findAll(Pageable pageable) {
        return applicationUpdateApplyRepository.findAll(pageable);
    }

    /**
     * 发布申请
     */
    public void apply(int packageId, int[] containerIds) throws Exception {

        Optional<ApplicationPackageEntity> applicationPackageEntity = applicationPackageRepository.findById(packageId);
        if (!applicationPackageEntity.isPresent()) {
            throw new IllegalArgumentException("未找到相应的更新包");
        }
        for (int containerId : containerIds) {
            this.apply(applicationPackageEntity.get(), containerId);
        }

    }

    public void apply(ApplicationPackageEntity applicationPackageEntity, int containerId) throws Exception {
        Optional<ContainerEntity> containerEntity = containerRepository.findById(containerId);
        if (!containerEntity.isPresent()) {
            throw new IllegalArgumentException("未找到相应的容器");
        }

        List<String[]> fileMd5List = ContainerService.fileMd5List(containerEntity.get());

        ApplicationUpdateApplyEntity model = new ApplicationUpdateApplyEntity();

        model.setSourceCodeId(applicationPackageEntity.getSourceCodeId());
        model.setPackageId(applicationPackageEntity.getId());
        model.setContainerId(containerId);

        String fileTree = JSONArray.toJSONString(fileMd5List);
        model.setContainerFileTree(fileTree);

        model.setContainerFilesSha1(DigestUtils.sha1Hex(fileTree));

        //TODO
        model.setCreateAdminId(0);
        model.setCreateTime(new Timestamp(System.currentTimeMillis()));
        model.setStatus(ApplicationUpdateApplyEntity.Status.APPLY);
        model.setAuditAdminId(0);
        applicationUpdateApplyRepository.save(model);
    }

}
