package com.mishamba.project.dao.impl;

import com.mishamba.project.dao.ProgramStepDAO;
import com.mishamba.project.dao.ProxyConnection;
import com.mishamba.project.exception.DAOException;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.ProgramStep;
import com.mishamba.project.exception.UtilException;
import com.mishamba.project.util.parser.impl.DateParser;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ProgramStepDAOImpl implements ProgramStepDAO {
    private final Logger logger = Logger.getLogger(ProgramStepDAOImpl.class);
    private final String COURSE_PROGRAM_QUEUE =
            "SELECT step, step_name, step_description, start_date, end_date " +
                    "FROM course_programs " +
                    "WHERE course_id = ?";

    @Override
    public ArrayList<ProgramStep> getCourseProgram(Course course) throws DAOException {
        if (course == null) {
            throw new DAOException("no course given");
        }
        ArrayList<ProgramStep> coursePrograms = new ArrayList<>();
        DateParser dateParser = new DateParser();

        try {
            ProxyConnection connection = ConnectionPoolImpl.getInstance().
                    getConnection();
            PreparedStatement statement = connection.
                    prepareStatement(COURSE_PROGRAM_QUEUE);
            statement.setInt(1, course.getId());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int step = resultSet.getInt("step");
                String stepName = resultSet.getString("step_name");
                String description = resultSet.getString("step_description");
                Date startDate = dateParser.parse(resultSet.getString("start_date"));
                Date endDate =dateParser.parse(resultSet.getString("end_date"));
                coursePrograms.add(new ProgramStep(course.getId(),
                        step, stepName, description, startDate, endDate));
            }
        } catch (DAOException | SQLException | UtilException e) {
            throw new DAOException("can't get course program", e);
        }

        return coursePrograms;
    }
}
