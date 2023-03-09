package com.xiangbian.reggie.dto;

import com.xiangbian.reggie.entity.Dish;
import com.xiangbian.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
