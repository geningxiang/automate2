package com.automate.vcs;

import com.automate.entity.SourceCodeEntity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/26 23:25
 */
public interface IVCSRepository {

    /**
     * 数据库中的代码仓库ID
     * @return
     */
    default Integer getId(){
        return 0;
    }

    /**
     * 获取版本控制类型
     * @see com.automate.entity.SourceCodeEntity.VcsType
     * @return
     */
    default Integer getVcsType(){
        return SourceCodeEntity.VcsType.GIT.ordinal();
    }

    /**
     * 获取本地仓库文件夹
     * @return
     */
    default String getLocalDir(){
        return null;
    }

    /**
     * 远程仓库地址
     * @return
     */
    String getRemoteUrl();

    /**
     * 版本控制用户名
     * @return
     */
    default String getUserName(){
        return "";
    }

    /**
     * 版本控制密码
     * @return
     */
    default String getPassWord(){
        return "";
    }
}
