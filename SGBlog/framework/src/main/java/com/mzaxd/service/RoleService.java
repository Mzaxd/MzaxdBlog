package com.mzaxd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.AddRoleDto;
import com.mzaxd.domain.dto.RoleChangeStatusDto;
import com.mzaxd.domain.dto.RoleDto;
import com.mzaxd.domain.dto.UpdateRoleDto;
import com.mzaxd.domain.entity.Role;

import java.util.List;

/**
* @author root
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2022-12-06 06:51:06
*/
public interface RoleService extends IService<Role> {

    /**
     * 根据userId查询角色关键字
     *
     * @author mzaxd
     * @date 12/6/22 1:22 PM
     * @param id
     * @return List<String>
     */
    List<String> selectRoleKeyByUserId(Long id);

    /**
     * 返回分页Role列表
     *
     * @author mzaxd
     * @date 12/11/22 3:06 AM
     * @param pageNum
     * @param pageSize
     * @param roleDto
     * @return ResponseResult
     */
    ResponseResult getRoleList(Integer pageNum, Integer pageSize, RoleDto roleDto);

    /**
     * 改变状态
     *
     * @author mzaxd
     * @date 12/11/22 3:59 AM
     * @param roleChangeDto
     * @return ResponseResult
     */
    ResponseResult changeStatus(RoleChangeStatusDto roleChangeDto);

    /**
     * 新增角色
     *
     * @author mzaxd
     * @date 12/11/22 8:50 AM
     * @param addRoleDto
     * @return ResponseResult
     */
    ResponseResult addRole(AddRoleDto addRoleDto);

    /**
     * 删除Role
     *
     * @author mzaxd
     * @date 12/11/22 8:57 AM
     * @param ids
     * @return ResponseResult
     */
    ResponseResult deleteRole(String ids);

    /**
     * 返回所有状态正常的角色
     *
     * @author mzaxd
     * @date 12/11/22 9:50 AM
     * @return ResponseResult
     */
    ResponseResult listAllRole();

    /**
     * 更新Role
     *
     * @author mzaxd
     * @date 12/12/22 12:21 AM
     * @param updateRoleDto
     * @return ResponseResult
     */
    ResponseResult updateRole(UpdateRoleDto updateRoleDto);

    /**
     * 返回角色菜单树
     *
     * @author mzaxd
     * @date 12/12/22 1:08 PM
     * @param id
     * @return ResponseResult
     */
    ResponseResult roleMenuTreeSelect(Long id);

    /**
     * 前端回显role信息
     *
     * @author mzaxd
     * @date 12/12/22 2:36 PM
     * @param id
     * @return ResponseResult
     */
    ResponseResult getRoleInfo(Long id);
}
