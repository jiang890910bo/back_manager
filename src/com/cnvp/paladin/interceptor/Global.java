package com.cnvp.paladin.interceptor;

import com.cnvp.paladin.kit.PropertyKit;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

public class Global implements Interceptor {
	public void intercept(ActionInvocation ai) {
		
		Controller ctrl=ai.getController();		
		ctrl.setAttr("root",ctrl.getRequest().getContextPath()+"/");		
		ctrl.setAttr("action",ai.getActionKey());
		ctrl.setAttr("app_name",PropertyKit.get("app_name"));
		ctrl.setAttr("ControllerKey",ai.getControllerKey());
//		Method m = ai.getMethod();
//		System.out.println(m.isVarArgs());
//		JFinal.me().getConstants().setDevMode(PropertyKit.getToBoolean("app_devModel"));
		//每个连接器必须执行这个方法才可以继续执行
		ai.invoke(); 
	}
}
