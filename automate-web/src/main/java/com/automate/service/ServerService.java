package com.automate.service;

import com.automate.common.utils.SpringContextUtil;
import com.automate.entity.ServerEntity;
import com.automate.repository.ServerRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/17 23:49
 */
@Service
public class ServerService {


    private static Cache<Integer, ServerEntity> serverLocalCache = Caffeine.newBuilder()
            .initialCapacity(64)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();


    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private EntityManager entityManager;

    public Iterable<ServerEntity> getList() {
        return serverRepository.findAll(Sort.by("id"));
    }

    /**
     * 查询对象
     **/
    public Optional<ServerEntity> getModel(int id) {
        return serverRepository.findById(id);
    }

    private static ServerEntity getModelStatic(int id) {
        Optional<ServerEntity> model = SpringContextUtil.getBean("serverService", ServerService.class).getModel(id);
        return model.isPresent() ? model.get() : null;
    }

    public static ServerEntity getModelByCache(Integer id) {
        if(id == null || id <= 0){
            return null;
        }
        return serverLocalCache.get(id, ServerService::getModelStatic);
    }

    /**
     * 添加对象
     **/
    public void save(ServerEntity model) {
        serverRepository.save(model);

        serverLocalCache.put(model.getId(), model);
    }


    /**
     * 删除对象
     **/
    public void deleteById(int id) {
        serverRepository.deleteById(id);

        serverLocalCache.invalidate(id);
    }

}
