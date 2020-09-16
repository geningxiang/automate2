package com.automate.ssh.environment;

import com.alibaba.fastjson.JSONObject;
import com.automate.entity.EnvironmentEntity;
import com.automate.entity.ServerEntity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 各环境的助手
 *
 * @author: genx
 * @date: 2019/1/2 22:32
 */
public interface IEnvironmentHelper {

    /**
     * 查找 是否已安装
     *
     * @param serverEntity
     * @return
     */
    EnvironmentInfo find(ServerEntity serverEntity);

    /**
     * 重新读取配置信息
     *
     * @param serverEntity
     * @param environmentEntity
     * @return
     */
    EnvironmentEntity readConfig(ServerEntity serverEntity, EnvironmentEntity environmentEntity);


    /**
     * 安装 环境
     *
     * @param serverEntity
     * @param dir
     * @param version
     * @param config
     * @return
     */
    EnvironmentEntity install(ServerEntity serverEntity, String dir, String version, JSONObject config);


    /**
     * 修改配置
     * @param serverEntity
     * @param dir
     * @param config
     * @return
     */
    EnvironmentEntity configure(ServerEntity serverEntity, String dir, JSONObject config);


}
