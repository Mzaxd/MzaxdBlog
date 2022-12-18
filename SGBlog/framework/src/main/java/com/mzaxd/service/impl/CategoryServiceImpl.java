package com.mzaxd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzaxd.constans.SystemConstants;
import com.mzaxd.domain.ResponseResult;
import com.mzaxd.domain.dto.UpdateCategoryStatusDto;
import com.mzaxd.domain.entity.Article;
import com.mzaxd.domain.entity.Category;
import com.mzaxd.domain.vo.CategoryVo;
import com.mzaxd.domain.vo.PageVo;
import com.mzaxd.domain.vo.WriteCategoryVo;
import com.mzaxd.mapper.CategoryMapper;
import com.mzaxd.service.ArticleService;
import com.mzaxd.service.CategoryService;
import com.mzaxd.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author Mzaxd
* @description 针对表【sg_category(分类表)】的数据库操作Service实现
* @createDate 2022-11-22 16:07:21
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Resource
    private ArticleService articleService;

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 返回分类信息
     *
     * @return ResponseResult<List>
     * @author mzaxd
     * @date 2022/11/22 16:21
     */
    @Override
    public ResponseResult<List<CategoryVo>> getCategoryList() {
        //查询文章表 状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryId = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryId);
        categories = categories.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.toString().equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult<List<WriteCategoryVo>> listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, SystemConstants.CATEGORY_STATUS_NORMAL);
        List<Category> list = list(queryWrapper);
        List<WriteCategoryVo> writeCategoryVos = BeanCopyUtils.copyBeanList(list, WriteCategoryVo.class);
        return ResponseResult.okResult(writeCategoryVos);
    }

    @Override
    public ResponseResult getCategoryListByPageAndCondition(Integer pageNum, Integer pageSize, Category condition) {
        //设置条件
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(condition.getName()), Category::getName, condition.getName());
        queryWrapper.eq(StringUtils.hasText(condition.getStatus()), Category::getStatus, condition.getStatus());
        //按条件查询
        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装返回
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(Category category) {
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryById(Long id) {
        Category category = categoryMapper.selectById(id);
        return ResponseResult.okResult(category);
    }

    @Override
    public ResponseResult updateCategory(Category category) {
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeStatus(UpdateCategoryStatusDto updateCategoryStatusDto) {
        LambdaUpdateWrapper<Category> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Category::getStatus, updateCategoryStatusDto.getStatus())
                     .eq(Category::getId, updateCategoryStatusDto.getCategoryId());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategorys(String ids) {
        String[] id = ids.split(",");
        getBaseMapper().deleteBatchIds(Arrays.asList(id));
        return ResponseResult.okResult();
    }


}




