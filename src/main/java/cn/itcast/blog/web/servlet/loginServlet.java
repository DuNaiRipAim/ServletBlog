package cn.itcast.blog.web.servlet;

import cn.itcast.blog.domain.ResultInfo;
import cn.itcast.blog.domain.User;
import cn.itcast.blog.service.UserService;
import cn.itcast.blog.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String[]> map = request.getParameterMap();
        User user=new User();
        try{
            BeanUtils.populate(user,map);
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }
        UserService service=new UserServiceImpl();
        User u = service.login(user);

        ResultInfo info = new ResultInfo();

        if(u==null){
            info.setFlag(false);
            info.setErrorMsg("用户名或者密码错误");
        }

        if(u!=null&&!"Y".equals(u.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("用户尚未激活!");
        }

        if(u!=null&&"Y".equals(u.getStatus())){
            request.getSession().setAttribute("user",u);
            info.setFlag(true);
        }

        ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
