package com.mzaxd.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author root
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListDto {

    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

}
