package com.mishamba.project.util.former;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.builder.impl.admin.*;
import com.mishamba.project.util.former.builder.impl.anonym.AnonymCourseProfileInfo;
import com.mishamba.project.util.former.builder.impl.anonym.AnonymMenuButtons;
import com.mishamba.project.util.former.builder.impl.anonym.AnonymSignUpField;
import com.mishamba.project.util.former.builder.impl.anonym.AnonymUserInfo;
import com.mishamba.project.util.former.builder.impl.student.StudentCourseProfileInfo;
import com.mishamba.project.util.former.builder.impl.student.StudentMenuButtons;
import com.mishamba.project.util.former.builder.impl.student.StudentProfile;
import com.mishamba.project.util.former.builder.impl.student.StudentUserInfo;
import com.mishamba.project.util.former.builder.impl.teacher.TeacherCourseProfileInfo;
import com.mishamba.project.util.former.builder.impl.teacher.TeacherMenuButtons;
import com.mishamba.project.util.former.builder.impl.teacher.TeacherProfile;
import com.mishamba.project.util.former.builder.impl.teacher.TeacherUserInfo;

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
        repository.put("anonym course profile button", new AnonymCourseProfileInfo());
        repository.put("admin course profile button", new AdminCourseProfileButtons());
        repository.put("teacher course profile button", new TeacherCourseProfileInfo());
        repository.put("student course profile button", new StudentCourseProfileInfo());
        repository.put("student profile", new StudentProfile());
        repository.put("teacher profile", new TeacherProfile());
        repository.put("admin profile", new AdminProfile());
    }

    private static class FormerProviderHolder {
        private static final FormerProvider HOLDER = new FormerProvider();
    }

    public static FormerProvider getInstance() {
        return FormerProviderHolder.HOLDER;
    }

    public Former getFormer(Properties properties) throws UtilException {
        if (properties == null) {
            throw new UtilException("property is null");
        }
        StringBuilder parameters = new StringBuilder();
        String role = properties.getProperty("role");
        String target = properties.getProperty("target");
        parameters.append(role).append(" ").append(target);

        return repository.get(parameters.toString());
    }
}
