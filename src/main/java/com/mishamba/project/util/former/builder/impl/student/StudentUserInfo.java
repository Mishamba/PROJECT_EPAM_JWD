package com.mishamba.project.util.former.builder.impl.student;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.builder.part.UserInfo;

import java.util.Properties;

public class StudentUserInfo implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        return UserInfo.get(properties);
    }
}
