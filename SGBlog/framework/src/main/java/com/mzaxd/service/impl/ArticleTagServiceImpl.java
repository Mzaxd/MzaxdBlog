package com.mzaxd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzaxd.domain.entity.ArticleTag;
import com.mzaxd.service.ArticleTagService;
import com.mzaxd.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author root
* @description 针对表【sg_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2022-12-07 04:41:25
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




