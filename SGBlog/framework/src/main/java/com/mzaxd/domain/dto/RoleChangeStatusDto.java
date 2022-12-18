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
public class RoleChangeStatusDto {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;
}
