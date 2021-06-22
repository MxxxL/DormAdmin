package com.kaiyu.dao;

import com.kaiyu.entity.Role;
import com.kaiyu.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/19
 */
@Repository
public interface UserDao {

    int saveUser(String username, String password);

    User findByUsername(String username);

    List<Role> getUserRolesById(Integer userId);
}