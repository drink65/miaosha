package com.zaogao.miaosha.domain;

import org.thymeleaf.context.IWebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Set;

public class IWebContextImpl implements IWebContext {
    @Override
    public HttpServletRequest getRequest() {
        return null;
    }

    @Override
    public HttpServletResponse getResponse() {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public boolean containsVariable(String s) {
        return false;
    }

    @Override
    public Set<String> getVariableNames() {
        return null;
    }

    @Override
    public Object getVariable(String s) {
        return null;
    }
}
