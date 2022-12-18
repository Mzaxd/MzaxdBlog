package com.mzaxd.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author root
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleDto {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * 菜单权限id列表
     */
    private List<String> menuIds;

    /**
     * 备注
     */
    private String remark;
}
