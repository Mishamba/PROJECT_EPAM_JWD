package com.mishamba.project.util.former.builder.impl.teacher;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.builder.PartsBuilder;

import java.util.Properties;

public class TeacherUserInfo implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        return PartsBuilder.getInstance().formUserInfo(properties);
    }
}
