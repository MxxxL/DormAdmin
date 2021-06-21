package com.kaiyu.utils;

import com.kaiyu.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author mxxxl
 * @date 2021/6/21
 */
public class UserUtils {

    private UserUtils() {
    }

    public static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
