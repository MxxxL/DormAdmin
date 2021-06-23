package com.kaiyu.service;

import com.kaiyu.dao.UserDao;
import com.kaiyu.dao.UserRoleDao;
import com.kaiyu.entity.User;
import com.kaiyu.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        user.setRoles(userDao.getUserRolesById(user.getId()));
        return user;
    }

    public boolean updateUserRole(Integer uid, Integer[] rids) {
        userRoleDao.deleteByUserId(uid);
        return userRoleDao.addRole(uid, rids) == rids.length;
    }

    public int userReg(String username, String password) {
        if (userDao.findByUsername(username) != null) {
            return -1;
        }
        String encode = passwordEncoder.encode(password);
        return userDao.saveUser(username, encode);
    }

    public boolean updateUserPassword(String oldPassword, String password, Integer userId) {
        User user = userDao.findUserById(userId);
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            String encodedPassword = passwordEncoder.encode(password);
            Integer result = userDao.updatePassword(userId, encodedPassword);
            return result >= 1;
        }
        return false;
    }

    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    public List<User> getUserByName(String name) {
        return userDao.getUserByName(UserUtils.getCurrentUser().getId(), name);
    }

    public int deleteUsers(User user) {
        userRoleDao.deleteByUserId(user.getId());
        return userDao.deleteUser(user.getId());
    }
}
