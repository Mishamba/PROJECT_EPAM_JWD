package com.mishamba.project.service;

import com.mishamba.project.model.Hometask;
import com.mishamba.project.exception.CustomServiceException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public interface HometaskService {
    ArrayList<Hometask> getCourseHometaskForUser(int courseId, int studentId,
                                                 String role)
            throws CustomServiceException;
    boolean createHometask(int courseId, String title, String description,
                           Date deadline) throws CustomServiceException;
    Optional<Hometask> getHometaskById(int hometaskId)
            throws CustomServiceException;
    boolean writeHometaskAnswer(String answer, int hometaskId, int studentId)
            throws CustomServiceException;
    Optional<Hometask> getStudentHometaskWithResponce(int hometaskId,
                                                      int studentId)
            throws CustomServiceException;
    boolean setHometaskMark(int hometaskId, int studentId, int mark)
            throws  CustomServiceException;
}
