package com.kaiyu.dao;

import com.kaiyu.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/21
 */
@Repository
public interface MenuDao {

    List<Menu> getAllMenusWithRole();

    List<Menu> getMenusByUserId(Integer userId);
}