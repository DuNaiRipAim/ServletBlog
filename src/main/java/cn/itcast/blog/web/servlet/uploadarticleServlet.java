package cn.itcast.blog.web.servlet;

import cn.itcast.blog.domain.Article;
import cn.itcast.blog.domain.ResultInfo;
import cn.itcast.blog.domain.User;
import cn.itcast.blog.service.ArticleService;
import cn.itcast.blog.service.KindService;
import cn.itcast.blog.service.UserService;
import cn.itcast.blog.service.impl.ArticleServiceImpl;
import cn.itcast.blog.service.impl.KindServiceImpl;
import cn.itcast.blog.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Map;

@WebServlet(name = "uploadarticleServlet", value = "/uploadarticleServlet")
public class uploadarticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String[]> map = request.getParameterMap();
        int role=Integer.valueOf(request.getParameter("role"));
        int kindtype=Integer.valueOf(request.getParameter("kindid"));
        String msg=null;
        Article article=new Article();
        User user=(User)request.getSession().getAttribute("user");
        try{
            BeanUtils.populate(article,map);
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        article.setKind_id(kindtype);
        article.setEmail(user.getEmail());
        article.setStatus(role);
        article.setCreat_time(new Timestamp(System.currentTimeMillis()));
        article.setLast_change_time(new Timestamp(System.currentTimeMillis()));
        ArticleService service=new ArticleServiceImpl();
        service.saveArticle(article);

    }
}
