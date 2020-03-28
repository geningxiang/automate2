package com.github.gnx.automate.repository;

import com.github.gnx.automate.entity.ContainerEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 23:50
 */
@Repository
public interface ContainerRepository extends PagingAndSortingRepository<ContainerEntity, Integer>, JpaSpecificationExecutor<ContainerEntity> {

    List<ContainerEntity> getAllByProjectIdOrderById(int projectId);

}
