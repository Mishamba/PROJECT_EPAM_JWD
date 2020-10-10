package com.mishamba.project.util.former.builder.impl.admin;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;

import java.util.Properties;

public class AdminProfile implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        return "<p>Admin has no courses. Only knowledge</p>";
    }
}
