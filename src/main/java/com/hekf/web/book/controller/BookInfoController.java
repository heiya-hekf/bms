package com.hekf.web.book.controller;

import com.hekf.base.utils.MyResult;
import com.hekf.base.utils.MyUtils;
import com.hekf.web.book.model.BookInfo;
import com.hekf.web.book.service.BookInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @BelongsProject bms
 * @BelongsPackage com.hekf.web.book.controller
 * @description: 图书信息controller
 * @author: hekf
 * @create: 2024/8/22 14:09
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/bookInfo")
@Api(value = "BookInfoController", tags = {"图书信息接口"})
public class BookInfoController {

    @Autowired
    BookInfoService bookInfoService;

    /**
    * @author : hkf
    * @description : 获取图书数量
    * @date : 2024/8/22 14:15
    * @param :
    * @return :
    */
    @GetMapping(value = "/getCount")
    @ApiOperation("获取图书数量接口")
    public Integer getCount(){
        return bookInfoService.getCount();
    }

    /**
    * @author : hkf
    * @description : 查询所有图书信息
    * @date : 2024/8/22 14:40
    * @param :
    * @return :
    */
    @GetMapping(value = "/queryBookInfos")
    @ApiOperation("查询所有图书信息接口")
    public List<BookInfo> queryBookInfos(){
        return bookInfoService.queryBookInfos();
    }

    /**
    * @author : hkf
    * @description : 分页搜索查询图书信息
    * @date : 2024/8/22 15:55
    * @param : {page, limit, bookname, bookauthor, booktypeid}
    * @return :
    */
    @GetMapping(value = "/queryBookInfosByPage")
    @ApiOperation("分页搜索查询图书信息接口")
    @ApiImplicitParam(name = "封装params入参", value = "{page, limit, bookname, bookauthor, booktypeid}")
    public Map<String, Object> queryBookInfosByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
        int count = bookInfoService.getSearchCount(params);  // 获得总数
        List<BookInfo> bookInfos = bookInfoService.searchBookInfosByPage(params);  // 分页查询
        return MyResult.getListResultMap(0, "success", count, bookInfos);
    }

    /**
    * @author : hkf
    * @description : 添加图书信息
    * @date : 2024/8/22 16:01
    * @param :
    * @return :
    */
    @PostMapping(value = "/addBookInfo")
    @ApiOperation("添加图书信息接口")
    @ApiImplicitParam(name = "bookInfo对象", value = "bookInfo")
    public Integer addBookInfo(@RequestBody BookInfo bookInfo){
        return bookInfoService.addBookInfo(bookInfo);
    }

    /**
    * @author : hkf
    * @description : 删除图书信息
    * @date : 2024/8/22 16:20
    * @param :
    * @return :
    */
    @DeleteMapping(value = "/deleteBookInfo")
    @ApiOperation("删除图书信息接口")
    @ApiImplicitParam(name = "bookInfo", value = "bookInfo")
    public Integer deleteBookInfo(@RequestBody BookInfo bookInfo){
        return bookInfoService.deleteBookInfo(bookInfo);
    }

    /**
    * @author : hkf
    * @description : 删除选中图书信息
    * @date : 2024/8/22 16:44
    * @param :
    * @return :
    */
    @DeleteMapping(value = "/deleteBookInfos")
    @ApiOperation("删除选中图书信息接口")
    @ApiImplicitParam(name = "bookInfos", value = "bookInfos")
    public Integer deleteBookInfos(@RequestBody List<BookInfo> bookInfos){
        return bookInfoService.deleteBookInfos(bookInfos);
    }

    /**
    * @author : hkf
    * @description : 更新图书信息
    * @date : 2024/8/22 17:09
    * @param :
    * @return :
    */
    @PutMapping(value = "/updateBookInfo")
    @ApiOperation("更新图书信息接口")
    @ApiImplicitParam(name = "bookInfo对象", value = "bookInfo")
    public Integer updateBookInfo(@RequestBody BookInfo bookInfo){
        return bookInfoService.updateBookInfo(bookInfo);
    }
}
