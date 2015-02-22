package com.cnvp.paladin.interceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class Shiro implements Interceptor {

	@Override
	public void intercept(ActionInvocation ai) {
		// 获取Shiro Subject
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {// 没有认证
//			DwzRender render = new DwzRender();
//			render.setStatusCode("301");
//			render.setMessage("会话超时，请重新登录。");
//			ai.getController().render(render);
			System.err.println("会话超时or未登录。");
			ai.getController().redirect("/Passport/login?from="+ai.getController().getRequest().getRequestURL());
		} else {			
			if (currentUser.isPermittedAll("permissions")) {
				System.err.println("授权通过");
			}else{
				System.err.println("授权");
			}
			ai.invoke();// 授权
			// 已经认证
//			if (permissions == null || currentUser.isPermittedAll(permissions)) {
//				ai.invoke();// 授权
//			} else {
//				// ai.getController().render("/401.html");
//				DwzRender render = new DwzRender();
//				render.setStatusCode("301");
//				render.setMessage("该功能没有授权，请重新登录。");
//				ai.getController().render(render);// 没有授权，通过DWZ返回错误信息
//			}
		}
		
	}

}
