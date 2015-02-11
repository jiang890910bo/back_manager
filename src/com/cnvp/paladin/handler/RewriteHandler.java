package com.cnvp.paladin.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

/**
 * 用途：重写Route，还有问题，需要改进
 * 说明：现在是简单实现用正则进行匹配，此方法还有待升级更多功能
 */
public class RewriteHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request,HttpServletResponse response, boolean[] isHandled) {
//		String fromUrl = target;
//		String toUrl = "";
//		Prop prop =  PropKit.use("urlrules.properties", Const.DEFAULT_ENCODING);
//		Properties p = prop.getProperties();
//		Enumeration<Object> UrlRules = p.keys();
//		while (UrlRules.hasMoreElements()){
//			String reg = (String) UrlRules.nextElement();
//			Pattern pattern = Pattern.compile("^"+reg+"$");
//			if (pattern.matcher(fromUrl).matches()) {
//				toUrl = p.getProperty(reg);
//				break;
//			}
//		}
//		target = toUrl;
//		System.out.println(target);
//		System.out.println( PropertyKit.get("app_status"));
//		if (PropertyKit.get("app_status").equals("close")) {
//			target = "/closed";
//		}
		nextHandler.handle(target, request, response, isHandled);
	}
}
