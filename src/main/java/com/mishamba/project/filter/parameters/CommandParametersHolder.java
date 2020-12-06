package com.mishamba.project.filter.parameters;

import java.util.*;

public class CommandParametersHolder {
    private static Map<String, List<String>> commandParameters = null;
    private static String USER_ID = "user_id";
    private static String COURSE_ID = "course_id";
    private final String BIRTHDAY = "birthday";
    private final String DEADLINE = "deadline";
    private final String HOMETASK_ID = "hometask_id";
    private final String STUDENT_ID = "student_id";
    private final String MARK = "mark";
    private final String FINISHED = "finished";

    private CommandParametersHolder() {
        commandParameters = new HashMap<>(11, 0.75F);
        commandParameters.put("sign_up_check", Collections.singletonList(BIRTHDAY));
        commandParameters.put("course_profile", Collections.singletonList(COURSE_ID));
        commandParameters.put("student_sign_up_for_course", Collections.singletonList(COURSE_ID));
        commandParameters.put("students_on_course_list", Collections.singletonList(COURSE_ID));
        commandParameters.put("course_hometask", Collections.singletonList(COURSE_ID));
        commandParameters.put("check_create_hometask", Arrays.asList(COURSE_ID, DEADLINE));
        commandParameters.put("answer_hometask", Collections.singletonList(HOMETASK_ID));
        commandParameters.put("enter_hometask_answer", Arrays.asList(HOMETASK_ID, USER_ID));
        commandParameters.put("student_progress", Arrays.asList(COURSE_ID, STUDENT_ID));
        commandParameters.put("check_hometask", Arrays.asList(HOMETASK_ID, STUDENT_ID));
        commandParameters.put("set_hometask_mark", Arrays.asList(STUDENT_ID, HOMETASK_ID, MARK));
        commandParameters.put("kick_student_page", Collections.singletonList(STUDENT_ID));
        commandParameters.put("user_courses", Collections.singletonList(FINISHED));
        commandParameters.put("create_hometask", Collections.singletonList(COURSE_ID));
    }

    private static class CommandParametersHolderHolder {
        private static final CommandParametersHolder HOLDER =
                new CommandParametersHolder();
    }

    public static CommandParametersHolder getInstance() {
        return CommandParametersHolderHolder.HOLDER;
    }

    public List<String> getParametersList(String commandName) {
        List<String> parameters = commandParameters.get(commandName);
        return (parameters == null) ? new ArrayList<>() : parameters;
    }
}
