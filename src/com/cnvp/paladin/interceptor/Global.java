package com.cnvp.paladin.interceptor;

import com.cnvp.paladin.kit.PropertyKit;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;

public class Global implements Interceptor {
	public void intercept(ActionInvocation ai) {
		
		Controller ctrl=ai.getController();		
		String cp = JFinal.me().getContextPath();
		ctrl.setAttr("root",("".equals(cp) || "/".equals(cp)) ? "" : cp);		
		ctrl.setAttr("action",ai.getActionKey());
		ctrl.setAttr("app_name",PropertyKit.get("app_name"));
		ctrl.setAttr("ControllerKey",ai.getControllerKey());
		ctrl.setAttr("ActionKey",ai.getActionKey());
//		Method m = ai.getMethod();
//		System.out.println(m.isVarArgs());
//		JFinal.me().getConstants().setDevMode(PropertyKit.getToBoolean("app_devModel"));
		//每个连接器必须执行这个方法才可以继续执行
		ai.invoke(); 
	}
}
