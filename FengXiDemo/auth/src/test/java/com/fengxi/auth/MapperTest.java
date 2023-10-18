package com.fengxi.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: com.fengxi.auth.MapperTest
 * @projectName demo
 * @date 2023/1/22 0:12:47
 */
@SpringBootTest(classes = AuthApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MapperTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void test() {
//        String admin = passwordEncoder.encode("admin");
//        String admin2 = passwordEncoder.encode("admin");
//        System.out.println(admin);
//        System.out.println(admin2);
        System.out.println(passwordEncoder.matches("a1dmin",
                "$2a$10$yPFX5RL2judKV06R7bdi3.oVspIF5KZWyzHC4vjhHZFkbg0bwSYcO"));
    }
}
