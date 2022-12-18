package com.mzaxd.job;

import com.mzaxd.constans.RedisConstants;
import com.mzaxd.domain.entity.Article;
import com.mzaxd.service.ArticleService;
import com.mzaxd.utils.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author root
 */
@Component
public class UpdateViewCountJob {

    @Resource
    private RedisCache redisCache;

    @Resource
    private ArticleService articleService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount() {
        //获取Redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(RedisConstants.ARTICLE_VIEW_COUNT);

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), Long.valueOf(entry.getValue())))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);
    }
}
