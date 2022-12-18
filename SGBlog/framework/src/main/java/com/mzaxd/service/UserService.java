package com.mzaxd.service;

import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.AddUserDto;
import com.mzaxd.domain.dto.UpdateUserRoleDto;
import com.mzaxd.domain.dto.UpdateUserStatusDto;
import com.mzaxd.domain.dto.UserListDto;
import com.mzaxd.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author root
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-11-27 05:50:02
*/
public interface UserService extends IService<User> {

    /**
     * 给个人信息页面返回用户信息
     *
     * @author mzaxd
     * @date 11/27/22 8:22 AM
     * @return ResponseResult
     */
    ResponseResult<?> userInfo();

    /**
     * 更新个人信息
     *
     * @author mzaxd
     * @date 11/27/22 11:24 AM
     * @param user
     * @return ResponseResult
     */
    ResponseResult<?> updateUserInfo(User user);

    /**
     * 用户注册
     *
     * @author mzaxd
     * @date 11/27/22 12:23 PM
     * @param user
     * @return ResponseResult
     */
    ResponseResult<?> register(User user);

    /**
     * 返回用户列表
     *
     * @param pageNum
     * @param pageSize
     * @param userListDto
     * @return ResponseResult
     * @author mzaxd
     * @date 12/11/22 9:23 AM
     */
    ResponseResult getUserList(Integer pageNum, Integer pageSize, UserListDto userListDto);

    /**
     * 后台增加用户
     *
     * @author mzaxd
     * @date 12/11/22 10:06 AM
     * @param addUserDto
     * @return ResponseResult
     */
    ResponseResult addUser(AddUserDto addUserDto);

    /**
     * 用户管理-修改-页面数据回显
     *
     * @author mzaxd
     * @date 12/15/22 1:50 PM
     * @param id
     * @return ResponseResult
     */
    ResponseResult getUserInfoById(Long id);

    /**
     * 同事更新用户信息和角色
     *
     * @author mzaxd
     * @date 12/15/22 11:52 PM
     * @param updateUserRoleDto
     * @return ResponseResult
     */
    ResponseResult updateUserRole(UpdateUserRoleDto updateUserRoleDto);

    /**
     * 修改状态
     *
     * @author mzaxd
     * @date 12/18/22 2:50 AM
     * @param updateUserStatusDto
     * @return ResponseResult
     */
    ResponseResult changeStatus(UpdateUserStatusDto updateUserStatusDto);

    /**
     * 删除用户
     *
     * @author mzaxd
     * @date 12/18/22 3:51 AM
     * @param ids
     * @return ResponseResult
     */
    ResponseResult deleteUsers(String ids);
}
