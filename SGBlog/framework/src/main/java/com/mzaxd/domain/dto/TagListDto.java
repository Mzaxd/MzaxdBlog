package com.mzaxd.domain.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author root
 */
@Data
@NoArgsConstructor
@Accessors
public class TagListDto {

    /**
     * 标签名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}
