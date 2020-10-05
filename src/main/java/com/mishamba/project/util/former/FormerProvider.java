package com.mishamba.project.util.former;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.builder.impl.admin.AdminMainPageButtons;
import com.mishamba.project.util.former.builder.impl.admin.AdminSingUpField;
import com.mishamba.project.util.former.builder.impl.admin.AdminUserInfo;
import com.mishamba.project.util.former.builder.impl.anonym.AnonymMainPagesButtons;
import com.mishamba.project.util.former.builder.impl.anonym.AnonymSingUpField;
import com.mishamba.project.util.former.builder.impl.anonym.AnonymUserInfo;
import com.mishamba.project.util.former.builder.impl.student.StudentMainPagesButtons;
import com.mishamba.project.util.former.builder.impl.student.StudentUserInfo;
import com.mishamba.project.util.former.builder.impl.teacher.TeacherMainPagesButtons;
import com.mishamba.project.util.former.builder.impl.teacher.TeacherUserInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FormerProvider {
    private final Map<String, Former> repository = new HashMap<>();
    private FormerProvider() {
        repository.put("student main menu",    new StudentMainPagesButtons());
        repository.put("student main user info", new StudentUserInfo());
        repository.put("teacher main menu",    new TeacherMainPagesButtons());
        repository.put("teacher main user info", new TeacherUserInfo());
        repository.put("admin main menu",      new AdminMainPageButtons());
        repository.put("admin main user info",   new AdminUserInfo());
        repository.put("anonym main user info",  new AnonymUserInfo());
        repository.put("anonym main menu",       new AnonymMainPagesButtons());
        repository.put("admin sing up field",    new AdminSingUpField());
        repository.put("anonym sing up field", new AnonymSingUpField());
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
        String page = properties.getProperty("page");
        String target = properties.getProperty("target");
        parameters.append(role).append(" ").append(page).append(" ").
                append(target);

        return repository.get(parameters.toString());
    }
}
