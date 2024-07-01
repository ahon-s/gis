//package siwei.ahon.mapserver.interceptor;
//
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class CorsInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
////        System.out.println(request.getHeader("origin"+"ssssssssssssssssssssssssssssssssssssssssssssssssssssss"));
////        response.setHeader("Access-Control-Allow-Credentials", "true");
////        response.setHeader("Access-Control-Allow-Origin", "*");
////        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
////        response.setHeader("Access-Control-Max-Age", "86400");
////        response.setHeader("Access-Control-Allow-Headers", "*");
////        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
////        response.setHeader("Content-Type", "application/json; charset=utf-8");
////
////
////
////        if(request.getMethod().equals(RequestMethod.OPTIONS.name()))
////        {
////            return false;
////        }
//
//        return true;
//    }
//}
