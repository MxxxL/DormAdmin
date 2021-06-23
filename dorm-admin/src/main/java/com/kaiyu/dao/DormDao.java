package com.kaiyu.dao;

import com.kaiyu.entity.Dorm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/23
 */
@Repository
public interface DormDao {

    List<Dorm> getDormAll();

    List<Dorm> getDormByName(String dormName);

    int saveDorm(Dorm dorm);

    int editDorm(Dorm dorm);

    int deleteDorms(@Param("ids") List<Integer> ids);
}
