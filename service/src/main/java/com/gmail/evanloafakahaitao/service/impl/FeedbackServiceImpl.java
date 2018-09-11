package com.gmail.evanloafakahaitao.service.impl;

import com.gmail.evanloafakahaitao.dao.FeedbackDao;
import com.gmail.evanloafakahaitao.dao.UserDao;
import com.gmail.evanloafakahaitao.dao.impl.FeedbackDaoImpl;
import com.gmail.evanloafakahaitao.dao.impl.UserDaoImpl;
import com.gmail.evanloafakahaitao.dao.model.Feedback;
import com.gmail.evanloafakahaitao.dao.model.User;
import com.gmail.evanloafakahaitao.service.FeedbackService;
import com.gmail.evanloafakahaitao.service.converter.Converter;
import com.gmail.evanloafakahaitao.service.converter.DTOConverter;
import com.gmail.evanloafakahaitao.service.converter.impl.FeedbackConverterImpl;
import com.gmail.evanloafakahaitao.service.converter.impl.FeedbackDTOConverterImpl;
import com.gmail.evanloafakahaitao.service.dto.FeedbackDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LogManager.getLogger(FeedbackServiceImpl.class);

    private FeedbackDao feedbackDao = new FeedbackDaoImpl(Feedback.class);
    private UserDao userDao = new UserDaoImpl(User.class);
    private Converter feedbackConverter = new FeedbackConverterImpl();
    private DTOConverter feedbackDTOConverter = new FeedbackDTOConverterImpl();

    @SuppressWarnings("unchecked")
    @Override
    public FeedbackDTO save(FeedbackDTO feedbackDTO) {
        Session session = feedbackDao.getCurrentSession();
        try {
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                session.beginTransaction();
            }
            Feedback feedback = (Feedback) feedbackConverter.toEntity(feedbackDTO);
            User user = userDao.findByEmail(feedbackDTO.getUser().getEmail());
            feedback.setUser(user);
            feedbackDao.create(feedback);
            transaction.commit();
            return (FeedbackDTO) feedbackDTOConverter.toDto(feedback);
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            logger.error("Failed to save Feedback", e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FeedbackDTO> findAll() {
        Session session = feedbackDao.getCurrentSession();
        try {
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                session.beginTransaction();
            }
            List<Feedback> feedbackList = feedbackDao.findAll();
            List<FeedbackDTO> feedbackDTOList = feedbackDTOConverter.toDTOList(feedbackList);
            transaction.commit();
            return feedbackDTOList;
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            logger.error("Failed to retrieve Feedback", e);
        }
        return null;
    }

    @Override
    public boolean deleteById(FeedbackDTO feedbackDTO) {
        Session session = feedbackDao.getCurrentSession();
        try {
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                session.beginTransaction();
            }
            feedbackDao.deleteById(feedbackDTO.getId());
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            logger.error("Failed to delete feedback by id", e);
        }
        return false;
    }
}
