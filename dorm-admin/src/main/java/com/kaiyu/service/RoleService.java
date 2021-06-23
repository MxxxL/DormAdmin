package com.kaiyu.service;

import com.kaiyu.dao.RoleDao;
import com.kaiyu.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/24
 */
@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    public int addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleDao.insert(role);
    }

    public int deleteRoleById(Integer rid) {
        return roleDao.deleteByPrimaryKey(rid);
    }
}
