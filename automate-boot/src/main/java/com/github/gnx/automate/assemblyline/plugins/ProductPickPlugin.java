package com.github.gnx.automate.assemblyline.plugins;

import com.github.gnx.automate.assemblyline.AssemblyLineEnv;
import com.github.gnx.automate.assemblyline.IAssemblyLinePlugin;
import com.github.gnx.automate.assemblyline.IAssemblyLineProgressListener;
import com.github.gnx.automate.assemblyline.config.ProductPickTask;
import com.github.gnx.automate.common.SystemUtil;
import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

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
    public boolean execute(AssemblyLineEnv env, ProductPickTask taskConfig, IAssemblyLineProgressListener listener) throws Exception {

        String filePath = taskConfig.getFilePath();
        listener.onLine("## 开始提取包文件 ##");
        listener.onLine(filePath);
        filePath = filePath.replace("${baseDir}", SystemUtil.getProjectSourceCodeDir(env.getProjectEntity()).getAbsolutePath());
        listener.onLine(filePath);
        File file = new File(filePath);
        if(!file.exists()){
            listener.onLine("未找到相应的文件: " + file.getAbsolutePath());
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

        listener.onLine("项目ID: " + assemblyLineLogEntity.getProjectId());
        listener.onLine("版本: " + env.getVersion());
        listener.onLine("分支: " + assemblyLineLogEntity.getBranch());
        listener.onLine("commitId: " + assemblyLineLogEntity.getCommitId());
        listener.onLine("提取前文件地址: " + file.getAbsolutePath());
        listener.onLine("提取后包地址: " + productEntity.getFilePath());

        env.put("productId", productEntity.getId());

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
