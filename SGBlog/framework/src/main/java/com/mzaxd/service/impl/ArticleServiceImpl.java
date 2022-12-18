package com.mzaxd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzaxd.constans.RedisConstants;
import com.mzaxd.constans.SystemConstants;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.AddArticleDto;
import com.mzaxd.domain.dto.ArticleListDto;
import com.mzaxd.domain.entity.Article;
import com.mzaxd.domain.entity.ArticleTag;
import com.mzaxd.domain.entity.Category;
import com.mzaxd.domain.vo.*;
import com.mzaxd.mapper.ArticleMapper;
import com.mzaxd.mapper.ArticleTagMapper;
import com.mzaxd.service.ArticleService;
import com.mzaxd.service.ArticleTagService;
import com.mzaxd.service.CategoryService;
import com.mzaxd.utils.BeanCopyUtils;
import com.mzaxd.utils.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Mzaxd
 * @description 针对表【sg_article(文章表)】的数据库操作Service实现
 * @createDate 2022-11-22 10:14:47
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private CategoryService categoryService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private ArticleTagMapper articleTagMapper;

    /**
     * 返回热门文章列表
     *
     * @return ResponseResult<List < HotArticleVo>>
     * @author mzaxd
     * @date 2022/11/22 12:38
     */
    @Override
    public ResponseResult<List<HotArticleVo>> hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();
        //bean拷贝
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVos);
    }

    /**
     * 分页返回文章概览信息
     *
     * @param pageNum    查询页号
     * @param pageSize   每页数据量
     * @param categoryId 所属类别id
     * @return ResponseResult
     * @author mzaxd
     * @date 2022/11/22 16:57
     */
    @Override
    public ResponseResult<PageVo> articleListByCategory(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果有categoryId 就要查询
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        //查询categoryName
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 获取文章详情页信息
     *
     * @param id 文章id
     * @return ResponseResult<?>
     * @author mzaxd
     * @date 11/26/22 10:21 AM
     */
    @Override
    public ResponseResult<?> getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue(RedisConstants.ARTICLE_VIEW_COUNT, id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (Objects.nonNull(category)) {
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult<?> updateViewCount(Long id) {
        //更新Redis中对应id的浏览量
        redisCache.incrementCacheMapValue(RedisConstants.ARTICLE_VIEW_COUNT, id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult<?> addArticle(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);
        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<?> articleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        //设置条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(articleListDto.getTitle()), Article::getTitle, articleListDto.getTitle());
        queryWrapper.like(StringUtils.hasText(articleListDto.getSummary()), Article::getSummary, articleListDto.getSummary());
        //按条件查询查询
        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装返回
        List<ArticleListManageVo> articleListManageVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListManageVo.class);
        PageVo pageVo = new PageVo(articleListManageVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleById(Integer id) {
        Article article = getBaseMapper().selectById(id);
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        //根据文章id查找Tags
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, articleVo.getId());
        List<ArticleTag> articleTags = articleTagMapper.selectList(queryWrapper);
        List<Long> tags = articleTags.stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        //封装数据返回
        articleVo.setTags(tags);
        return ResponseResult.okResult(articleVo);
    }

    @Override
    public ResponseResult updateArticle(AddArticleDto updateArticleDto) {
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        getBaseMapper().updateById(article);
        List<ArticleTag> articleTags = updateArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        //添加 博客和标签的关联
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, article.getId());
        articleTagService.remove(queryWrapper);
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticles(String ids) {
        String[] id = ids.split(",");
        getBaseMapper().deleteBatchIds(Arrays.asList(id));
        return ResponseResult.okResult();
    }
}




