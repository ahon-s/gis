package siwei.ahon.mapserver.interceptor;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import siwei.ahon.mapserver.expection.MyResult;
import siwei.ahon.mapserver.mapper.UserMapper;
import siwei.ahon.mapserver.model.User;
import siwei.ahon.mapserver.services.UserService;
import siwei.ahon.mapserver.util.RedisUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    RedisUtils redisUtils;

    @Resource
    UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //拿到token
        String token = request.getHeader("token");
        if (isEmpty(token))  {
            httpReturn(response, MyResult.authfail("请传入token"));
            return false;
        }

        if (!redisUtils.hasKey(token)){
            if (token.equals(userMapper.selectById(1).getToken())){
                return true;
            }
            httpReturn(response, MyResult.authfail("身份认证失败,请传入有效token"));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


    private void  httpReturn(HttpServletResponse response, MyResult myResult){
        String r= JSONObject.toJSONString(myResult);

        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(r);

        } catch (IOException e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }


    }
}
