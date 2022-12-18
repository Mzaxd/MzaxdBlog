package com.mzaxd.domain.vo;

import com.mzaxd.domain.entity.Role;
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
public class UpdateUserRoleVo {

    List<Long> roleIds;

    List<Role> roles;

    UserUpdateVo user;
}
