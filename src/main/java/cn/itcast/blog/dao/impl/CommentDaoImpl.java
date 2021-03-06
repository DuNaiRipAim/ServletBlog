package cn.itcast.blog.dao.impl;

import cn.itcast.blog.dao.CommentDao;
import cn.itcast.blog.dao.impl.rowmappers.CommentRowMapper;
import cn.itcast.blog.domain.Comment;
import cn.itcast.blog.domain.User;
import cn.itcast.blog.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * <p>评论Dao实现类</p>
 * @author 2020401308 李翊君
 * @version 1.0
 * @see cn.itcast.blog.dao.CommentDao
 * */
public class CommentDaoImpl implements CommentDao {
    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * <p>保存评论</p>
     * @param comment 评论信息
     * */
    @Override
    public void saveComment(Comment comment) {
        String sql="insert into comment(post_id,uname_email,content,parent,parent_name,count_good,ctime,uname_head) values(?,?,?,?,?,?,?,?)";
        template.update(sql,comment.getPost_id(),comment.getUname_email(),comment.getContent(),
                comment.getParent(),comment.getParent_name(),comment.getCount_good(),comment.getCtime(),comment.getUname_head());
    }

    /**
     * <p>读取某个用户特定评论</p>
     * @param email 用户邮箱
     * @return 评论列表
     * */
    @Override
    public List<Comment> loadComment(String email) {
        List<Comment> comments=null;
        String sql="select * from comment where uname_email = ?";
        comments=template.query(sql,new CommentRowMapper(),email);
        return comments;
    }


    /**
     * <p>获取所有用户的评论</p>
     * @return 评论列表
     * */
    @Override
    public List<Comment> loadAllComment() {
        List<Comment> comments=null;
        String sql="select * from comment";
        comments=template.query(sql,new CommentRowMapper());
        return comments;
    }

    /**
     * <p>获取某个文章中的评论信息</p>
     * @param id 文章ID
     * @return 评论列表
     * */
    @Override
    public List<Comment> loadArticleComment(int id) {
        List<Comment> comments=null;
        String sql="select * from comment where post_id = ?";
        comments=template.query(sql,new CommentRowMapper(),id);
        String sql2="select * from user where Email = ?";
        for(int i=0;i<comments.size();i++){
            String head=template.queryForObject(sql2,new BeanPropertyRowMapper<User>(User.class),comments.get(i).getUname_email()).getHead();
            comments.get(i).setUname_head(head);
        }
        return comments;
    }

    /**
     * <p>删除某个评论</p>
     * @param id 评论ID
     * */
    public void deleteComment(int id) {
        String sql="delete from comment where id = ?";
        template.update(sql,id);
    }

}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 