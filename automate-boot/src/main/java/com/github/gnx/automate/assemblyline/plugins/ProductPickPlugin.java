package com.github.gnx.automate.assemblyline.plugins;

import com.github.gnx.automate.assemblyline.AssemblyLineEnv;
import com.github.gnx.automate.assemblyline.IAssemblyLinePlugin;
import com.github.gnx.automate.assemblyline.config.ProductPickTask;
import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.common.exception.AlreadyExistsException;
import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.service.IProductService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/3 13:30
 */
@Component
public class ProductPickPlugin implements IAssemblyLinePlugin<ProductPickTask> {

    @Autowired
    private IProductService productService;


    @Override
    public Class<ProductPickTask> getTaskConfigClass() {
        return ProductPickTask.class;
    }

    @Override
    public boolean execute(AssemblyLineEnv env, ProductPickTask taskConfig, IMsgListener listener) throws Exception {

        String filePath = taskConfig.getFilePath();
        listener.appendLine(" == 开始提取产物 == ");
        listener.appendLine(filePath);
        filePath = filePath.replace("${baseDir}", env.getBaseDir()).replace("//", "/");

        listener.appendLine(filePath);

        File tempDir = null;

        try {
            tempDir = new File(System.getProperty("java.io.tmpdir") + File.separator + FastDateFormat.getInstance("yyyyMMddHHmmssSSS").format(new Date()));

            File file = env.getExecConnection().download(filePath, tempDir, listener);

            if (!file.exists()) {
                listener.appendLine("未找到相应的文件: " + file.getAbsolutePath());
                //未找到相应的文件
                return false;
            }

            AssemblyLineLogEntity assemblyLineLogEntity = env.getAssemblyLineLogEntity();

            ProductEntity productEntity = productService.createByBuild(
                    assemblyLineLogEntity.getProjectId(),
                    env.getVersion(),
                    assemblyLineLogEntity.getBranch(),
                    assemblyLineLogEntity.getCommitId(),
                    "后台构建生成", file);

            listener.appendLine("项目ID: " + assemblyLineLogEntity.getProjectId());
            listener.appendLine("分支: " + assemblyLineLogEntity.getBranch());
            listener.appendLine("版本: " + env.getVersion());
            listener.appendLine("commitId: " + StringUtils.trimToEmpty(assemblyLineLogEntity.getCommitId()));
            listener.appendLine("提取前文件地址: " + file.getAbsolutePath());
            listener.appendLine("提取后包地址: " + productEntity.getFilePath());

            env.put("productId", productEntity.getId());
        } catch (AlreadyExistsException e) {
            ProductEntity local = (ProductEntity) e.getSource();
            listener.appendLine("[warn]已存在相同产物, id: " + local.getId());
        } finally {
            if (tempDir != null) {
                FileUtils.deleteDirectory(tempDir);
            }
        }
        return true;
    }

    @Override
    public String[] input() {
        return new String[0];
    }

    @Override
    public String[] export() {
        return new String[]{"productId"};
    }
}
