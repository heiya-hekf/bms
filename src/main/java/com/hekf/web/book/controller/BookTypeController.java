package com.hekf.web.book.controller;

import com.hekf.base.utils.MyResult;
import com.hekf.base.utils.MyUtils;
import com.hekf.web.book.model.BookType;
import com.hekf.web.book.service.BookTypeService;
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
 * @description: 图书类型controller
 * @author: hekf
 * @create: 2024/8/22 14:09
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/bookType")
@Api(value = "BookInfoController", tags = {"图书信息接口"})
public class BookTypeController {

    @Autowired
    BookTypeService bookTypeService;

    /**
    * @author : hkf
    * @description : 获取图书类型数量
    * @date : 2024/8/23 10:03
    * @param :
    * @return :
    */
    @GetMapping(value = "/getCount")
    @ApiOperation("获取图书类型数量")
    public Integer getCount(){
        return bookTypeService.getCount();
    }

    /**
    * @author : hkf
    * @description : 查询图书所有类型
    * @date : 2024/8/23 10:30
    * @param :
    * @return :
    */
    @GetMapping(value = {"/queryBookTypes", "/reader/queryBookTypes"})
    @ApiOperation("查询图书所有类型")
    public List<BookType> queryBookTypes(){
        return bookTypeService.queryBookTypes();
    }

    /**
    * @author : hkf
    * @description : 分页查询图书类型
    * @date : 2024/8/23 11:11
    * @param : {page, limit, booktypename}
    * @return :
    */
    @GetMapping(value = "/queryBookTypesByPage")
    @ApiOperation("分页查询图书类型接口")
    @ApiImplicitParam(name = "封装params入参", value = "{page, limit, booktypename}")
    public Map<String, Object> queryBookTypesByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
        int count = bookTypeService.getSearchCount(params);
        List<BookType> bookTypes = bookTypeService.searchBookTypesByPage(params);
        return MyResult.getListResultMap(0, "success", count, bookTypes);
    }

    /**
    * @author : hkf
    * @description : 添加图书类型
    * @date : 2024/8/23 12:12
    * @param :
    * @return :
    */
    @PostMapping(value = "/addBookType")
    @ApiOperation("添加图书类型接口")
    @ApiImplicitParam(name = "bookType", value = "bookType")
    public Integer addBookType(@RequestBody BookType bookType){
        return bookTypeService.addBookType(bookType);
    }

    /**
    * @author : hkf
    * @description : 删除类型
    * @date : 2024/8/23 12:25
    * @param :
    * @return :
    */
    @DeleteMapping(value = "/deleteBookType")
    @ApiOperation("删除类型接口")
    @ApiImplicitParam(name = "bookType", value = "bookType")
    public Integer deleteBookType(@RequestBody BookType bookType){
        return bookTypeService.deleteBookType(bookType);
    }

    /**
    * @author : hkf
    * @description : 删除选中类型
    * @date : 2024/8/23 12:45
    * @param :
    * @return :
    */
    @DeleteMapping(value = "/deleteBookTypes")
    @ApiOperation("删除选中类型接口")
    @ApiImplicitParam(name = "bookTypes", value = "bookTypes")
    public Integer deleteBookTypes(@RequestBody List<BookType> bookTypes){
        return bookTypeService.deleteBookTypes(bookTypes);
    }

    /**
    * @author : hkf
    * @description : 更新图书类型
    * @date : 2024/8/23 15:13
    * @param :
    * @return :
    */
    @PutMapping(value = "/updateBookType")
    @ApiOperation("更新图书类型接口")
    @ApiImplicitParam(name = "updateBookType", value = "updateBookType")
    public Integer updateBookType(@RequestBody BookType bookType){
        return bookTypeService.updateBookType(bookType);
    }
}
