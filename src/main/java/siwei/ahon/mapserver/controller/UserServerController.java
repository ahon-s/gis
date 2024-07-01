package siwei.ahon.mapserver.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import siwei.ahon.mapserver.pojo.dto.UserDTO;
import siwei.ahon.mapserver.expection.MyResult;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.model.User;
import siwei.ahon.mapserver.model.UserRight;
import siwei.ahon.mapserver.pojo.PageFilterPojo;
import siwei.ahon.mapserver.services.UserRightService;
import siwei.ahon.mapserver.services.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping("/mapserver")
@CrossOrigin(allowedHeaders  = {"*"})
@Validated
public class UserServerController {
    @Resource
    UserService userService;

    @Resource
    UserRightService userRightService;

    /**用户的登录注册**/
    @PostMapping("/login")
    public MyResult UserLogin(@Valid User user){
        UserDTO userData = userService.userLogin(user);
        return MyResult.success(userData);
    }
    @PostMapping("/register")
    public MyResult UserRegister(@Valid  User user){
        userService.addUser(user);
        return MyResult.success(user.getId());
    }
    /**用户注销**/
    @GetMapping("/logout")
    public MyResult UserLogout(User user){
        String message = userService.userLoginOut(user);
        return MyResult.success(message);
    }


    /**查询用户**/
    @GetMapping("/getUserList")
    public MyResult getUserList(User user, PageFilterPojo pf){
        PageData<User> userList = userService.getUserList(user, pf);
        return MyResult.success(userList);
    }
    /**修改用户信息**/
    @PostMapping("/updateUser")
    public MyResult updateUser(@Valid User user){
        Integer result = userService.updateUser(user);
        return MyResult.success(result);
    }
    /**删除用户**/
    @PostMapping("/deleteUser")
    public MyResult deleteUserList(User user){
        Integer result = userService.deleteUser(user);
        return MyResult.success(result);
    }
    /**获取用户token**/
    @GetMapping("/getUserToken")
    public MyResult getUserToken(User user){
        String result = userService.getUserToken(user);
        return MyResult.success(result);
    }


    /**更新用户token**/
    @PostMapping("/updateUserToken")
    public MyResult updateUserToken(User user){
        String result = userService.updateUserToken(user);
        return MyResult.success(result);
    }

    /**获取用户权限**/
    @GetMapping("/getUserPermissions")
    public MyResult getUserPermissions(User user){
        UserRight userPermissions = userRightService.getUserPermissions(user);
        return MyResult.success(userPermissions);
    }
    @GetMapping("/getUserPermissionsList")
    public MyResult getUserPermissionsList(UserRight userRight,PageFilterPojo pf){
        PageData<UserRight> userPermissionsList = userRightService.getUserPermissionsList(userRight, pf);
        return MyResult.success(userPermissionsList);

    }
    /**添加权限**/
    @PostMapping("/addUserPermissions")
    public MyResult addUserPermissions(UserRight userRight){
        Integer result = userRightService.addUserPermissions(userRight);
        return MyResult.success(result);
    }

    /**更新权限**/
    @PostMapping("/updatePermissions")
    public MyResult updatePermissions(UserRight userRight){
        Integer result = userRightService.updateUserPermissions(userRight);
        return MyResult.success(result);
    }
    @GetMapping("/verifyToken")
    public MyResult verifyToken(String token){
        User user = userService.verifyToken(token);
        if (isEmpty(user)) return MyResult.fail("token无效");
        return MyResult.success("token有效");
    }

}
