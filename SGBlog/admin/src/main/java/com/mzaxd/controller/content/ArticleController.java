package com.mzaxd.controller.content;

import com.mzaxd.annotation.SystemLog;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.AddArticleDto;
import com.mzaxd.domain.dto.ArticleListDto;
import com.mzaxd.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author root
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping()
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.addArticle(addArticleDto);
    }

    @GetMapping("/list")
    @SystemLog(businessName = "返回文章列表")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        return articleService.articleList(pageNum, pageSize, articleListDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticle(@PathVariable("id") Integer id) {
        return articleService.getArticleById(id);
    }

    @PutMapping()
    public ResponseResult updateArticle(@RequestBody AddArticleDto updateArticleDto) {
        return articleService.updateArticle(updateArticleDto);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteArticles(@PathVariable("ids") String ids) {
        return articleService.deleteArticles(ids);
    }

}
