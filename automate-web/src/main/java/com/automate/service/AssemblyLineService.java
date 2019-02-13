package com.automate.service;

import com.alibaba.fastjson.JSON;
import com.automate.entity.AssemblyLineEntity;
import com.automate.event.handle.IEventHandler;
import com.automate.event.po.SourceCodePushEvent;
import com.automate.repository.AssemblyLineRepository;
import com.automate.task.background.BackgroundAssemblyManager;
import com.automate.task.background.impl.BaseSourceCodeAssembly;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/4 16:23
 */
@Service
public class AssemblyLineService implements IEventHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AssemblyLineRepository assemblyLineRepository;

    @Autowired
    private BackgroundAssemblyManager backgroundAssemblyManager;


    public Iterable<AssemblyLineEntity> findAll() {
        return assemblyLineRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    /**
     * 查询对象
     **/
    public Optional<AssemblyLineEntity> getModel(int id) {
        return assemblyLineRepository.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(AssemblyLineEntity model) {
        assemblyLineRepository.save(model);
    }

    public List<AssemblyLineEntity> getAllBySourceCodeId(int sourceCodeId){
        return assemblyLineRepository.getAllBySourceCodeId(sourceCodeId);
    }

    public List<AssemblyLineEntity> getAllBySourceCodeIdWidthAutoTrigger(int sourceCodeId){
        return assemblyLineRepository.getAllBySourceCodeIdAndAutoTrigger(sourceCodeId, true);
    }


    @Subscribe
    public void sourceCodePushed(SourceCodePushEvent sourceCodePushEvent) {
        logger.debug("pushed:{}", JSON.toJSONString(sourceCodePushEvent));

        List<AssemblyLineEntity> list = this.getAllBySourceCodeIdWidthAutoTrigger(sourceCodePushEvent.getSourceCodeId());
        if(list.size() > 0){
            for (AssemblyLineEntity assemblyLineEntity : list) {


                if(StringUtils.isNotBlank(assemblyLineEntity.getBranches()) && isMatch(assemblyLineEntity.getBranches(), sourceCodePushEvent.getBranchName())){
                    logger.debug("触发自动化流水线:id={}" , assemblyLineEntity.getId());
                    try {
                        backgroundAssemblyManager.execute(BaseSourceCodeAssembly.create(assemblyLineEntity, sourceCodePushEvent.getBranchName(), sourceCodePushEvent.getCommitId()));
                    } catch (Exception e) {
                        logger.error("发布后台任务失败", e);
                    }
                }
            }
        }
    }

    private boolean isMatch(String pattern, String branchName){
        try{
            return Pattern.matches(pattern, branchName);
        }catch (Exception e){
            logger.error("匹配分支的正则表达式格式错误", e);
        }
        return false;
    }
}
