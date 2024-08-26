package com.hekf.web.borrow.controller;

import com.hekf.base.exception.NotEnoughException;
import com.hekf.base.exception.OperationFailureException;
import com.hekf.base.utils.MyResult;
import com.hekf.base.utils.MyUtils;
import com.hekf.web.book.model.BookInfo;
import com.hekf.web.book.service.BookInfoService;
import com.hekf.web.borrow.model.Borrow;
import com.hekf.web.borrow.service.BorrowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject bms
 * @BelongsPackage com.hekf.web.borrow.controller
 * @description: 借阅图书controller
 * @author: hekf
 * @create: 2024/8/24 09:29
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/borrow")
@Api(value = "BorrowController", tags = {"借阅图书操作接口"})
public class BorrowController {

    @Autowired
    BorrowService borrowService;
    @Autowired
    BookInfoService bookInfoService;

    /**
    * @author : hkf
    * @description : 分页查询借阅图书
    * @date : 2024/8/24 10:05
    * @param : {page, limit, userid, bookid}
    * @return :
    */
    @GetMapping(value = "/queryBorrowsByPage")
    @ApiOperation("分页查询借阅图书接口")
    @ApiImplicitParam(name = "封装params入参", value = "{page, limit, userid, bookid}")
    public Map<String, Object> queryBorrowsByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
        int count = borrowService.getSearchCount(params);
        List<Borrow> borrows = borrowService.searchBorrowsByPage(params);
        return MyResult.getListResultMap(0, "success", count, borrows);
    }

    /**
    * @author : hkf
    * @description : 添加借阅
    * @date : 2024/8/24 10:20
    * @param :
    * @return :
    */
    @PostMapping(value = "/addBorrow")
    @ApiOperation("添加借阅接口")
    @ApiImplicitParam(name = "borrow", value = "borrow")
    public Integer addBorrow(@RequestBody Borrow borrow){
        return borrowService.addBorrow(borrow);
    }

    /**
    * @author : hkf
    * @description : 获得数量
    * @date : 2024/8/24 10:43
    * @param :
    * @return :
    */
    @GetMapping(value = "/getCount")
    @ApiOperation("获得数量接口")
    public Integer getCount(){
        return borrowService.getCount();
    }

    /**
    * @author : hkf
    * @description : 删除借阅
    * @date : 2024/8/24 11:02
    * @param :
    * @return :
    */
    @DeleteMapping(value = "/deleteBorrow")
    @ApiOperation("删除借阅接口")
    @ApiImplicitParam(name = "borrow对象", value = "borrow")
    public Integer deleteBorrow(@RequestBody Borrow borrow){
        return borrowService.deleteBorrow(borrow);
    }

    /**
    * @author : hkf
    * @description : 删除选中借阅
    * @date : 2024/8/24 11:11
    * @param :
    * @return :
    */
    @DeleteMapping(value = "/deleteBorrows")
    @ApiOperation("删除选中借阅")
    @ApiImplicitParam(name = "borrow对象", value = "borrow")
    public Integer deleteBorrows(@RequestBody List<Borrow> borrows){
        return borrowService.deleteBorrows(borrows);
    }

    /**
    * @author : hkf
    * @description : 更新借阅
    * @date : 2024/8/24 11:33
    * @param :
    * @return :
    */
    @PutMapping(value = "/updateBorrow")
    @ApiOperation("更新借阅接口")
    @ApiImplicitParam(name = "borrow对象", value = "borrow")
    public Integer updateBorrow(@RequestBody Borrow borrow){
        return borrowService.updateBorrow(borrow);
    }

    /**
    * @author : hkf
    * @description : 借阅操作
    * @date : 2024/8/24 11:52
    * @param :
    * @return :
    */
    @PostMapping(value = {"/borrowBook", "/reader/borrowBook"})
    @Transactional
    @ApiOperation("借阅操作接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "用户id", dataType = "Integer"),
            @ApiImplicitParam(name = "bookid", value = "图书id",  dataType = "Integer")
    })
    public Integer borrowBook(Integer userid, Integer bookid){
        try{
            // 查询该书的情况
            BookInfo theBook = bookInfoService.queryBookInfoById(bookid);

            if(theBook == null) {  // 图书不存在
                throw new NullPointerException("图书" + bookid + "不存在");
            } else if(theBook.getIsborrowed() == 1) {  // 已经被借
                throw new NotEnoughException("图书" + bookid + "库存不足（已经被借走）");
            }

            // 更新图书表的isBorrowed
            BookInfo bookInfo = new BookInfo();
            bookInfo.setBookid(bookid);
            bookInfo.setIsborrowed((byte) 1);
            Integer res2 = bookInfoService.updateBookInfo(bookInfo);
            if(res2 == 0) throw new OperationFailureException("图书" + bookid + "更新被借信息失败");

            // 添加一条记录到borrow表
            Borrow borrow = new Borrow();
            borrow.setUserid(userid);
            borrow.setBookid(bookid);
            borrow.setBorrowtime(new Date(System.currentTimeMillis()));
            Integer res1 = borrowService.addBorrow2(borrow);
            if(res1 == 0) throw new OperationFailureException("图书" + bookid + "添加借阅记录失败");

        } catch (Exception e) {
            System.out.println("发生异常，进行手动回滚");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
    * @author : hkf
    * @description : 还书操作
    * @date : 2024/8/24 13:02
    * @param :
    * @return :
    */
    @PostMapping(value = {"/returnBook", "/reader/returnBook"})
    @Transactional
    @ApiOperation("还书操作接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "borrowid", value = "id标识", dataType = "Integer"),
            @ApiImplicitParam(name = "bookid", value = "图书id",  dataType = "Integer")
    })
    public Integer returnBook(Integer borrowid, Integer bookid){
        try {
            // 查询该书的情况
            BookInfo theBook = bookInfoService.queryBookInfoById(bookid);
            // 查询借书的情况
            Borrow theBorrow = borrowService.queryBorrowsById(borrowid);

            if(theBook == null) {  // 图书不存在
                throw new NullPointerException("图书" + bookid + "不存在");
            } else if(theBorrow == null) {   //结束记录不存在
                throw new NullPointerException("借书记录" + bookid + "不存在");
            } else if(theBorrow.getReturntime() != null) {  // 已经还过书
                throw new NotEnoughException("图书" + bookid + "已经还过了");
            }

            // 更新图书表的isBorrowed
            BookInfo bookInfo = new BookInfo();
            bookInfo.setBookid(bookid);
            bookInfo.setIsborrowed((byte) 0);
            Integer res2 = bookInfoService.updateBookInfo(bookInfo);
            if(res2 == 0) throw new OperationFailureException("图书" + bookid + "更新被借信息失败");

            // 更新Borrow表，更新结束时间
            Borrow borrow = new Borrow();
            borrow.setBorrowid(borrowid);
            borrow.setReturntime(new Date(System.currentTimeMillis()));
            Integer res1 = borrowService.updateBorrow2(borrow);
            if(res1 == 0) throw new OperationFailureException("图书" + bookid + "更新借阅记录失败");

        } catch (Exception e) {
            System.out.println("发生异常，进行手动回滚");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

}
