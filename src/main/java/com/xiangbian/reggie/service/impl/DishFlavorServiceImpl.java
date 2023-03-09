package com.xiangbian.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiangbian.reggie.entity.DishFlavor;
import com.xiangbian.reggie.mapper.DishFlavorMapper;
import com.xiangbian.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
