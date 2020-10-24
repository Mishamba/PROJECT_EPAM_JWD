package com.mishamba.project.dao;

import com.mishamba.project.exception.DAOException;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.ProgramStep;

import java.util.ArrayList;

public interface ProgramStepDAO {
    ArrayList<ProgramStep> getCourseProgram(Course course)
            throws DAOException;
}
