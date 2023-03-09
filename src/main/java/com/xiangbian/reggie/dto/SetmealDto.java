package com.xiangbian.reggie.dto;

import com.xiangbian.reggie.entity.Setmeal;
import com.xiangbian.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
