package com.mzaxd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.AddUserDto;
import com.mzaxd.domain.dto.UpdateUserRoleDto;
import com.mzaxd.domain.dto.UpdateUserStatusDto;
import com.mzaxd.domain.dto.UserListDto;
import com.mzaxd.domain.entity.Role;
import com.mzaxd.domain.entity.User;
import com.mzaxd.domain.entity.UserRole;
import com.mzaxd.domain.vo.*;
import com.mzaxd.mapper.RoleMapper;
import com.mzaxd.mapper.UserMapper;
import com.mzaxd.mapper.UserRoleMapper;
import com.mzaxd.service.UserRoleService;
import com.mzaxd.service.UserService;
import com.mzaxd.utils.BeanCopyUtils;
import com.mzaxd.utils.SecurityUtils;
import com.mzaxd.utils.UserUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author root
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2022-11-26 12:24:08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserService userService;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserUtils userUtils;

    @Override
    public ResponseResult<?> userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据id查询用户信息
        User user = getById(userId);
        //将用户信息封装Vo并返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult<?> updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<?> register(User user) {
        //对数据进行合法性判断
        userUtils.isUserLegal(user);
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        //封装条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userListDto.getUserName()), User::getUserName, userListDto.getUserName());
        queryWrapper.like(StringUtils.hasText(userListDto.getPhonenumber()), User::getPhonenumber, userListDto.getPhonenumber());
        queryWrapper.eq(StringUtils.hasText(userListDto.getStatus()), User::getStatus, userListDto.getStatus());
        //分页查询
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        //封装结果返回
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(page.getRecords(), UserListVo.class);
        PageVo pageVo = new PageVo(userListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult addUser(AddUserDto addUserDto) {
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        //对数据进行非空判断
        userUtils.isUserLegal(user);
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        //将用户-角色关系存入数据库
        List<UserRole> userRoles = addUserDto.getRoleIds().stream()
                .map(id -> new UserRole(user.getId(), Long.valueOf(id)))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserInfoById(Long id) {
        //获取所需信息
        User user = userMapper.selectById(id);
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        ArrayList<Long> roleIds = new ArrayList<>();
        for (UserRole userRole: userRoles) {
            roleIds.add(userRole.getRoleId());
        }
        List<Role> roles = roleMapper.selectList(null);
        //转换成对应Vo
        UserUpdateVo userVo = BeanCopyUtils.copyBean(user, UserUpdateVo.class);
        //封装返回
        UpdateUserRoleVo updateUserRoleVo = new UpdateUserRoleVo(roleIds, roles, userVo);
        return ResponseResult.okResult(updateUserRoleVo);
    }

    @Override
    @Transactional
    public ResponseResult updateUserRole(UpdateUserRoleDto updateUserRoleDto) {
        User user = BeanCopyUtils.copyBean(updateUserRoleDto, User.class);
        userMapper.updateById(user);
        userRoleMapper.deleteById(updateUserRoleDto.getId());
        List<UserRole> userRoles = updateUserRoleDto.getRoleIds().stream()
                .map(id -> new UserRole(updateUserRoleDto.getId(), id))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeStatus(UpdateUserStatusDto updateUserStatusDto) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getStatus, updateUserStatusDto.getStatus())
                     .eq(User::getId, updateUserStatusDto.getUserId());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUsers(String ids) {
        String[] id = ids.split(",");
        getBaseMapper().deleteBatchIds(Arrays.asList(id));
        return ResponseResult.okResult();
    }

}




