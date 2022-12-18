package com.mzaxd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzaxd.constans.SystemConstants;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.AddRoleDto;
import com.mzaxd.domain.dto.RoleChangeStatusDto;
import com.mzaxd.domain.dto.RoleDto;
import com.mzaxd.domain.dto.UpdateRoleDto;
import com.mzaxd.domain.entity.Menu;
import com.mzaxd.domain.entity.Role;
import com.mzaxd.domain.entity.RoleMenu;
import com.mzaxd.domain.vo.MenuTreeVo;
import com.mzaxd.domain.vo.PageVo;
import com.mzaxd.domain.vo.RoleMenuVo;
import com.mzaxd.domain.vo.RoleVo;
import com.mzaxd.mapper.MenuMapper;
import com.mzaxd.mapper.RoleMapper;
import com.mzaxd.mapper.RoleMenuMapper;
import com.mzaxd.service.RoleMenuService;
import com.mzaxd.service.RoleService;
import com.mzaxd.utils.BeanCopyUtils;
import com.mzaxd.utils.SecurityUtils;
import com.mzaxd.utils.TreeUtils;
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
 * @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
 * @createDate 2022-12-06 06:51:06
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员，如果是返回集合中只需要有admin
        if (SecurityUtils.isAdmin()) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的其他角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult getRoleList(Integer pageNum, Integer pageSize, RoleDto roleDto) {
        //封装条件
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleDto.getRoleName()), Role::getRoleName, roleDto.getRoleName());
        queryWrapper.eq(StringUtils.hasText(roleDto.getStatus()), Role::getStatus, roleDto.getStatus());
        queryWrapper.orderByAsc(Role::getRoleSort);
        //设置分页查询
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        //封装结果返回
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(RoleChangeStatusDto roleChangeDto) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Role::getStatus, roleChangeDto.getStatus())
                     .eq(Role::getId, roleChangeDto.getRoleId());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        roleMapper.insert(role);
        System.out.println(role.getId());
        //TODO 添加 menuIds
        List<RoleMenu> roleMenus = addRoleDto.getMenuIds().stream()
                .map(id -> new RoleMenu(role.getId(), Long.valueOf(id)))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(String ids) {
        String[] id = ids.split(",");
        getBaseMapper().deleteBatchIds(Arrays.asList(id));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.ROLE_STATUS_NORMAL);
        List<Role> roleList = list(queryWrapper);
        return ResponseResult.okResult(roleList);
    }

    @Override
    @Transactional
    public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
        Role role = BeanCopyUtils.copyBean(updateRoleDto, Role.class);
        roleMapper.updateById(role);
        roleMenuMapper.deleteById(updateRoleDto.getId());
        List<RoleMenu> roleMenus = updateRoleDto.getMenuIds().stream()
                .map(id -> new RoleMenu(updateRoleDto.getId(), Long.valueOf(id)))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult roleMenuTreeSelect(Long id) {
        //先根据roleId找出所有有关Menu
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, id);
        List<RoleMenu> roleMenus = roleMenuService.list(queryWrapper);
        //将roleMenus的menuId取出
        List<Long> menuIdList = roleMenus.stream()
                .map(roleMenu -> roleMenu.getMenuId())
                .collect(Collectors.toList());
        //先找出所有Menu
        List<Menu> menus = menuMapper.selectAllRouterMenu();
        List<MenuTreeVo> menuTree = BeanCopyUtils.copyBeanList(menus, MenuTreeVo.class);
        //将menuName复制给label
        for (MenuTreeVo mt : menuTree) {
            mt.setLabel(mt.getMenuName());
        }
        List<MenuTreeVo> menuTreeVos = TreeUtils.generateTrees(menuTree);
        //封装结果返回
        RoleMenuVo roleMenuVo = new RoleMenuVo(menuTreeVos, menuIdList);
        return ResponseResult.okResult(roleMenuVo);
    }

    @Override
    public ResponseResult getRoleInfo(Long id) {
        Role role = getBaseMapper().selectById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }
}




