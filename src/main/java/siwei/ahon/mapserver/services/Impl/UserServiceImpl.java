package siwei.ahon.mapserver.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import siwei.ahon.mapserver.annotation.FilterFiledHelper;
import siwei.ahon.mapserver.pojo.dto.UserDTO;
import siwei.ahon.mapserver.expection.BaseException;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.mapper.UserMapper;
import siwei.ahon.mapserver.mapper.UserRightMapper;
import siwei.ahon.mapserver.model.User;
import siwei.ahon.mapserver.model.UserRight;
import siwei.ahon.mapserver.pojo.PageFilterPojo;
import siwei.ahon.mapserver.services.UserRightService;
import siwei.ahon.mapserver.services.UserService;
import siwei.ahon.mapserver.util.RedisUtils;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import static org.springframework.util.StringUtils.isEmpty;
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Resource
    FilterFiledHelper filedHelper;

    @Resource
    RedisUtils redisUtils;

    @Resource
    UserRightService userRightService;

    @Resource
    UserRightMapper userRightMapper;
    @Resource
    private HttpServletRequest request;

    @Override
    public PageData<User> getUserList(User user, PageFilterPojo pf) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(userQueryWrapper, user);
        QueryWrapper<UserRight> userRightQueryWrapper = new QueryWrapper<>();
        userRightQueryWrapper.eq("`groups`","all");
        List<UserRight> userRights = userRightMapper.selectList(userRightQueryWrapper);
        System.out.println(userRights);
        if (!isEmpty(userRights))
        for (UserRight userRight : userRights) {
            queryWrapper.ne("id",userRight.getUserId());
        }
        timeFilter(queryWrapper, pf);
        queryWrapper.orderByDesc("gmt_login");
        Page<User> userPage = new Page<User>(pf.getPageNum(), pf.getPageSize());
        IPage page = userMapper.selectPage(userPage, queryWrapper);
        PageData pd = new PageData(page);
        return pd;
    }

    @Override
    public Integer addUser(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        User valid = userMapper.selectOne(queryWrapper);
        if (!isEmpty(valid)) throw new BaseException("该用户已存在，请勿重复注册");
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        user.setGmtLogin(new Date());
        user.setToken(UUID.randomUUID().toString());
        userMapper.insert(user);
        UserRight userRight = new UserRight();
        userRight.setUserId(user.getId());
        userRight.setWebModuleConfig("4,5");
        userRightMapper.insert(userRight);
        return user.getId();
    }

    @Override
    public Integer updateUser(User user) {
        if (isEmpty(user.getId())){
            throw new BaseException("用户不存在");
        }

        userMapper.updateById(user);
        user.setGmtModified(new Date());
        return user.getId();
    }

    @Override
    public Integer deleteUser(User user) {
        if (isEmpty(user.getId())){
            throw new BaseException("用户不存在");
        }
        userMapper.deleteById(user);
        return user.getId();
    }

    @Override
    public UserDTO userLogin(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        queryWrapper.eq("password",user.getPassword());
        User userT = userMapper.selectOne(queryWrapper);
        if (isEmpty(userT)){
            throw new BaseException("用户名或密码错误");
        }else {
            userT.setGmtLogin(new Date());
            UserRight userPermissions = userRightService.getUserPermissions(userT);
            redisUtils.set(userT.getToken(),userPermissions);
            userMapper.updateById(userT);
            UserDTO  userDTO = new UserDTO();
            BeanUtils.copyProperties(userT,userDTO);
            userDTO.setGroups(userPermissions.getGroups());
            userDTO.setWebModuleConfig(userPermissions.getWebModuleConfig());

            return userDTO;
        }
    }

    @Override
    public User findUserByToken(String token) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("token",token);
        User user = userMapper.selectOne(queryWrapper);
        if (isEmpty(user)){
            return null;
        }else {
            return user;
        }
    }

    @Override
    public String userLoginOut(User user) {
        String token = request.getHeader("token");
        if (redisUtils.hasKey(token)){
            redisUtils.del(token);
            User userByToken = this.findUserByToken(token);
//            userByToken.setToken(null);
            System.out.println("注销用户"+userByToken);
//            userMapper.updateById(userByToken);
            return "注销成功";
        }
        return "该用户未登录";
    }

    @Override
    public String getUserToken(User user) {
        if (isEmpty(user)) throw new BaseException("id不能为空");
        User userT = userMapper.selectById(user.getId());
        if (isEmpty(userT))  throw new BaseException("对应用户不存在");
        if (isEmpty(userT.getToken())) return "该用户没有登录过,请重新生成";
//        if (!redisUtils.hasKey(userT.getToken())) return "token已经过期请重新生成";
        return userT.getToken();
    }

    @Override
    @Transactional
    public String updateUserToken(User user) {
        if (isEmpty(user)) throw new BaseException("id不能为空");
        User userT = userMapper.selectById(user.getId());
        if (userT.getId().equals(1)){
            throw new BaseException("该用户token不允许更改");
        }

        if (isEmpty(userT))  throw new BaseException("对应用户不存在");
        if (!isEmpty(userT.getToken())){
            if (redisUtils.hasKey(user.getToken()))
                redisUtils.del(user.getToken());
        }
        String token = UUID.randomUUID().toString();
        userT.setToken(token);
        UserRight userPermissions = userRightService.getUserPermissions(userT);
        redisUtils.set(token,userPermissions);
        userMapper.updateById(userT);
        return token;
    }

    @Override
    public User verifyToken(String token) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token",token);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }


    private  void timeFilter(QueryWrapper qw, PageFilterPojo pf){
        qw.gt("gmt_login",pf.getsTime());
        qw.lt("gmt_login",pf.geteTime());
    }
}
