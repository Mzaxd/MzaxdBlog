package com.mzaxd.mapper;

import com.mzaxd.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author root
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2022-12-06 06:47:44
* @Entity com.mzaxd.domain.entity.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据userId查询权限列表
     *
     * @author mzaxd
     * @date 12/6/22 1:36 PM
     * @param id
     * @return List<String>
     */
    List<String> selectPermsByUserId(Long id);

    /**
     * 查询所有符合条件的动态路由
     *
     * @author mzaxd
     * @date 12/6/22 1:37 PM
     * @return List<Menu>
     */
    List<Menu> selectAllRouterMenu();

    /**
     * 根据用户id查询对应动态路由
     *
     * @author mzaxd
     * @date 12/6/22 1:40 PM
     * @param userId
     * @return List<Menu>
     */
    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}




