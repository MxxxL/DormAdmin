package com.kaiyu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mxxxl
 * @date 2021/6/21
 */
@Data
public class MenuRole implements Serializable {
    private Integer id;

    private Integer mid;

    private Integer rid;

    private static final long serialVersionUID = 1L;
}