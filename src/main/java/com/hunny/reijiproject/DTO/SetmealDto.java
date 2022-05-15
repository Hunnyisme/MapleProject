package com.hunny.reijiproject.DTO;

import com.hunny.reijiproject.entity.Setmeal;
import com.hunny.reijiproject.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;

    @Override
    public String toString() {
        return "SetmealDto{" +
                "setmealDishes=" + setmealDishes +
                ", categoryName='" + categoryName + '\'' +
                '}'+" "+super.toString();
    }
}
