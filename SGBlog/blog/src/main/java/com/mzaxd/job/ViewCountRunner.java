package com.mzaxd.job;

import com.mzaxd.constans.RedisConstants;
import com.mzaxd.domain.entity.Article;
import com.mzaxd.mapper.ArticleMapper;
import com.mzaxd.utils.RedisCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author root
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //从mysql查询博客信息 id viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(
                        article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()
                ));
        //存储到Redis中
        redisCache.setCacheMap(RedisConstants.ARTICLE_VIEW_COUNT, viewCountMap);
    }
}
