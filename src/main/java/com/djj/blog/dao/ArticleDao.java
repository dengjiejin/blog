package com.djj.blog.dao;

import com.djj.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<Article, String> {

    public List<Article> findAllByCategory_Name(String name);

    @Query("from Article where title like %:title%")
    public List<Article> findByTitleLike(@Param("title") String title);

}
