package com.mzaxd.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mzaxd
 * @since 2022-11-22 16:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 分类名
     */
    private String name;
}
