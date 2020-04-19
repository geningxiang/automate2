package com.github.gnx.automate.assemblyline.plugins;

import com.github.gnx.automate.assemblyline.AssemblyLineEnv;
import com.github.gnx.automate.assemblyline.IAssemblyLinePlugin;
import com.github.gnx.automate.assemblyline.IAssemblyLineProgressListener;
import com.github.gnx.automate.assemblyline.config.ApplicationUpdateTask;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/3 19:42
 */
public class ApplicationUpdatePlugin implements IAssemblyLinePlugin<ApplicationUpdateTask> {


    @Autowired
    private IProductService productService;

    @Override
    public Class<ApplicationUpdateTask> getTaskConfigClass() {
        return ApplicationUpdateTask.class;
    }

    @Override
    public boolean execute(AssemblyLineEnv env, ApplicationUpdateTask taskConfig, IAssemblyLineProgressListener listener) throws Exception {


        Integer productId = (Integer) env.get("productId");

        Assert.isNull(productId, "productId is null");

        Optional<ProductEntity> productEntityOptional = productService.findById(productId);

        if(!productEntityOptional.isPresent()){
            throw new IllegalArgumentException("productEntity is not found");
        }




        return false;
    }

    @Override
    public String[] input() {
        return new String[]{"productId"};
    }

    @Override
    public String[] export() {
        return new String[0];
    }

}
