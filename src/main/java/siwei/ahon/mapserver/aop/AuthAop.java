package siwei.ahon.mapserver.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import siwei.ahon.mapserver.expection.BaseException;
import siwei.ahon.mapserver.model.PrjConfig;
import siwei.ahon.mapserver.model.UserRight;
import siwei.ahon.mapserver.util.RedisUtils;
import siwei.ahon.mapserver.model.PrjConfig;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@Component
@Aspect
public class AuthAop {
    @Resource
    private HttpServletRequest request;

    @Resource
    RedisUtils redisUtils;

    @Value("${authValue.adminCode}")
    String adminCode;

    @Before("(execution(* siwei.ahon.mapserver.services.Impl.UserRightServiceImpl.*(..)) || " +
            "execution(* siwei.ahon.mapserver.services.Impl.UserServiceImpl.getUserList(..)))&& " +
            "!execution(* siwei.ahon.mapserver.services.Impl.UserRightServiceImpl.getUserPermissions(..))")
    public void beforeUserRightService(JoinPoint joinPoint){
        String token = request.getHeader("token");
        if (!redisUtils.hasKey(token)){
            throw new BaseException("用户未登录");
        }else {
            UserRight userRight = (UserRight)redisUtils.get(token);
            if (isEmpty(userRight) || !userRight.getGroups().equals(adminCode)) throw new BaseException("权限不足");
        }
    }
    @Before("execution(* siwei.ahon.mapserver.services.Impl.PrjConfigServiceImpl.updatePrjConfig(..)) or" +
            "execution(* siwei.ahon.mapserver.services.Impl.PrjConfigServiceImpl.deletePrjConfig(..))")
    public  void beforePrjConfigService(JoinPoint joinPoint){
        PrjConfig prjConfig = null;
        for (Object arg : joinPoint.getArgs()) {
            // request/response无法使用toJSON
            if (arg instanceof PrjConfig) {
                prjConfig = (PrjConfig) arg;
            }
        }
        if (isEmpty(prjConfig)) throw new BaseException("未能传入有效项目");
        String token = request.getHeader("token");
        UserRight userRight = (UserRight)redisUtils.get(token);
        if (isEmpty(userRight)) throw new BaseException("没有权限");
        boolean contains = userRight.getGroups().contains(prjConfig.getId().toString());
        if (!contains && !userRight.getGroups().equals(adminCode)  ) throw new BaseException("权限不足，没有管理此项目的权限");
    }
    @Before("execution(* siwei.ahon.mapserver.services.Impl.PrjConfigServiceImpl.addPrjConfig(..)) or" +
            "execution(* siwei.ahon.mapserver.services.Impl.ModulePermissionServiceImpl.*(..))")
    public void beforeAddPrjConfig(JoinPoint joinPoint){

        String token = request.getHeader("token");
        if (!redisUtils.hasKey(token)){
            throw new BaseException("用户未登录");
        }else {
            UserRight userRight = (UserRight)redisUtils.get(token);
            if (isEmpty(userRight) || !userRight.getGroups().equals(adminCode)) throw new BaseException("非管理员，不能添加项目");
        }
    }
}
