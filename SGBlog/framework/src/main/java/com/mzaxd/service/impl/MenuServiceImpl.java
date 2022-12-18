package com.mzaxd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzaxd.constans.SystemConstants;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.MenuDto;
import com.mzaxd.domain.entity.Menu;
import com.mzaxd.domain.vo.MenuTreeVo;
import com.mzaxd.domain.vo.MenuVo;
import com.mzaxd.enums.AppHttpCodeEnum;
import com.mzaxd.mapper.MenuMapper;
import com.mzaxd.service.MenuService;
import com.mzaxd.utils.BeanCopyUtils;
import com.mzaxd.utils.SecurityUtils;
import com.mzaxd.utils.TreeUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author root
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
 * @createDate 2022-12-06 06:47:44
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if (SecurityUtils.isAdmin()) {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus, SystemConstants.LINK_STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            return menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
        }
        //否则返回所具有的权限
        List<String> perms = getBaseMapper().selectPermsByUserId(id);
        return perms;
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        List<Menu> menus = null;
        //判断是否是管理员
        if (SecurityUtils.isAdmin()) {
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        } else {
            //否则 获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建tree
        //先找出第一层的菜单 然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = TreeUtils.generateTrees(menus);
        return menuTree;
    }

    @Override
    public ResponseResult menuList(MenuDto menuDto) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(menuDto.getMenuName()), Menu::getMenuName, menuDto.getMenuName());
        queryWrapper.eq(StringUtils.hasText(menuDto.getStatus()), Menu::getStatus, menuDto.getStatus());
        queryWrapper.orderByAsc(Menu::getOrderNum, Menu::getId);
        List<Menu> list = list(queryWrapper);
        List<MenuVo> menuVo = BeanCopyUtils.copyBeanList(list, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Integer id) {
        Menu menu = getBaseMapper().selectById(id);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if (menu.getId().equals(menu.getParentId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Integer id) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Menu::getParentId, id);
        List<Menu> childrenMenuList = list(queryWrapper);
        if (!childrenMenuList.isEmpty()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "存在子菜单不允许删除");
        }
        getBaseMapper().deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult treeSelect() {
        //先找出所有Menu
        List<Menu> menus = menuMapper.selectAllRouterMenu();
        List<MenuTreeVo> menuTree = BeanCopyUtils.copyBeanList(menus, MenuTreeVo.class);
        //将menuName复制给label
        for (MenuTreeVo mt : menuTree) {
            mt.setLabel(mt.getMenuName());
        }
        List<MenuTreeVo> menuTreeVos = TreeUtils.generateTrees(menuTree);
        return ResponseResult.okResult(menuTreeVos);
    }
}




