package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.entity.ProjectEntity;
import com.github.gnx.automate.repository.ProjectRepository;
import com.github.gnx.automate.utils.ColumnUtil;
import com.github.gnx.automate.utils.SFunction;
import com.github.gnx.automate.vo.search.ProjectSearchVO;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/6/28 21:29
 */
@SpringBootTest
class ProjectServiceImplTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void test(){

        ProjectSearchVO condition = new ProjectSearchVO();

        Specification<ProjectEntity> specification = new Specification<ProjectEntity>() {
            public Predicate toPredicate(Root<ProjectEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (condition.getId() != null) {
                    predicates.add(cb.equal(root.get("id"), condition.getId()));
                }

                if (condition.getCreateTimeEnd() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), condition.getCreateTimeEnd()));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

//        Root<InvitationCodeEntity> root = null;
//        CriteriaBuilder cb = null;
//        Predicate a = cb.equal(root.get("appId"), condition.getAppId());


        Pageable pageable = PageRequest.of(1, 20, Sort.by(Sort.Direction.ASC, "createTime"));

        Page<ProjectEntity> dataList = projectRepository.findAll(specification, pageable);
    }

    @Test
    public void test1() {

        a();

        a();


    }

    public void a() {
        this.execute(ProjectEntity::getName);
    }


    public void execute(SFunction<ProjectEntity, ?> function) {

        System.out.println(function.getClass());


        for (Method declaredMethod : function.getClass().getDeclaredMethods()) {
            System.out.println(declaredMethod);
        }

        System.out.println(ColumnUtil.getName(function));

    }

}