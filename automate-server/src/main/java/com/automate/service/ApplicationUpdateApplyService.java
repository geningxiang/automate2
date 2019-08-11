package com.automate.service;

import com.automate.common.utils.FileListSha256Util;
import com.automate.entity.ApplicationEntity;
import com.automate.entity.ApplicationUpdateApplyEntity;
import com.automate.entity.ProjectPackageEntity;
import com.automate.repository.ApplicationRepository;
import com.automate.repository.ApplicationUpdateApplyRepository;
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
    private ApplicationRepository applicationRepository;

    public Page<ApplicationUpdateApplyEntity> findAll(Pageable pageable) {
        return applicationUpdateApplyRepository.findAll(pageable);
    }

    public Optional<ApplicationUpdateApplyEntity> findById(int id) {
        return applicationUpdateApplyRepository.findById(id);
    }

    /**
     * 发布申请
     */
    public void apply(int packageId, int[] applicationIds) throws Exception {

        Optional<ProjectPackageEntity> applicationPackageEntity = projectPackageRepository.findById(packageId);
        if (!applicationPackageEntity.isPresent()) {
            throw new IllegalArgumentException("未找到相应的更新包");
        }
        for (int applicationId : applicationIds) {
            this.apply(applicationPackageEntity.get(), applicationId);
        }

    }

    public void apply(ProjectPackageEntity applicationPackageEntity, int applicationId) throws Exception {
        Optional<ApplicationEntity> containerEntity = applicationRepository.findById(applicationId);
        if (!containerEntity.isPresent()) {
            throw new IllegalArgumentException("未找到相应的容器");
        }


        ApplicationUpdateApplyEntity model = new ApplicationUpdateApplyEntity();

        model.setProjectId(applicationPackageEntity.getProjectId());
        model.setProjectPackageId(applicationPackageEntity.getId());
        model.setApplicationId(applicationId);

        if (applicationPackageEntity.getType() == ProjectPackageEntity.Type.PART.ordinal()) {
            //增量更新  需要读取当前容器源代码的 sh256
            List<String[]> fileSha256List = ApplicationService.fileSha256List(containerEntity.get());
            String fileList = FileListSha256Util.parseToFileListByArray(fileSha256List);
            String sha256 = DigestUtils.sha256Hex(fileList);
            model.setApplicationFileSha256(sha256);
        }

        //TODO
        model.setCreateUserId(0);
        model.setCreateTime(new Timestamp(System.currentTimeMillis()));
        model.setStatus(ApplicationUpdateApplyEntity.Status.APPLY);
        model.setAuditUserId(0);
        applicationUpdateApplyRepository.save(model);
    }

}
