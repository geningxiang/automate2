package com.automate.repository;

import com.automate.entity.ServerEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/16 22:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class ServerRepositoryTest {
    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void findAll() {
        Iterable<ServerEntity> list = serverRepository.findAll();
        for (ServerEntity serverEntity : list) {
            System.out.println(serverEntity.toJson());
        }
    }

    @Test
    public void test(){

        Query query = entityManager.createQuery( "select s from ServerEntity s where s.id = :id");
        query.setParameter("id", 1);
        List<ServerEntity> list = query.getResultList();
        for (ServerEntity serverEntity : list) {
            System.out.println(serverEntity.toJson());
        }
    }

}