package com.kaiyu.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/21
 */
@Data
public class Menu implements Serializable {
    private Integer id;

    private String url;

    private String path;

    private String component;

    private String name;

    private String iconCls;

    private Meta meta;

    private Integer parentId;

    private Boolean enabled;

    private List<Menu> children;
    private List<Role> roles;

    private static final long serialVersionUID = 1L;
}