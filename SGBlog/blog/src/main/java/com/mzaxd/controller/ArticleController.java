package com.mzaxd.controller;

import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.vo.HotArticleVo;
import com.mzaxd.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Mzaxd
 * @since 2022-11-22 10:22
 */

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult<List<HotArticleVo>> hotArticleList() {
        return articleService.hotArticleList();
    }

    @GetMapping("articleList")
    public ResponseResult<?> articleListByCategory(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleListByCategory(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult<?> getArticleDetail(@PathVariable Long id) {
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult<?> updateViewCount(@PathVariable Long id) {
        return articleService.updateViewCount(id);
    }

}
