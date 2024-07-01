package siwei.ahon.mapserver.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siwei.ahon.mapserver.annotation.FilterFiledHelper;
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

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class UserRightServiceImpl implements UserRightService {


    @Resource
    UserRightMapper userRightMapper;

    @Resource
    FilterFiledHelper filedHelper;

    @Resource
    RedisUtils redisUtils;

    @Resource
    UserMapper userMapper;

    @Resource
    private HttpServletRequest request;
    @Override
    public PageData<UserRight> getUserPermissionsList(UserRight userRight, PageFilterPojo pf) {

        QueryWrapper<UserRight> userRightQueryWrapper = new QueryWrapper<>();
        QueryWrapper queryWrapper = filedHelper.getQueryWrapper(userRightQueryWrapper, userRight);
        Page<UserRight> userRightPage = new Page<UserRight>(pf.getPageNum(), pf.getPageSize());
        IPage page = userRightMapper.selectPage(userRightPage, queryWrapper);
        PageData pd = new PageData(page);
        return pd;
    }

    @Override
    public Integer addUserPermissions(UserRight userRight) {
        userRightMapper.insert(userRight);
        return userRight.getId();
    }

    @Override
    public UserRight getUserPermissions(User user) {
        if (!isEmpty(user.getId())){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id",user.getId());
            UserRight userRight = userRightMapper.selectOne(queryWrapper);
            return userRight;
        }
        String token = request.getHeader("token");
        if(!redisUtils.hasKey(token)) throw new BaseException("该用户未登录或token不存在");
            UserRight userRight = (UserRight) redisUtils.get(token);
//            System.out.println(userRight);
            return userRight;
    }

    @Override
    public Integer updateUserPermissions(UserRight userRight) {
        if (isEmpty(userRight.getUserId())){
            throw new BaseException("userID不能为空");
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userRight.getUserId());
        Integer update = userRightMapper.update(userRight, queryWrapper);
        if (update == 0){
            this.addUserPermissions(userRight);
        }
        User user = userMapper.selectById(userRight.getUserId());
        String token =user.getToken();

        if (redisUtils.hasKey(token)){
            redisUtils.set(token,userRight);
        }
        return userRight.getId();
    }

}