package com.mishamba.project.util.former.builder.impl.admin;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.builder.part.UserInfo;

import java.util.Properties;

public class AdminUserInfo implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        return UserInfo.get(properties);
    }
}
