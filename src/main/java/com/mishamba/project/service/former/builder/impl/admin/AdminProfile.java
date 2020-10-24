package com.mishamba.project.service.former.builder.impl.admin;

import com.mishamba.project.exception.CustomServiceException;
import com.mishamba.project.service.former.builder.Former;

import java.util.Properties;

public class AdminProfile implements Former {
    @Override
    public String form(Properties properties) throws CustomServiceException {
        return "<p>Admin has no courses. Only knowledge</p>";
    }
}
