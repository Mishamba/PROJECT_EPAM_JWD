package com.mishamba.project.dao;

import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.Hometask;
import com.mishamba.project.model.HometaskResponse;

import java.util.ArrayList;

public interface HometaskDAO {
    void createHometask(Hometask hometask) throws DAOException;
    ArrayList<Hometask> getHometasksOnCourseForUser(int courseId, int studentId) throws DAOException;
    HometaskResponse getHometaskResponse(int hometaskId, int studentId) throws DAOException;
    Hometask getHometaskById(int hometaskId) throws DAOException;
    boolean writeHometaskResponse(HometaskResponse response) throws DAOException;
    boolean setHometaskMark(int hometaskId, int studentId, int mark) throws DAOException;
}
