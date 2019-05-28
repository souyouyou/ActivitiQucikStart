package com.krxinuo.activiti.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class Cfg_View extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("main").setViewName("main");
        registry.addViewController("top").setViewName("top");
        registry.addViewController("left").setViewName("left");
        registry.addViewController("index").setViewName("index");
        registry.addViewController("computer").setViewName("computer");
        registry.addViewController("default").setViewName("default");
        registry.addViewController("error").setViewName("error");
        registry.addViewController("filelist").setViewName("filelist");
        registry.addViewController("imglist").setViewName("imglist");
        registry.addViewController("form").setViewName("form");
        registry.addViewController("imglist1").setViewName("imglist1");
        registry.addViewController("imgtable").setViewName("imgtable");
        registry.addViewController("tools").setViewName("tools");
        registry.addViewController("right").setViewName("right");
        registry.addViewController("model-list").setViewName("activiti/model-list");
        registry.addViewController("editor").setViewName("activiti/modeler");
        registry.addViewController("user-manage").setViewName("user/user-manage");

    }
}
