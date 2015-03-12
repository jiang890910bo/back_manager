package com.cnvp.paladin.plugin.shiro;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import com.cnvp.paladin.model.SysRes;
import com.cnvp.paladin.model.SysUser;
import com.jfinal.core.JFinal;

public class AuthorizationFilter4Shiro extends AuthorizationFilter {	

	@Override
	protected boolean isAccessAllowed(ServletRequest request,ServletResponse response, Object mappedValue) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		Subject currentUser = getSubject(request, response);
		SysUser user = (SysUser) currentUser.getPrincipal();
		// 根据ak读取权限代码
		Map<String,String> ak_coderoutes = SysRes.dao.getAk_CodeRoutes();
		String code_route = ak_coderoutes.get(getActionKey(req));			
		//进行权限判断
		if (user==null) 
			return false;
		else if(user.getStr("account").equals("superadmin")){
			return true;
		}else if(code_route==null){
			return false;
		}else if(currentUser.isPermitted(code_route)){
			return true;
		}else{
			return false;
		}
	}

	private String getActionKey(HttpServletRequest request) {
		//获取所有AK
		List<String> aks = JFinal.me().getAllActionKeys();
		/*
		 * 由于不能直接使用Action类，无法使用JFinal.me().getAction(url, urlPara)来直接获取
		 * 以下代码取自JFinal的JFinal.core的相关类，保证与JFinal获取AK的方式相同
		 * TODO 各位路人不知道是否有更好的方法？
		*/		
		//开始
		String target = request.getRequestURI();
		String contextPath = request.getServletContext().getContextPath();
		Integer contextPathLength = (contextPath == null || "/".equals(contextPath) ? 0 : contextPath.length());
		if (contextPathLength != 0)
			target = target.substring(contextPathLength);
		//结束
		if (aks.contains(target))
			return target;
		int i = target.lastIndexOf("/");
		if (i != -1) {
			target = target.substring(0, i);
			if (aks.contains(target))
				return target;
		}
		return "";
	}

}
