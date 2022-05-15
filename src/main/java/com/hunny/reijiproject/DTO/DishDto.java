package com.hunny.reijiproject.DTO;

import com.hunny.reijiproject.entity.Dish;
import com.hunny.reijiproject.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors ;

    private String categoryName;

    private Integer copies;

    @Override
    public String toString() {

        return "DishDto{" +
                "flavors=" + flavors +
                ", categoryName='" + categoryName + '\'' +
                ", copies=" + copies +
                '}'+super.toString();
    }

}
