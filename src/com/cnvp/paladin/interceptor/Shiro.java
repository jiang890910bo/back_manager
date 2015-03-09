package com.cnvp.paladin.interceptor;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.cnvp.paladin.model.SysRes;
import com.cnvp.paladin.model.SysUser;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.JFinal;

public class Shiro implements Interceptor {

	@Override
	public void intercept(ActionInvocation ai) {		
		/* TODO 继续完善权限控制体系
		 * 1、按照“权限代码”构建权限目录树
		 * 2、针对固定的权限，添加actionKey
		 * 3、shiro拦截器中，根据actionKey在数据库中查找“权限代码”，并进行验证
		 * */
		// 获取Shiro Subject
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			// 判断是否登陆
			System.err.println("会话超时or未登录");
			String cp = JFinal.me().getContextPath();
			cp =  ("".equals(cp) || "/".equals(cp)) ? "" : cp;
			String url = cp+"/Passport/login?from="+ai.getController().getRequest().getRequestURL();
			ai.getController().redirect(url);
		} else {
			SysUser user = (SysUser) currentUser.getPrincipal();
			// 根据ak读取权限代码
			Map<String,String> ak_coderoutes = SysRes.dao.getAk_CodeRoutes();
			String code_route = ak_coderoutes.get(ai.getActionKey());			
			//进行权限判断
			if(user.getStr("account").equals("superadmin")){
				ai.invoke();
			}else if(code_route==null){
				ai.getController().renderText(ai.getActionKey()+"由于该ActionKey未被配置到系统资源中，故默认没有权限");
			}else if(currentUser.isPermitted(code_route)){
				ai.invoke();
			}else{
				ai.getController().renderText("未授权，请联系管理员");
			}
			return;
		}
		
	}

}
