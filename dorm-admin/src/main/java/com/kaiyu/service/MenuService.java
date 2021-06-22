package com.kaiyu.service;

import com.kaiyu.dao.MenuDao;
import com.kaiyu.dao.MenuRoleDao;
import com.kaiyu.entity.Menu;
import com.kaiyu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/21
 */
@Service
@CacheConfig(cacheNames = "menus_cache")
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private MenuRoleDao menuRoleDao;

    @Cacheable
    public List<Menu> getAllMenusWithRole() {
        return menuDao.getAllMenusWithRole();
    }

    public List<Menu> getMenusByUserId() {
        return menuDao.getMenusByUserId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }
}
