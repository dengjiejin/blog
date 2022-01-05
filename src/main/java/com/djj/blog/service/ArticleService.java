package com.djj.blog.service;

import com.djj.blog.dao.ArticleDao;
import com.djj.blog.entity.Article;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleService {

    @Resource
    private ArticleDao articleDao;

    /**
     * 查询所有博客
     */
    public List<Article> list(){
        List<Article> articles = articleDao.findAll();
        return articles;
    }

    /**
     * 根据id查找博客
     */
    public Article getById(String id){
        return articleDao.getById(id);
    }

    /**
     * 删除博客
     */
    public void delete(String id){
        articleDao.deleteById(id);
    }
    /**
     * 修改博客
     */

    public void update(String id){
//        articleDao.
    }

    public void save(Article article){
        articleDao.save(article);
    }

    public List<Article> getArticleByCategoryName(String categoryName) {
        List<Article> articles = articleDao.findAllByCategory_Name(categoryName);
        return articles;
    }

    /**
     * 查找
     */
    public List<Article> search(String key){
        return articleDao.findByTitleLike(key);
    }
}

