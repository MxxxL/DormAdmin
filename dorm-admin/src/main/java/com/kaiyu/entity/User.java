package com.kaiyu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mxxxl
 * @date 2021/6/15
 */
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 账户
     */
    private String username;

    /**
     * 密码
     */
    private String password;


    private static final long serialVersionUID = 1L;
}