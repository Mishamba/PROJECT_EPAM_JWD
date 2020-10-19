package com.mishamba.project.dao;

import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.Hometask;
import com.mishamba.project.model.HometaskResponse;

import java.util.ArrayList;
import java.util.zip.DataFormatException;

public interface HometaskDAO {
    void createHometask(Hometask hometask) throws DAOException;
    ArrayList<Hometask> getHometasksOnCourseForUser(int courseId, int studentId) throws DAOException;
    HometaskResponse getHometaskResponse(int hometaskId, int studentId) throws DAOException;
    Hometask getHometaskById(int hometaskId) throws DAOException;
    void writeHometaskResponse(HometaskResponse response) throws DAOException;
    void setHometaskMark(int hometaskId, int studentId, int mark) throws DAOException;
}
