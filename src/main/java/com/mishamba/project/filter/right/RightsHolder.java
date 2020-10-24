package com.mishamba.project.filter.right;

import java.util.*;

public class RightsHolder {
    private static Map<String, List<String>> requestsRights = null;
    private static final String ADMIN = "admin";
    private static final String STUDENT = "student";
    private static final String TEACHER = "teacher";
    private static final String ANONYM = "anonym";

    private RightsHolder() {
        requestsRights = new HashMap<>(21, 0.75F);
        requestsRights.put("main_page", Arrays.asList(ADMIN, STUDENT, TEACHER,
                ANONYM));
        requestsRights.put("course_catalog", Arrays.asList(ADMIN, STUDENT,
                TEACHER, ANONYM));
        requestsRights.put("sign_up", Arrays.asList(ANONYM, ADMIN));
        requestsRights.put("sign_in", Collections.singletonList(ANONYM));
        requestsRights.put("sign_up_check", Arrays.asList(ANONYM, ADMIN));
        requestsRights.put("sign_in_check", Collections.singletonList(ANONYM));
        requestsRights.put("create_hometask", Collections.singletonList(TEACHER));
        requestsRights.put("sign_out", Arrays.asList(ADMIN, STUDENT, TEACHER));
        requestsRights.put("course_profile", Arrays.asList(ADMIN, STUDENT, TEACHER, ANONYM));
        requestsRights.put("student_sign_up_for_course", Collections.singletonList(STUDENT));
        requestsRights.put("user_profile", Arrays.asList(ADMIN, STUDENT, TEACHER));
        requestsRights.put("user_courses", Arrays.asList(TEACHER, STUDENT));
        requestsRights.put("students_on_course_list", Arrays.asList(ADMIN, TEACHER));
        requestsRights.put("course_hometask", Arrays.asList(TEACHER, STUDENT, ADMIN));
        requestsRights.put("check_create_hometask", Collections.singletonList(TEACHER));
        requestsRights.put("answer_hometask", Collections.singletonList(STUDENT));
        requestsRights.put("enter_hometask_answer", Collections.singletonList(STUDENT));
        requestsRights.put("student_progress", Arrays.asList(ADMIN, TEACHER));
        requestsRights.put("check_hometask", Collections.singletonList(TEACHER));
        requestsRights.put("set_hometask_mark", Collections.singletonList(TEACHER));
        requestsRights.put("kick_student_page", Collections.singletonList(TEACHER));
    }

    private static class RightsHolderHolder {
        private static final RightsHolder HOLDER = new RightsHolder();
    }

    public static RightsHolder getInstance() {
        return RightsHolderHolder.HOLDER;
    }

    public boolean rightsCorrect(String commandName, String givenRole) {
        if (commandName == null) {
            return false;
        }

        if (givenRole == null) {
            givenRole = ANONYM;
        }

        List<String> roles = requestsRights.get(commandName);
        for (String role : roles) {
            if (role.equals(givenRole)) {
                return true;
            }
        }

        return false;
    }
}
