package com.automate.ssh.environment.jdk;

import com.alibaba.fastjson.JSONObject;
import com.automate.entity.EnvironmentEntity;
import com.automate.entity.ServerEntity;
import com.automate.ssh.environment.EnvironmentInfo;
import com.automate.ssh.environment.IEnvironmentHelper;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/30 18:00
 */
public class JdkHelper implements IEnvironmentHelper {


    @Override
    public EnvironmentInfo find(ServerEntity serverEntity) {


        return null;
    }

    @Override
    public EnvironmentEntity readConfig(ServerEntity serverEntity, EnvironmentEntity environmentEntity) {
        return null;
    }

    @Override
    public EnvironmentEntity install(ServerEntity serverEntity, String dir, String version, JSONObject config) {
        return null;
    }

    @Override
    public EnvironmentEntity configure(ServerEntity serverEntity, String dir, JSONObject config) {
        return null;
    }
}
