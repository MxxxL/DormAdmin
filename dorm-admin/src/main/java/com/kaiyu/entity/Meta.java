package com.kaiyu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mxxxl
 * @date 2021/6/21
 */
@Data
public class Meta implements Serializable {

    private Boolean keepAlive;

    private Boolean requireAuth;

    private static final long serialVersionUID = 1L;
}
