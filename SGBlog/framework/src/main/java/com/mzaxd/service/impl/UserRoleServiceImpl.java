package com.mzaxd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzaxd.domain.entity.UserRole;
import com.mzaxd.service.UserRoleService;
import com.mzaxd.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author root
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2022-12-15 13:35:49
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




