//package com.smilcool.server.base.config.elasticsearch.repository;
////
////import cn.hutool.json.JSONUtil;
////import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
////import com.smilcool.server.SmilcoolServerApplicationTests;
////import com.smilcool.server.common.util.BeanUtil;
////import com.smilcool.server.core.pojo.form.ArticleQueryForm;
////import com.smilcool.server.core.pojo.po.Article;
////import com.smilcool.server.core.pojo.vo.ArticleVO;
////import com.smilcool.server.core.service.ArticleService;
////import lombok.extern.slf4j.Slf4j;
////import org.junit.Test;
////import org.springframework.beans.factory.annotation.Autowired;
////
////import java.util.List;
////
/////**
//// * @author Angus
//// * @date 2019/5/26
//// */
////@Slf4j
////public class ArticleRepositoryTest extends SmilcoolServerApplicationTests {
////
////    @Autowired
////    private ArticleService articleService;
////
////
////    /**
////     * 创建索引和映射
////     */
////    @Test
////    public void createIndex() {
////        // 删除索引
////        elasticsearchTemplate.deleteIndex(ArticleDocument.class);
////        // 重建索引和映射
////        elasticsearchTemplate.createIndex(ArticleDocument.class);
////        elasticsearchTemplate.putMapping(ArticleDocument.class);
////    }
////
////    @Test
////    public void testSave() {
////        List<Article> articleList = articleService.listArticle(new ArticleQueryForm());
////        articleList.forEach(article -> {
////            ArticleDocument articleDocument = BeanUtil.copyProp(article, ArticleDocument.class);
////            // 将以 JSON 数组形式存储的 tags 字符串字段转化为 List<String> 类型
////            List<String> tags = JSONUtil.parseArray(article.getTags()).toList(String.class);
////            articleDocument.setTagList(tags);
////            articleRepository.save(articleDocument);
////        });
////    }
////}