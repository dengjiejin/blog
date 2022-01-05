package com.djj.blog.service;

import com.djj.blog.dao.CategoryDao;
import com.djj.blog.entity.Category;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    @Resource
    private CategoryDao categoryDao;

    public List<Category> list(){
        List<Category> categories = categoryDao.findAll();
        return categories;
    }

    public Category findByName(String name){
        Category category = categoryDao.findByName(name);
        return category;
    }
}
