package com.mzaxd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.MenuDto;
import com.mzaxd.domain.entity.Menu;

import java.util.List;

/**
* @author root
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2022-12-06 06:47:44
*/
public interface MenuService extends IService<Menu> {

    /**
     * 根据用户id查询对应权限关键字
     *
     * @author mzaxd
     * @date 12/6/22 7:00 AM
     * @param id
     * @return List<String>
     */
    List<String> selectPermsByUserId(Long id);

    /**
     * 按照树形数据结构返回动态路由数据
     *
     * @author mzaxd
     * @date 12/6/22 1:30 PM
     * @param userId
     * @return List<Menu>
     */
    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    /**
     * 返回所有菜单
     *
     * @author mzaxd
     * @date 12/11/22 2:18 AM
     * @param menuDto
     * @return ResponseResult
     */
    ResponseResult menuList(MenuDto menuDto);

    /**
     * 添加Menu
     *
     * @author mzaxd
     * @date 12/11/22 2:18 AM
     * @param menu
     * @return ResponseResult
     */
    ResponseResult addMenu(Menu menu);

    /**
     * 根据id返回Menu信息
     *
     * @author mzaxd
     * @date 12/11/22 2:23 AM
     * @param id
     * @return ResponseResult
     */
    ResponseResult getMenuById(Integer id);

    /**
     * 修改Menu
     *
     * @author mzaxd
     * @date 12/11/22 2:29 AM
     * @param menu
     * @return ResponseResult
     */
    ResponseResult updateMenu(Menu menu);

    /**
     * 根据id删除Menu
     *
     * @author mzaxd
     * @date 12/11/22 2:40 AM
     * @param id
     * @return ResponseResult
     */
    ResponseResult deleteMenu(Integer id);

    /**
     * 给添加角色页面返回菜单选项树
     *
     * @author mzaxd
     * @date 12/11/22 5:34 AM
     * @return ResponseResult
     */
    ResponseResult treeSelect();
}
