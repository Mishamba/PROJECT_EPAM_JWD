package com.mishamba.project.service.former.builder.impl.admin;

import com.mishamba.project.service.exception.CustomServiceException;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.service.former.builder.Former;

import java.util.Properties;

public class AdminProfile implements Former {
    @Override
    public String form(Properties properties) throws CustomServiceException {
        return "<p>Admin has no courses. Only knowledge</p>";
    }
}
