package com.mzaxd.domain.vo;

import com.mzaxd.utils.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * @author root
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuTreeVo implements TreeNode<Long> {

    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单名称(前端所需名称)
     */
    private String label;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 子路由列表
     */
    private List<MenuTreeVo> children;

    @Override
    public Long id() {
        return this.id;
    }

    @Override
    public Long parentId() {
        return this.parentId;
    }

    @Override
    public boolean root() {
        return Objects.equals(this.parentId, 0L);
    }

    @Override
    public void setChildren(List<? extends TreeNode<Long>> children) {
        this.children = (List<MenuTreeVo>) children;
    }
}
