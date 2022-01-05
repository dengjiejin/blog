package com.djj.blog.controller;

import com.djj.blog.entity.Article;
import com.djj.blog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;

import javax.annotation.Resource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

@Controller
@RequestMapping("article")
public class ArticleController {

    @Resource
    private ArticleService articleService;


    /**
     * 分页显示博客
     */
    @RequestMapping("")
    public String list(Model model){
        List<Article> articles = articleService.list();
        model.addAttribute("articles",articles);

        return "front/index";
    }
    /**
     * 按类型显示博客
     */
    @RequestMapping("/column/{displayname}/{category}")
    public String columr(@PathVariable("displayname") String displayname,@PathVariable("category") String category,Model model){
        model.addAttribute("articles",articleService.getArticleByCategoryName(category));
        model.addAttribute("displayName",displayname);
        return "front/columnPage";
    }

    /**
     * 显示博客详细信息
     */
    @RequestMapping("detail/{id}")
    public String detail(@PathVariable("id") String id,Model model) throws ParseException {
        Article article = articleService.getById(id);

        Markdown markdown = new Markdown();

        StringWriter out = new StringWriter();
        markdown.transform(new StringReader(article.getContent()),out);
        out.flush();
        article.setContent(out.toString());

        model.addAttribute("article",article);
        return "front/detail";
    }

    /**
     * 搜索
     */
    @RequestMapping("search")
    public String search(String key,Model model){
        List<Article> articles = articleService.search(key);
        model.addAttribute("articles",articles);

        return "front/columnPage";
    }
}
