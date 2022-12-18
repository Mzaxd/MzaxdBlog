package com.mzaxd.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author root
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListManageVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 创建时间
     */
    private Date createTime;

}
