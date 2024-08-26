//package com.seeyii.base.exception;
//
//import com.seeyii.base.common.response.ResponseData;
//import com.seeyii.base.common.response.ResultGenerator;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @ClassName ErrorHandler
// * @Description 处理404异常请求，进行异常捕获封装返回
// * @Author PC-002
// * @created 2021/4/21 17:15
// * @Version 1.0
// */
//@RestController
//public class ErrorHandler implements ErrorController {
//
//    private static Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);
//
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
//
//    @RequestMapping(value = {"/error"})
//    public ResponseData error(HttpServletRequest request, HttpServletResponse response) {
//        int status = response.getStatus();
//        if (status == 404) {
//            logger.error("运行时异常：{}");
//            return ResultGenerator.failResult("exception in request");
//        }
//        return ResultGenerator.failResult("exception in request");
//    }
//}
