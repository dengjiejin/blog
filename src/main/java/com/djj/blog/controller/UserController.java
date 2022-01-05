package com.djj.blog.controller;

import com.djj.blog.aspect.WebSecurityConfig;
import com.djj.blog.entity.Article;
import com.djj.blog.entity.Category;
import com.djj.blog.entity.User;
import com.djj.blog.service.ArticleService;
import com.djj.blog.service.CategoryService;
import com.djj.blog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("admin")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private ArticleService articleService;

    @Resource
    private CategoryService categoryService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**/

    @RequestMapping("")
    public String admin(Model model){
        List<Article> articles = articleService.list();
        model.addAttribute("articles",articles);

        return "admin/index";
    }

    /**
     * 登录操作
     */
    @RequestMapping("login")
    private String login(){
        return "admin/login";
    }

    /**
     * 验证输入信息
     */
    @RequestMapping(value = "dologin", method = RequestMethod.POST)
    public String doLogin(HttpServletResponse response, User user, Model model){
//        if(userService.login(user.getUsername(),user.getPassword())){
//            Cookie cookie = new Cookie(WebSecurityConfig.SESSION_KEY,user.toString());
//            response.addCookie(cookie);
//            model.addAttribute("user",user);
//            return "redirect:/admin";
//        }else{
//            model.addAttribute("error","用户名或者密码错误");
//            return "admin/login";
//        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try{
            subject.login(token);
            return "redirect:/admin";
        }catch(UnknownAccountException e){
            model.addAttribute("msg","用户名错误");
            return "admin/login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "admin/login";
        }

    }

    /**
     * 写博客
     */
    @RequestMapping("write")
    public String write(Model model){
        List<Category> categories = categoryService.list();
        model.addAttribute("categories",categories);
        model.addAttribute("article",new Article());
        return "admin/write";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public String save(Article article){
        String name = article.getCategory().getName();
        Category category = categoryService.findByName(name);
        article.setCategory(category);
        if(article.getContent().length()>40){
            article.setSummary(article.getContent().substring(0,40));
        }else{
            article.setSummary(article.getContent());
        }
        article.setDate(sdf.format(new Date()));
        article.setId("123");
        articleService.save(article);

        return "redirect:/admin";
    }

    /**
     * 删除博客
     */
    @RequestMapping("delete/{id}")
    public String delete(@PathVariable("id") String id){
        articleService.delete(id);
        return "redirect:/admin";
    }

    /**
     * 修改博客
     */
    @RequestMapping("update/{id}")
    public String update(@PathVariable("id") String id,Model model){
        Article article = articleService.getById(id);
        model.addAttribute("target",article);
        List<Category> categories = categoryService.list();
        model.addAttribute("categories",categories);
        model.addAttribute("article",new Article());
        return "admin/update";
    }

    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权无法访问";
    }
}
