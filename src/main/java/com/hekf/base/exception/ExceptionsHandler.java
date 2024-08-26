package com.hekf.base.exception;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.hekf.base.common.response.ResponseData;
import com.hekf.base.common.response.ResultCode;
import com.hekf.base.common.response.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @ClassName ExceptionsHandler
 * @Description 全局异常拦截 返回json数据响应
 * @Author PC-002
 * @created 2021/4/21 15:54
 * @Version 1.0
 */
@RestControllerAdvice
@ResponseBody
public class ExceptionsHandler {

    private static Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    public ResponseData requestParamsNotFound(){
        return ResultGenerator.failResult("No Such Method Exception");
    }

    /** 未知方法异常 */
    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseData noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        logger.error("未知方法异常：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("No Such Method Exception");
    }
    /** 运行时异常 */
    @ExceptionHandler(RuntimeException.class)
    public ResponseData runtimeExceptionHandler(RuntimeException ex) {
        logger.error("运行时异常：{}", ex.getMessage(), ex);
        return ResultGenerator.failResult("Runtime Exception");
    }
    /** 空指针异常 */
    @ExceptionHandler(NullPointerException.class)
    public ResponseData nullPointerExceptionHandler(NullPointerException ex) {
        logger.error("空指针异常：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Null Pointer Exception");
    }
    /** 数组越界异常 */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseData indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        logger.error("数组越界异常：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Index Out Of Bounds Exception");
    }
    /** 类型转换异常 */
    @ExceptionHandler(ClassCastException.class)
    public ResponseData classCastExceptionHandler(ClassCastException ex) {
        logger.error("类型转换异常：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Class Cast Exception");
    }
    /** IO异常 */
    @ExceptionHandler(IOException.class)
    public ResponseData ioExceptionHandler(IOException ex) {
        logger.error("IO异常：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("IO Exception");
    }
    /** 请求方法不支持 */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseData httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        logger.error("请求方法不支持：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Http Request Method Not Supported Exception");
    }
    /** 文件未找到异常 */
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseData fileNotFoundException(FileNotFoundException ex) {
        logger.error("文件未找到异常：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("File Not Found Exception");
    }
    /** 数字格式异常 */
    @ExceptionHandler(NumberFormatException.class)
    public ResponseData numberFormatException(NumberFormatException ex) {
        logger.error("数字格式异常：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Number Format Exception");
    }
    /** 安全异常 */
    @ExceptionHandler(SecurityException.class)
    public ResponseData securityException(SecurityException ex) {
        logger.error("安全异常：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Security Exception");
    }
    /** sql异常 */
    @ExceptionHandler(SQLException.class)
    public ResponseData sqlException(SQLException ex) {
        logger.error("sql异常：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("SQL Exception");
    }
    /** 类型不存在异常 */
    @ExceptionHandler(TypeNotPresentException.class)
    public ResponseData TypeNotPresentException(TypeNotPresentException ex) {
        logger.error("类型不存在异常：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Type Not Present Exception");
    }
    /** sql语法错误异常 */
    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseData badSqlGrammarException(BadSqlGrammarException ex) {
        logger.error("sql语法错误异常：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Bad Sql Grammar Exception");
    }
    /** sql数据插入异常 */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseData dataIntegrityViolationException(DataIntegrityViolationException ex){
        logger.error("sql数据插入异常：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Data Integrity Violation Exception");
    }
    /** 无法注入bean异常 */
    @ExceptionHandler(NoSuchBeanDefinitionException.class)
    public ResponseData noSuchBeanDefinitionException(NoSuchBeanDefinitionException ex) {
        logger.error("无法注入bean异常 ：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("No Such Bean Definition Exception");
    }
    /** Http消息不可读异常 */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseData requestNotReadable(HttpMessageNotReadableException ex) {
        logger.error("400错误..requestNotReadable：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Http Message Not Readable Exception");
    }
    /** 400错误 */
    @ExceptionHandler({TypeMismatchException.class})
    public ResponseData requestTypeMismatch(TypeMismatchException ex) {
        logger.error("400错误..TypeMismatchException：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Type Mismatch Exception");
    }
    /** 500错误 */
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public ResponseData server500(RuntimeException ex) {
        logger.error("500错误：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Server Exception");
    }
    /** 栈溢出 */
    @ExceptionHandler({StackOverflowError.class})
    public ResponseData requestStackOverflow(StackOverflowError ex) {
        logger.error("栈溢出：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Stack Overflow Error");
    }
    /** 除数不能为0 */
    @ExceptionHandler({ArithmeticException.class})
    public ResponseData arithmeticException(ArithmeticException ex) {
        logger.error("除数不能为0：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Arithmetic Exception");
    }
    /** 其他错误 */
    @ExceptionHandler({Exception.class})
    public ResponseData exception(Exception ex) {
        logger.error("其他错误：{} ", ex.getMessage(), ex);
        return ResultGenerator.failResult("Exception");
    }

    @ExceptionHandler({TokenExpiredException.class})
    public ResponseData exception(TokenExpiredException ex) {
        logger.error("jwtToken已经过期：{} ", ex.getMessage(), ex);
        return ResultGenerator.freeResult(ResultCode.JWT_TOKEN_EXPIRED);
    }
    @ExceptionHandler({SignatureVerificationException.class})
    public ResponseData exception(SignatureVerificationException ex) {
        logger.error("jwt 签名错误：{} ", ex.getMessage(), ex);
        return ResultGenerator.freeResult(ResultCode.JWT_SIGNATURE_VERIFICATION);
    }
    @ExceptionHandler({AlgorithmMismatchException.class})
    public ResponseData exception(AlgorithmMismatchException ex) {
        logger.error("jwt 加密算法不匹配：{} ", ex.getMessage(), ex);
        return ResultGenerator.freeResult(ResultCode.JWT_ALGORITHM_MISMATCH);
    }
    @ExceptionHandler({JWTDecodeException.class})
    public ResponseData exception(JWTDecodeException ex) {
        logger.error("jwt 解析异常：{} ", ex.getMessage(), ex);
        return ResultGenerator.freeResult(ResultCode.JWT_DECODE_EXCEPTION);
    }
    @ExceptionHandler({BlacklistException.class})
    public ResponseData exception(BlacklistException ex) {
        logger.error("token黑名单，请联系管理员：{} ", ex.getMessage(), ex);
        return ResultGenerator.freeResult(ResultCode.JWT_TOKEN_BLACKLIST_EXCEPTION);
    }
    @ExceptionHandler({TokenNullException.class})
    public ResponseData exception(TokenNullException ex) {
        logger.error("token不存在，请确认：{} ", ex.getMessage(), ex);
        return ResultGenerator.freeResult(ResultCode.JWT_TOKEN_NOT_FOUND);
    }

}
