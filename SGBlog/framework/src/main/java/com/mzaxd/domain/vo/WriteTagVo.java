package com.mzaxd.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author root
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WriteTagVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 标签名
     */
    private String name;

}
