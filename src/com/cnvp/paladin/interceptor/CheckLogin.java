package com.cnvp.paladin.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

public class CheckLogin implements Interceptor {
	public void intercept(ActionInvocation ai) {
		Controller ctrl = ai.getController();
		ctrl.setAttr("root", ctrl.getRequest().getContextPath());
		// 每个连接器必须执行这个方法才可以继续执行
		ai.invoke();
	}
}
