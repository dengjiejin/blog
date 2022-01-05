package com.djj.blog.cinfig;

import com.djj.blog.service.UserService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {



    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        /**
         * anon:无需认证就可以访问
         * authc:必须认证才能访问
         * user: 必须有记住我功能才能访问
         * perms:用有某个资源的权限才能访问
         * role:拥有某个角色权限才能访问
         */
        Map<String,String> map = new LinkedHashMap<>();
        map.put("/admin/update/**","perms[user:add]");
//        map.put("/admin/**","authc");
        map.put("/admin/save","authc");
        map.put("/admin/write","authc");
        map.put("/admin/delete/**","authc");
//        map.put("/admin/update/**","authc");


        bean.setFilterChainDefinitionMap(map);

        //设置登录的权限
        bean.setLoginUrl("/admin/login");
        //未授权页面
        bean.setUnauthorizedUrl("/admin/noauth");

        return bean;
    }



    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm());
        return securityManager;
    }


    //创建realm对象
    @Bean(name="userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
