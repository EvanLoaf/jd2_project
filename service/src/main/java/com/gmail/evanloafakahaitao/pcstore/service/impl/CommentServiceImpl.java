package com.gmail.evanloafakahaitao.pcstore.service.impl;

import com.gmail.evanloafakahaitao.pcstore.dao.CommentDao;
import com.gmail.evanloafakahaitao.pcstore.dao.NewsDao;
import com.gmail.evanloafakahaitao.pcstore.dao.UserDao;
import com.gmail.evanloafakahaitao.pcstore.dao.impl.CommentDaoImpl;
import com.gmail.evanloafakahaitao.pcstore.dao.impl.NewsDaoImpl;
import com.gmail.evanloafakahaitao.pcstore.dao.impl.UserDaoImpl;
import com.gmail.evanloafakahaitao.pcstore.dao.model.Comment;
import com.gmail.evanloafakahaitao.pcstore.dao.model.News;
import com.gmail.evanloafakahaitao.pcstore.dao.model.User;
import com.gmail.evanloafakahaitao.pcstore.service.CommentService;
import com.gmail.evanloafakahaitao.pcstore.service.converter.Converter;
import com.gmail.evanloafakahaitao.pcstore.service.converter.DTOConverter;
import com.gmail.evanloafakahaitao.pcstore.service.converter.impl.entity.CommentConverter;
import com.gmail.evanloafakahaitao.pcstore.service.converter.impl.dto.CommentDTOConverter;
import com.gmail.evanloafakahaitao.pcstore.service.dto.CommentDTO;
import com.gmail.evanloafakahaitao.pcstore.service.dto.NewsDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Iterator;

public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LogManager.getLogger(CommentServiceImpl.class);

    private CommentDao commentDao = new CommentDaoImpl(Comment.class);
    private NewsDao newsDao = new NewsDaoImpl(News.class);
    private UserDao userDao = new UserDaoImpl(User.class);
    private Converter commentConverter = new CommentConverter();
    private DTOConverter commentDTOConverter = new CommentDTOConverter();

    @SuppressWarnings("unchecked")
    @Override
    public CommentDTO save(NewsDTO newsDTO) {
        Session session = commentDao.getCurrentSession();
        try {
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                session.beginTransaction();
            }
            News news = newsDao.findOne(newsDTO.getId());
            Comment comment = (Comment) commentConverter.toEntity(newsDTO.getCommentSet().iterator().next());
            User user = userDao.findByEmail(comment.getUser().getEmail());
            comment.setUser(user);
            news.getCommentSet().add(comment);
            newsDao.update(news);
            CommentDTO commentDTOsaved = (CommentDTO) commentDTOConverter.toDto(comment);
            transaction.commit();
            return commentDTOsaved;
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            logger.error("Failed to save Comment", e);
        }
        return null;
    }

    @Override
    public boolean deleteById(NewsDTO newsDTO) {
        Session session = commentDao.getCurrentSession();
        try {
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                session.beginTransaction();
            }
            Long deleteCommentId = newsDTO.getCommentSet().iterator().next().getId();
            News news = newsDao.findOne(newsDTO.getId());
            Iterator<Comment> iterator = news.getCommentSet().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getId().equals(deleteCommentId)) {
                    iterator.remove();
                    break;
                }
            }
            newsDao.update(news);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            logger.error("Failed to delete comment by id", e);
        }
        return false;
    }
}