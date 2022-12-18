package com.mzaxd.controller.system;

import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.MenuDto;
import com.mzaxd.domain.entity.Menu;
import com.mzaxd.service.MenuService;
import com.mzaxd.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author root
 */
@RequestMapping("/system/menu")
@RestController
public class MenuController {

    @Resource
    private MenuService menuService;

    @Resource
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult menuList(MenuDto menuDto) {
        return menuService.menuList(menuDto);
    }

    @PostMapping()
    public ResponseResult addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

    @GetMapping("/{id}")
    public ResponseResult getMenuById(@PathVariable("id") Integer id) {
        return menuService.getMenuById(id);
    }

    @PutMapping()
    public ResponseResult updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteMenu(@PathVariable Integer id) {
        return menuService.deleteMenu(id);
    }

    @GetMapping("/treeSelect")
    public ResponseResult treeSelect() {
        return menuService.treeSelect();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("id") Long id) {
        return roleService.roleMenuTreeSelect(id);
    }

}
