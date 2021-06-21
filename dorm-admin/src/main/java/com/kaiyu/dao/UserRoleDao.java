package com.kaiyu.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author mxxxl
 * @date 2021/6/19
 */
@Repository
public interface UserRoleDao {

    void deleteByUserId(Integer uid);

    Integer addRole(@Param("uid") Integer userId, @Param("rids") Integer[] rids);
}