package com.hunny.reijiproject;

import com.hunny.reijiproject.common.R;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReiJiProjectApplicationTests {

    @Test
    void contextLoads() {

        R r= R.success("nihao");
        System.out.println(r.getData());
    }

}
