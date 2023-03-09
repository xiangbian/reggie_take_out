package com.xiangbian.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiangbian.reggie.common.R;
import com.xiangbian.reggie.entity.Category;
import com.xiangbian.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 分类分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> categoryPage(int page ,int pageSize) {
        log.info("page = {} ; pageSize = {} ;", page, pageSize);
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();

        //添加排序条件
        //queryWrapper.orderByDesc(Category::getSort);
        queryWrapper.orderByAsc(Category::getSort);

        //执行查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> addCategory(@RequestBody Category category) {
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 根据id删除分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteCategory(Long ids) {
        log.info("删除分类，id为：" + ids);
        //categoryService.removeById(ids);
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    /**
     * 根据id修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> updateCategory(@RequestBody Category category) {
        log.info("修改分类信息：" + category + category.getId());
        categoryService.updateById(category);
        return R.success("更新成功");
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @GetMapping("/list")
    public R<List> categoryList(int type) {

        //构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();

        //添加排序条件
        //queryWrapper.orderByDesc(Category::getSort);
         queryWrapper.eq(Category::getType,type);

        //执行查询
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
