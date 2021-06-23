package com.kaiyu.service;

import com.kaiyu.dao.DormDao;
import com.kaiyu.entity.Dorm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/23
 */
@Service
public class DormService {

    @Autowired
    private DormDao dormDao;

    public List<Dorm> getDormAll() {
        return dormDao.getDormAll();
    }
}
