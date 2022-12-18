package com.mzaxd.service;

import com.mzaxd.domain.dto.AddArticleDto;
import com.mzaxd.domain.dto.ArticleListDto;
import com.mzaxd.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.vo.HotArticleVo;

import java.util.List;

/**
* @author Mzaxd
* @description 针对表【sg_article(文章表)】的数据库操作Service
* @createDate 2022-11-22 10:14:47
*/
public interface ArticleService extends IService<Article> {

    /**
     * 返回热门文章列表
     * @author mzaxd
     * @date 2022/11/22 12:38
     * @return ResponseResult<List<HotArticleVo>>
     */
    ResponseResult<List<HotArticleVo>> hotArticleList();

    /**
     * 分页返回文章概览信息
     *
     * @author mzaxd
     * @date 2022/11/22 16:57
     * @param pageNum 查询页号
     * @param pageSize 每页数据量
     * @param categoryId 所属类别id
     * @return ResponseResult
     */
    ResponseResult<?> articleListByCategory(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 获取文章详情页信息
     *
     * @author mzaxd
     * @date 11/26/22 10:21 AM
     * @param id 文章id
     * @return ResponseResult<?>
     */
    ResponseResult<?> getArticleDetail(Long id);

    /**
     * 更新文章浏览量
     *
     * @author mzaxd
     * @date 12/4/22 1:02 AM
     * @param id
     * @return ResponseResult<?>
     */
    ResponseResult<?> updateViewCount(Long id);

    /**
     * 添加文章
     *
     * @author mzaxd
     * @date 12/7/22 4:33 AM
     * @param addArticleDto
     * @return ResponseResult
     */
    ResponseResult<?> addArticle(AddArticleDto addArticleDto);

    /**
     * 给后台返回所有文章的信息
     *
     * @author mzaxd
     * @date 12/10/22 11:32 AM
     * @param pageNum
     * @param pageSize
     * @param articleListDto
     * @return ResponseResult
     */
    ResponseResult articleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    /**
     * 根据id返回对应文章
     *
     * @author mzaxd
     * @date 12/10/22 12:33 PM
     * @param id
     * @return ResponseResult
     */
    ResponseResult getArticleById(Integer id);

    /**
     * 修改文章
     *
     * @author mzaxd
     * @date 12/10/22 1:11 PM
     * @param updateArticleDto
     * @return ResponseResult
     */
    ResponseResult updateArticle(AddArticleDto updateArticleDto);

    /**
     * 批量删除文章
     *
     * @author mzaxd
     * @date 12/10/22 1:35 PM
     * @param ids
     * @return ResponseResult
     */
    ResponseResult deleteArticles(String ids);
}
