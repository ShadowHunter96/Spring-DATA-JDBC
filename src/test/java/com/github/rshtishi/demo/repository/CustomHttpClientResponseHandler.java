package com.github.rshtishi.demo.repository;

import org.apache.http.protocol.HttpContext;

/**
 * Created by User:
 * Date: 08.06.2023
 * Time: 19:45
 */
public class CustomHttpClientResponseHandler implements HttpContext {
    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public Object removeAttribute(String s) {
        return null;
    }
}
