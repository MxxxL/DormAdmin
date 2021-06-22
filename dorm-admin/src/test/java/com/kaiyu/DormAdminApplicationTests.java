package com.kaiyu;

import com.kaiyu.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DormAdminApplicationTests {

    @Autowired
    UserServiceImpl userService;

    @Test
    void contextLoads() {
        int result = userService.userReg("test", "123");
        Assertions.assertEquals(1, result);
    }

}
