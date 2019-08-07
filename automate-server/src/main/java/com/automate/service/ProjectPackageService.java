package com.automate.service;

import com.automate.common.SystemConfig;
import com.automate.common.utils.FileListSha1Util;
import com.automate.common.utils.ZipUtil;
import com.automate.entity.FileListShaEntity;
import com.automate.entity.ProjectPackageEntity;
import com.automate.repository.FileListShaRepository;
import com.automate.repository.ProjectPackageRepository;
import com.automate.vo.PathSha1Info;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/5 11:09
 */
@Service
public class ProjectPackageService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectPackageRepository projectPackageRepository;

    @Autowired
    private FileListShaRepository fileListShaRepository;

    public Optional<ProjectPackageEntity> findById(int id) {
        return projectPackageRepository.findById(id);
    }


    public Iterable<ProjectPackageEntity> findAll() {
        return projectPackageRepository.findAll();
    }

    public Page<ProjectPackageEntity> findAll(Pageable pageable) {
        return projectPackageRepository.findAll(pageable);
    }

    /**
     * 根据内部具体文件的sha1生成的总的sha1
     * @param sha1
     * @return
     */
    public ProjectPackageEntity getFirstBySha1OrderByIdDesc(String sha1) {
        return projectPackageRepository.getFirstBySha1OrderByIdDesc(sha1);
    }


    /**
     * 手动上传  创建项目文件包
     * @param projectId
     * @param version
     * @param branch
     * @param commitId
     * @param remark
     * @param fileData
     * @param type
     * @param userId
     * @return
     * @throws IOException
     */
    public ProjectPackageEntity createByUpload(int projectId, String version, String branch, String commitId, String remark,  CommonsMultipartFile fileData, ProjectPackageEntity.Type type, int userId) throws IOException {
        String fileType = fileData.getOriginalFilename().substring(fileData.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        if ("war".equals(fileType) || "zip".equals(fileType)) {
            //创建一个文件 用来存产出物
            File destFile = new File(buildFilePath(projectId, fileType));
            //转存文件
            fileData.transferTo(destFile);
            return create(projectId, version, branch, commitId, remark, destFile, type, userId);
        } else {
            throw new IllegalArgumentException("不支持的文件后缀,当前仅支持war、zip");
        }
    }

    /**
     * 自动构建 创建项目文件包
     * @param projectId
     * @param version
     * @param branch
     * @param commitId
     * @param remark
     * @param file
     * @return
     * @throws IOException
     */
    public ProjectPackageEntity createByBuild(int projectId, String version, String branch, String commitId, String remark, File file) throws IOException {
        String fileType;
        File destFile;
        if (file.isDirectory()) {
            //文件夹 打包成zip
            fileType = "zip";
            destFile = new File(buildFilePath(projectId, fileType));
            ZipUtil.compress(file, destFile);
        } else {
            //非文件夹  复制文件
            fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            destFile = new File(buildFilePath(projectId, fileType));
            FileUtils.copyFile(file, destFile);
        }
        return create(projectId, version, branch, commitId, remark, destFile, ProjectPackageEntity.Type.WHOLE, 0);
    }

    /**
     * 保存更新包
     * @param projectId
     * @param version
     * @param branch
     * @param commitId
     * @param remark
     * @param file
     * @param type
     * @param userId
     * @return
     * @throws IOException
     */
    private ProjectPackageEntity create(int projectId, String version, String branch, String commitId, String remark, File file, ProjectPackageEntity.Type type, int userId) throws IOException {
        //读取文件列表
        List<PathSha1Info> list = FileListSha1Util.list(file);
        String fileList = FileListSha1Util.parseToFileList(list);
        String sha1 = DigestUtils.sha1Hex(fileList);

        ProjectPackageEntity local = this.getFirstBySha1OrderByIdDesc( sha1);
        if(local != null){
            file.delete();
            throw new IllegalArgumentException("sha1已存在:"+ sha1);
        }

        //保存 sha1 fileList关系
        this.saveSha1AndFileList(sha1, fileList);

        ProjectPackageEntity projectPackageEntity = buildProjectPackageEntity(projectId, version, branch, commitId, remark, type, userId);
        projectPackageEntity.setFilePath(file.getAbsolutePath());
        projectPackageEntity.setSha1(sha1);
        String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        projectPackageEntity.setSuffix(fileType);
        projectPackageRepository.save(projectPackageEntity);
        logger.info("保存更新包,projectId={}, filePath={}", projectId, projectPackageEntity.getFilePath());
        return projectPackageEntity;
    }

    private void saveSha1AndFileList(String sha1, String fileList){
        FileListShaEntity fileListShaEntity = new FileListShaEntity();
        fileListShaEntity.setSha1(sha1);
        fileListShaEntity.setFileList(fileList);
        fileListShaEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        fileListShaRepository.save(fileListShaEntity);
    }


    private ProjectPackageEntity buildProjectPackageEntity(int projectId, String version, String branch, String commitId, String remark, ProjectPackageEntity.Type type, int userId) {
        ProjectPackageEntity projectPackageEntity = new ProjectPackageEntity();
        projectPackageEntity.setType(type);
        projectPackageEntity.setProjectId(projectId);
        projectPackageEntity.setBranch(StringUtils.trimToEmpty(branch));
        projectPackageEntity.setCommitId(StringUtils.trimToEmpty(commitId));
        projectPackageEntity.setVersion(StringUtils.trimToEmpty(version));
        projectPackageEntity.setRemark(StringUtils.trimToEmpty(remark));
        projectPackageEntity.setUserId(userId);
        projectPackageEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return projectPackageEntity;
    }

    private String buildFilePath(int sourceCodeId, String fileType) {
        //复制文件
        String dirPath = SystemConfig.getPackageDir(sourceCodeId);
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        StringBuilder s = new StringBuilder(128);
        s.append(dirPath);
        s.append(FastDateFormat.getInstance("yyyyMMddHHmmssSSS").format(getCurrentTimeMillis()));
        s.append(".").append(fileType);
        return s.toString();
    }

    private volatile long currentTimeMillis;

    /**
     * 确保单机情况下不会有重复
     *
     * @return
     */
    public synchronized long getCurrentTimeMillis() {
        long now = System.currentTimeMillis();
        if (now == currentTimeMillis) {
            try {
                Thread.sleep(1);
                now = System.currentTimeMillis();
            } catch (InterruptedException e) {
                now++;
            }

        }
        currentTimeMillis = now;
        return now;
    }
}
