package com.mzaxd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzaxd.domain.entity.RoleMenu;
import com.mzaxd.service.RoleMenuService;
import com.mzaxd.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author root
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2022-12-12 00:24:59
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




