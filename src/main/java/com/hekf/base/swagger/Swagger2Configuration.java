package com.hekf.base.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Configuration extends WebMvcConfigurationSupport {

    /*@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hekf.web.*.controller"))
                .paths(PathSelectors.any())
                .build();
    }*/

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) // SWAGGER_2
                .groupName("用户管理")
                .apiInfo(apiInfo())
                .select()
                // 此处自行修改为自己的 Controller 包路径
                .apis(RequestHandlerSelectors.basePackage("com.hekf.web.user.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setGlobalParameters());
    }

    @Bean
    public Docket createRestApi2() {
        return new Docket(DocumentationType.SWAGGER_2) // SWAGGER_2
                .groupName("借阅管理")
                .apiInfo(apiInfo())
                .select()
                // 此处自行修改为自己的 Controller 包路径
                .apis(RequestHandlerSelectors.basePackage("com.hekf.web.borrow.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setGlobalParameters());
    }

    @Bean
    public Docket createRestApi3() {
        return new Docket(DocumentationType.SWAGGER_2) // SWAGGER_2
                .groupName("图书管理")
                .apiInfo(apiInfo())
                .select()
                // 此处自行修改为自己的 Controller 包路径
                .apis(RequestHandlerSelectors.basePackage("com.hekf.web.book.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setGlobalParameters());
    }

    /**
     * 设置全局参数
     *
     * @return
     */
    private List<Parameter> setGlobalParameters() {
        List<Parameter> globalParameterList = new ArrayList<>();
        //Header中必需 token参数。非必填，传空也可以，一般业务登录拦截器校验 token是否合法
        ParameterBuilder tokenBuilder = new ParameterBuilder();
        tokenBuilder.name("token").description("用户 TOKEN参数")
                .required(false)// 非必填
                .modelRef(new ModelRef("string"))
                .parameterType("header");
        globalParameterList.add(tokenBuilder.build());
        return globalParameterList;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BMS图书管理系统")
                .description("后端接口-API文档")
                .version("1.0")
                .build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry r){
        // 解决静态资源无法访问
        r.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        // 解决swagger无法访问
        r.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        r.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        // 解决自定义接口文档无法访问
        r.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
    }

}