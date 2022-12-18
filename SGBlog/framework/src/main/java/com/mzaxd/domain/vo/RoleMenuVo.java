package com.mzaxd.domain.vo;

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
public class RoleMenuVo {

    private List<MenuTreeVo> menus;

    private List<Long> checkedKeys;
}
