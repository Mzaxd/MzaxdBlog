package com.mzaxd.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author root
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 友链名称
     */
    private String name;

    /**
     * 友链logo
     */
    private String logo;

    /**
     * 友链描述
     */
    private String description;

    /**
     * 网站地址
     */
    private String address;
}
