package com.cnvp.paladin.handler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

/**
 * cookie参数填入到request
 */
public class CookieHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request,HttpServletResponse response, boolean[] isHandled) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				request.setAttribute(name, cookie.getValue());
			}
		nextHandler.handle(target, request, response, isHandled);
	}
}
