package com.cnvp.paladin.utils;

import java.util.List;

import com.cnvp.paladin.core.BaseController;
import com.cnvp.paladin.kit.ClassKit;
import com.jfinal.config.Routes;

/**
 * 用途：根据类名完整路径自动映射route与controller关系 说明：
 * 1、com.cnvp.controllers为根分组，其下面直接的controller为根控制器
 * 2、com.cnvp.controllers下的包为其他分组 3、各分组中以Index开头的控制器为默认控制器，可在app.properties中进行配置
 * 【重要约定】 1、根控制器，不得与分组重名 2、根控制器中的方法，不得与分组中的控制器重名 3、同一分组内，控制器不得与index控制器中的方法重名
 * 比如有了Admin分组，根控制器就不能有AdminController
 */
public class AutoRoutes {
	@SuppressWarnings("unchecked")
	public static void add(Routes me, String defaultContorller) {
		String controllersPackge = "com.cnvp.paladin.controller";
		List<String> classNames = ClassKit.getClassName(controllersPackge);
		for (String className : classNames) {
			String routeFromClass = className.replace(controllersPackge + ".",
					"").replace("Controller", "");
			String[] layers = routeFromClass.split("\\.");
			String route = "";
			for (int i = 0; i < layers.length; i++) {
				if (i != (layers.length - 1)
						|| !layers[i].equals(defaultContorller))
					route += "/" + layers[i];
			}
			if ("".equals(route))
				route = "/";
			Class<BaseController> forClass;
			try {
				forClass = (Class<BaseController>) Class.forName(className);
			} catch (ClassNotFoundException e) {
				break;
			}
			System.out.println(route + "=>" + className);
			me.add(route, forClass);
		}
	}
}
