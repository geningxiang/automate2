package com.github.gnx.automate.repository;

import com.github.gnx.automate.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/3 20:14
 */
@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Integer>, JpaSpecificationExecutor<ProductEntity> {


    ProductEntity getFirstBySha256OrderByIdDesc(String sha256);

}
