/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pig.admin.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户绑定信息表
 *
 * @author lixuelaing
 * @date 2020-08-03 10:18:58
 */
@Data
public class CommunistUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 身份证号码
     */
    private String idCard;
    /**
     * 微信openid
     */
    private String openId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 删除标志
     */
    private Integer deleteFlag;
    /**
     * 用户来源（1:在职 2:离职）
     */
    private Integer type;
    /**
     * 所在公司
     */
    private Integer company;
    /**
     * 所在部门
     */
    private Integer department;
    /**
     * 所在党支部
     */
    private String party;
    /**
     * 职务档次
     */
    private String userPosition;
    /**
     * 在职离职
     */
    private Integer workStatus;

}
