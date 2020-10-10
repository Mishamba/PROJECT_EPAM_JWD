package com.mishamba.project.util.former.builder;

import com.mishamba.project.util.exception.UtilException;

import javax.servlet.http.HttpSession;
import java.util.Properties;

public interface Former {
    String form(Properties properties) throws UtilException;
}
