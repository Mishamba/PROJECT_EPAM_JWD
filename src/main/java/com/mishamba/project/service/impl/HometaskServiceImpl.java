package com.mishamba.project.service.impl;

import com.mishamba.project.dao.DAOFactory;
import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.Hometask;
import com.mishamba.project.model.HometaskResponse;
import com.mishamba.project.service.HometaskService;
import com.mishamba.project.service.exception.CustomServiceException;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.parser.impl.DateParser;
import com.mishamba.project.util.validator.DateValidator;
import com.mishamba.project.util.validator.MarkValidator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

public class HometaskServiceImpl implements HometaskService {
    private static final Logger logger = Logger.getLogger(HometaskServiceImpl.class);

    @Override
    public ArrayList<Hometask> getCourseHometaskForUser(int courseId, int userId, String role) throws CustomServiceException {
        ArrayList<Hometask> hometasks;
        try {
            logger.info("getting hometask on course");
            hometasks = DAOFactory.getInstance().getHometaskDAO().
                    getHometasksOnCourseForUser(courseId, userId);
        } catch (DAOException e) {
            throw new CustomServiceException("can't get course hometask", e);
        }

        if (hometasks == null) {
            hometasks = new ArrayList<>();
        }

        return hometasks;
    }

    @Override
    public boolean createHometask(Properties hometaskProperties) throws CustomServiceException {
        int courseId = Integer.parseInt(hometaskProperties.getProperty("courseId"));
        String title = hometaskProperties.getProperty("title");
        String description = hometaskProperties.getProperty("description");
        String deadlineNotParsed = hometaskProperties.getProperty("deadline");

        DateParser dateParser = new DateParser();
        DateValidator dateValidator = new DateValidator();

        try {
            if (DAOFactory.getInstance().getCourseDAO().getCourseById(courseId).getFinished()) {
                throw new CustomServiceException("can't create hometask. course is finished");
            }
        } catch (DAOException e) {
            logger.error("can't check is course finished");
            return false;
        }

        Date deadline;
        try {
            deadline = dateParser.parse(deadlineNotParsed);
            if (dateValidator.checkForFuture(deadline)) {
                throw new CustomServiceException("deadline is not in future");
            }
        } catch (UtilException e) {
            throw new CustomServiceException("can't parse date", e);
        }
        Date beginDate = new Date();

        Hometask hometask = new Hometask(courseId, null, title, description,
                beginDate, deadline, null);
        try {
            return DAOFactory.getInstance().getHometaskDAO().createHometask(hometask);
        } catch (DAOException e) {
            logger.error("can't create hometask");
            throw new CustomServiceException("can't create hometask", e);
        }
    }

    @Override
    public Optional<Hometask> getHometaskById(int hometaskId) throws CustomServiceException {
        try {
            return Optional.ofNullable(DAOFactory.getInstance().getHometaskDAO().getHometaskById(hometaskId));
        } catch (DAOException e) {
            logger.error("can't get hometask");
            throw new CustomServiceException("can't get hometask", e);
        }
    }

    @Override
    public boolean writeHometaskAnswer(String answer, int hometaskId, int studentId) throws CustomServiceException {
        HometaskResponse response = new HometaskResponse(hometaskId, studentId, answer, null);

        try {
            return DAOFactory.getInstance().getHometaskDAO().writeHometaskResponse(response);
        } catch (DAOException e) {
            logger.error("can't write hometask response");
            throw new CustomServiceException("can't write hometask response", e);
        }
    }

    @Override
    public Optional<Hometask> getStudentHometaskWithResponce(int hometaskId, int studentId) throws CustomServiceException {
        try {
            Hometask hometask = DAOFactory.getInstance().getHometaskDAO().getHometaskById(hometaskId);
            if (hometask != null) {
                HometaskResponse response = DAOFactory.getInstance().getHometaskDAO().getHometaskResponse(hometaskId, studentId);
                hometask.setResponse(response);
            }

            return Optional.ofNullable(hometask);
        } catch (DAOException e) {
            logger.error("can't get hometask");
            throw new CustomServiceException("can't get hometask");
        }
    }

    @Override
    public boolean setHometaskMark(int hometaskId, int studentId, int mark) throws CustomServiceException {
        MarkValidator validator = new MarkValidator();
        if (!validator.isCorrect(mark)) {
            throw new CustomServiceException("mark incorrect");
        }

        try {
            return DAOFactory.getInstance().getHometaskDAO().setHometaskMark(hometaskId, studentId, mark);
        } catch (DAOException e) {
            logger.error("can't set mark");
            throw new CustomServiceException("can't set mark", e);
        }
    }
}
