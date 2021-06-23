package com.kaiyu.model;

import lombok.Data;

import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/23
 */
@Data
public class ResponsePage {

    private List<?> data;
    private Long total;
}
