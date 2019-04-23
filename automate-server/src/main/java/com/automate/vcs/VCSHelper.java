package com.automate.vcs;

import com.automate.contants.VcsType;
import com.automate.vcs.git.GitHelper;
import com.automate.vcs.svn.SvnHelper;
import org.springframework.util.Assert;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/16 16:15
 */
public class VCSHelper {

    public static IVCSHelper create(IVCSRepository cvsRepository) {
        Assert.notNull(cvsRepository, "cvsRepository is required");
        Integer cvsType = cvsRepository.getVcsType();
        if (cvsType == null) {

        } else if (cvsType == VcsType.GIT.ordinal()) {
            return new GitHelper(cvsRepository);
        } else if (cvsType == VcsType.SVN.ordinal()) {
            return new SvnHelper(cvsRepository);
        }
        throw new RuntimeException("暂不支持该版本控制类型:" + cvsType);
    }
}
