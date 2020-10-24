package com.mishamba.project.service.former;

import com.mishamba.project.exception.CustomServiceException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.builder.impl.admin.*;
import com.mishamba.project.service.former.builder.impl.anonym.AnonymCourseProfileInfo;
import com.mishamba.project.service.former.builder.impl.anonym.AnonymMenuButtons;
import com.mishamba.project.service.former.builder.impl.anonym.AnonymSignUpField;
import com.mishamba.project.service.former.builder.impl.anonym.AnonymUserInfo;
import com.mishamba.project.service.former.builder.impl.student.*;
import com.mishamba.project.service.former.builder.impl.teacher.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FormerProvider {
    private final Map<String, Former> repository = new HashMap<>();
    private FormerProvider() {
        repository.put("student menu",    new StudentMenuButtons());
        repository.put("student user info", new StudentUserInfo());
        repository.put("teacher menu",    new TeacherMenuButtons());
        repository.put("teacher user info", new TeacherUserInfo());
        repository.put("admin menu",      new AdminMenuButtons());
        repository.put("admin user info",   new AdminUserInfo());
        repository.put("anonym user info",  new AnonymUserInfo());
        repository.put("anonym menu",       new AnonymMenuButtons());
        repository.put("admin sign up",    new AdminSignUpField());
        repository.put("anonym sign up", new AnonymSignUpField());
        repository.put("anonym not on course profile button", new AnonymCourseProfileInfo());
        repository.put("admin not on course profile button", new AdminCourseProfileButtons());
        repository.put("teacher not on course profile button", new TeacherNotOnCourseProfileInfo());
        repository.put("student not on course profile button", new StudentNotOnCourseProfileInfo());
        repository.put("teacher on course profile button", new TeacherOnCourseProfileInfo());
        repository.put("student on course profile button", new StudentOnCourseProfileInfo());
        repository.put("student profile", new StudentProfileButtons());
        repository.put("teacher profile", new TeacherProfileButtons());
        repository.put("admin profile", new AdminProfile());
    }

    private static class FormerProviderHolder {
        private static final FormerProvider HOLDER = new FormerProvider();
    }

    public static FormerProvider getInstance() {
        return FormerProviderHolder.HOLDER;
    }

    public Former getFormer(Properties properties) throws CustomServiceException {
        if (properties == null) {
            throw new CustomServiceException("property is null");
        }
        StringBuilder parameters = new StringBuilder();
        String role = properties.getProperty("role");
        String target = properties.getProperty("target");
        parameters.append(role).append(" ").append(target);

        return repository.get(parameters.toString());
    }
}
