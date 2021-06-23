package com.kaiyu.controller;

import com.kaiyu.entity.Dorm;
import com.kaiyu.service.DormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/23
 */
@RestController
@RequestMapping("/api/dorm")
public class DormController {

    @Autowired
    private DormService dormService;

    @GetMapping("/all")
    public List<Dorm> getDormAll() {
        return dormService.getDormAll();
    }
}
