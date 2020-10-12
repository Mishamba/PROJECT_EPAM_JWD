package com.mishamba.project.dao;

import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.Hometask;

import java.util.ArrayList;

public interface HometaskDAO {
    void createHometask(Hometask hometask) throws DAOException;
    ArrayList<Hometask> getHometasksOnCourse(int courseId) throws DAOException;
}
