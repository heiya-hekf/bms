package com.hekf.web.user.controller;

import com.hekf.base.utils.MyResult;
import com.hekf.base.utils.MyUtils;
import com.hekf.base.utils.TokenProcessor;
import com.hekf.web.user.model.User;
import com.hekf.web.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject bms
 * @BelongsPackage com.hekf.web.user.controller
 * @description: 用户管理操作controller
 * @author: hekf
 * @create: 2024/8/24 09:29
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/user")
@Api(value = "UserController", tags = {"用户管理操作接口"})
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    /**
    * @author : hkf
    * @description : 用户登录
    * @date : 2024/8/25 18:21
    * @param :
    * @return :
    */
    @PostMapping(value = "/login")
    @ApiOperation("用户登录接口")
    @ApiImplicitParam(name = "用户对象", value = "user")
    public Map<String, Object> login(@RequestBody User user) {
        // 登录操作，先简单保存，后面用md5进行加密处理再调整逻辑
        User userObj = userService.login(user);
        if(userObj == null) {   // 账号或密码错误
            // 返回结果对象
            return MyResult.getResultMap(420, "账号或密码错误");
        } else {    // 账号密码正确
            // 创建token
            String token = TokenProcessor.getInstance().makeToken();
            // 保存到Redis
            userService.saveUser(token, userObj);
            // 返回结果对象
            return MyResult.getResultMap(200, "登录成功",
                    new HashMap<String, String>(){{ put("token", token); }});
        }
    }

    /**
    * @author : hkf
    * @description : 查看用户信息
    * @date : 2024/8/25 18:22
    * @param :
    * @return :
    */
    @GetMapping(value = "/info")
    @ApiOperation("查看用户信息接口")
    @ApiImplicitParam(name = "token", value = "toekn")
    public Map<String, Object> info(String token) {
        // 从redis中取用户
        User user = userService.getUser(token);
        if(user == null) {  // 获取失败
            return MyResult.getResultMap(420, "获取用户信息失败");
        } else {    // 获取成功
            return MyResult.getResultMap(200, "获取用户信息成功", user);
        }
    }

    /**
    * @author : hkf
    * @description : 退出登录
    * @date : 2024/8/25 18:22
    * @param :
    * @return :
    */
    @PostMapping(value = "/logout")
    @ApiOperation("用户退出接口")
    @ApiImplicitParam(name = "token", value = "toekn")
    public Map<String, Object> logout(String token) {
        // 从redis中移除用户
        userService.removeUser(token);
        return MyResult.getResultMap(200, "退出登录成功" );
    }

    /**
    * @author : hkf
    * @description : 注册
    * @date : 2024/8/25 18:22
    * @param :
    * @return :
    */
    @PostMapping(value = "/register")
    @ApiOperation("用户注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码",  dataType = "String")
    })
    public Integer register(String username, String password){
        return userService.register(username, password);
    }

    /**
    * @author : hkf
    * @description : 修改密码 先简单处理后期考虑验密md5
    * @date : 2024/8/25 18:23
    * @param :
    * @return :
    */
    @PostMapping(value = {"/alterPassword", "reader/alterPassword"})
    @ApiOperation("修改密码接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "用户id", dataType = "Integer"),
            @ApiImplicitParam(name = "username", value = "用户名",  dataType = "String"),
            @ApiImplicitParam(name = "isadmin", value = "是否管理员",  dataType = "Byte"),
            @ApiImplicitParam(name = "oldPassword", value = "旧密码",  dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码",  dataType = "String"),
    })
    public Integer alterPassword(Integer userid, String username, Byte isadmin,
                                 String oldPassword, String newPassword){
        //检查旧密码是否正确
        User userObj = new User();
        userObj.setUserid(userid);
        userObj.setUsername(username);
        userObj.setUserpassword(oldPassword);
        userObj.setIsadmin(isadmin);

        User user = userService.login(userObj);
        if(user == null) {  //旧密码不正确
            return 0;
        } else {    //旧密码正确，设置新密码
            userService.setPassword(userObj.getUserid(), newPassword);
            return 1;
        }
    }

    /**
    * @author : hkf
    * @description : 获取用户数量
    * @date : 2024/8/25 18:23
    * @param :
    * @return :
    */
    @GetMapping(value = "/getCount")
    @ApiOperation("获取用户数量接口")
    public Integer getCount(){
        return userService.getCount();
    }

    /**
    * @author : hkf
    * @description : 查询所有用户
    * @date : 2024/8/25 18:23
    * @param :
    * @return :
    */
    @GetMapping(value = "/queryUsers")
    @ApiOperation("查询所有用户接口")
    public List<User> queryUsers(){
        return userService.queryUsers();
    }

    /**
    * @author : hkf
    * @description : 分页查询用户
    * @date : 2024/8/25 18:24
    * @param : {page, limit, username}
    * @return :
    */
    @GetMapping(value = "/queryUsersByPage")
    @ApiOperation("分页查询用户接口")
    @ApiImplicitParam(name = "封装params入参", value = "{page, limit, username}")
    public Map<String, Object> queryUsersByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
        int count = userService.getSearchCount(params);
        List<User> users = userService.searchUsersByPage(params);
        return MyResult.getListResultMap(0, "success", count, users);
    }

    /**
    * @author : hkf
    * @description : 添加用户
    * @date : 2024/8/25 18:24
    * @param :
    * @return :
    */
    @PostMapping(value = "/addUser")
    @ApiOperation("添加用户接口")
    @ApiImplicitParam(name = "user对象", value = "user")
    public Integer addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    /**
    * @author : hkf
    * @description : 删除用户
    * @date : 2024/8/25 18:24
    * @param :
    * @return :
    */
    @DeleteMapping(value = "/deleteUser")
    @ApiOperation("删除用户接口")
    @ApiImplicitParam(name = "user对象", value = "user")
    public Integer deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }

    /**
    * @author : hkf
    * @description : 删除选中用户
    * @date : 2024/8/25 18:24
    * @param :
    * @return :
    */
    @DeleteMapping(value = "/deleteUsers")
    @ApiOperation("删除选中用户接口")
    @ApiImplicitParam(name = "user对象", value = "user")
    public Integer deleteUsers(@RequestBody List<User> users){
        return userService.deleteUsers(users);
    }

    /**
    * @author : hkf
    * @description : 更新用户
    * @date : 2024/8/25 18:24
    * @param :
    * @return :
    */
    @PutMapping(value = "/updateUser")
    @ApiOperation("更新用户接口")
    @ApiImplicitParam(name = "user对象", value = "user")
    public Integer updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    /**
    * @author : hkf
    * @description : 图片上传
    * @date : 2024/8/25 18:25
    * @param :
    * @return :
    */
    private String myUpdate(HttpServletRequest req, String dirName) {
        String res = null;  // 返回网络路径
        try {
            String staticDir = ResourceUtils.getURL("classpath:").getPath() + "static";  // 得到classes/static目录
            String localDir = staticDir + "/" + dirName;   //本地目录
            // 如果结果目录不存在，则创建目录
            File resDirFile = new File(localDir);
            if(!resDirFile.exists()) {
                boolean flag = resDirFile.mkdirs();
                if(!flag) throw new RuntimeException("创建结果目录失败");
            }
            //先判断上传的数据是否多段数据（只有是多段的数据，才是文件上传的）
            if (ServletFileUpload.isMultipartContent(req)) {
                // 创建 FileItemFactory 工厂实现类
                FileItemFactory fileItemFactory = new DiskFileItemFactory();
                // 创建用于解析上传数据的工具类 ServletFileUpload 类
                ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
                // 解析上传的数据，得到每一个表单项 FileItem
                List<FileItem> list = servletFileUpload.parseRequest(new ServletRequestContext(req));
                // 循环判断，每一个表单项，是普通类型，还是上传的文件
                for (FileItem fileItem : list) {
                    if ( !fileItem.isFormField()) { // 是上传的文件
                        // 上传的文件
                        logger.info("表单项的 name 属性值：{}", fileItem.getFieldName());
                        logger.info("上传的文件名：{}", fileItem.getName());
                        // 加个时间戳防止重名
                        String newFileName = System.currentTimeMillis() + fileItem.getName();
                        // 写文件
                        File file = new File(localDir + "/" + newFileName);
                        fileItem.write(file);
                        // 返回值
                        res = "http://localhost:6001/bms/" + dirName + "/" + newFileName;
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
    * @author : hkf
    * @description : 修改图片上传
    * @date : 2024/8/25 18:25
    * @param :
    * @return :
    */
    @PostMapping("/updateImg")
    @ResponseBody
    @ApiOperation("修改图片上传接口")
    @ApiImplicitParam(name = "request请求对象", value = "req")
    public Map<String,Object> updateImg(HttpServletRequest req){
        String resPath = myUpdate(req, "pictures");
        Map<String,Object> res = new HashMap<>();
        res.put("code",0);
        res.put("data", resPath);
        return res;
    }


}
