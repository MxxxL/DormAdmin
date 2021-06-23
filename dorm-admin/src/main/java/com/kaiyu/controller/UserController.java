package com.kaiyu.controller;

import com.kaiyu.entity.Role;
import com.kaiyu.entity.User;
import com.kaiyu.model.ResponseMsg;
import com.kaiyu.service.RoleService;
import com.kaiyu.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author mxxxl
 * @date 2021/6/23
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/info")
    public User getCurrentUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

    @GetMapping("/list")
    public List<User> getUsersByName(String name) {
        return userService.getUserByName(name);
    }

    @PostMapping("/add")
    public ResponseMsg addOrEditUser(@RequestBody User user) {
        if (null == user.getId()) {
            if (userService.userReg(user.getUsername(), user.getPassword()) >= 1) {
                return ResponseMsg.ok("添加成功！");
            } else {
                return ResponseMsg.error("添加失败！");
            }
        } else {
            if (userService.updateUser(user) >= 1) {
                return ResponseMsg.ok("修改成功！");
            } else {
                return ResponseMsg.error("修改失败！");
            }
        }
    }

    @PostMapping("/info")
    public ResponseMsg updateUserInfo(@RequestBody User user) {
        if (userService.updateUser(user) >= 1) {
            return ResponseMsg.ok("修改成功！");
        } else {
            return ResponseMsg.error("修改失败！");
        }
    }

    @PostMapping("/pass")
    public ResponseMsg updateUserPassword(@RequestBody Map<String, Object> info) {
        String oldPassword = String.valueOf(info.get("oldpass"));
        String password = String.valueOf(info.get("pass"));
        Integer userId = (Integer) info.get("userId");
        if (userService.updateUserPassword(oldPassword, password, userId)) {
            return ResponseMsg.ok("修改密码成功！");
        } else {
            return ResponseMsg.error("修改密码失败！");
        }
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PutMapping("/role")
    public ResponseMsg updateUserRole(Integer uid, Integer[] rids) {
        if (userService.updateUserRole(uid, rids)) {
            return ResponseMsg.ok("更新成功!");
        }
        return ResponseMsg.error("更新失败!");
    }

    @PostMapping("/del")
    public ResponseMsg deleteUsers(@RequestBody User user) {
        if (userService.deleteUsers(user) >= 1) {
            return ResponseMsg.ok("删除成功！");
        } else {
            return ResponseMsg.error("删除失败！");
        }
    }
}
