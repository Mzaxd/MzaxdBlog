package com.mzaxd.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author root
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {

    /**
     * 菜单名称
     */
    private String menuName;


    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;
}
