package com.mzaxd.mapper;

import com.mzaxd.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author root
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2022-12-06 06:51:06
* @Entity com.mzaxd.domain.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}




