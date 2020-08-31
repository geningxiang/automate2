package com.github.gnx.automate.controller.api;

import com.github.gnx.automate.cache.IEntityCache;
import com.github.gnx.automate.common.CurrentUser;
import com.github.gnx.automate.common.ResponseEntity;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/18 20:48
 */
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final IProductService productService;

    private final IEntityCache entityCache;

    public ProductController(IProductService productService, IEntityCache entityCache) {
        this.productService = productService;
        this.entityCache = entityCache;
    }

    /**
     * 产物列表(分页)
     * @return
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<Page<ProductEntity>> productList(
            CurrentUser currentUser,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        //倒序排序
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Page<ProductEntity> result = productService.queryPage(PageRequest.of(page - 1, pageSize, sort));

        return ResponseEntity.ok(new PageImpl(result.getContent()
                .stream().map(entityCache::parse).collect(Collectors.toList()),
                result.getPageable(),
                result.getTotalElements()));
    }
}
