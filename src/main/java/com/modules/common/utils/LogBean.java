package com.modules.common.utils;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class LogBean {
    private String id;
    private Integer userId;
    private String username;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDate;
    private String ip;
    private String className;
    private String method;
    private String reqParam;
}
