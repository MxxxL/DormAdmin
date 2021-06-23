package com.kaiyu.dao;

import com.kaiyu.entity.Dorm;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/23
 */
@Repository
public interface DormDao {

    List<Dorm> getDormAll();
}
