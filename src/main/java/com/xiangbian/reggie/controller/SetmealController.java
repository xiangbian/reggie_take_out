package com.xiangbian.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiangbian.reggie.common.CustomException;
import com.xiangbian.reggie.common.R;
import com.xiangbian.reggie.dto.SetmealDto;
import com.xiangbian.reggie.entity.Category;
import com.xiangbian.reggie.entity.Setmeal;
import com.xiangbian.reggie.service.CategoryService;
import com.xiangbian.reggie.service.SetmealDishService;
import com.xiangbian.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    @Autowired
    SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page> selectPage(int page, int pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal>  queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据name进行like模糊查询
        queryWrapper.like(name != null, Setmeal :: getName, name);

        //添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(setmealPage,queryWrapper);

        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");
        List<Setmeal> records = setmealPage.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto1 = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item, setmealDto1);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                //分类名称
                String categoryName = category.getName();
                setmealDto1.setCategoryName(categoryName);
            }
            return setmealDto1;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("接收到前端setmealDto参数" + setmealDto.toString());
        setmealService.saveWithDish(setmealDto);
        return R.success("新增成功");
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteSetmeal(@RequestParam List<Long> ids){
        if (ids.isEmpty()) {
            throw  new CustomException("请先勾选套餐");
        }
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@RequestParam List<Long> ids,@PathVariable int status) {
        log.info("ids-----" + ids + "状态---------" + status);

        LambdaUpdateWrapper<Setmeal> setmealLambdaUpdateWrapper = new LambdaUpdateWrapper<>();


        setmealLambdaUpdateWrapper.in(Setmeal::getId,ids).set(Setmeal::getStatus, status);
        setmealService.update(setmealLambdaUpdateWrapper);

        return R.success("状态修改成功");
    }
}
