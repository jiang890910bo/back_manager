package com.cnvp.paladin.core;

import org.beetl.core.GroupTemplate;

import com.alibaba.druid.filter.stat.StatFilter;
//import com.cnvp.handler.RewriteHandler;
import com.cnvp.paladin.handler.CookieHandler;
import com.cnvp.paladin.handler.RewriteHandler;
import com.cnvp.paladin.handler.SessionHandler;
import com.cnvp.paladin.interceptor.Global;
import com.cnvp.paladin.interceptor.Shiro;
import com.cnvp.paladin.kit.ConfigKit;
import com.cnvp.paladin.kit.PropertyKit;
import com.cnvp.paladin.plugin.AutoTableBindPlugin;
import com.cnvp.paladin.plugin.TableNameStyle;
import com.cnvp.paladin.utils.AutoRoutes;
import com.cnvp.paladin.utils.beetl.BeetlRenderFactory;
import com.cnvp.paladin.utils.beetl.BeetlShiro;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
public class appConfig extends JFinalConfig {
	private Routes routes;
    public static DruidPlugin dp;
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
	    loadPropertyFile("app.properties");
		//beetl集成
		me.setMainRenderFactory(new BeetlRenderFactory());	
		//可对beetl设置共享变量
//		GroupTemplate gt=BeetlRenderFactory.groupTemplate;
//		gt.registerFunctionPackage("shiro", BeetlShiro.class);
//		Map<String,Object> shared = new HashMap<String,Object>();
//		shared.put("key","value");
//		groupTemplate.setSharedVars(shared);
		//是否启用开发模式
		me.setDevMode(PropertyKit.getToBoolean("app_devModel"));
		
//		me.setViewType(ViewType.OTHER);
		me.setEncoding("UTF-8");	
		
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		this.routes = me;
		//路由自动映射
		String default_contorller = getProperty("default_contorller", "Index");
		AutoRoutes.add(me,default_contorller);
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置数据库连接池插件
		dp = new DruidPlugin(ConfigKit.get("jdbc.url"),ConfigKit.get("jdbc.username"),ConfigKit.get("jdbc.password"),ConfigKit.get("jdbc.driver"));
		dp.addFilter(new StatFilter());
		me.add(dp);
		//添加自动绑定model与表插件
		AutoTableBindPlugin autoTableBindPlugin = new AutoTableBindPlugin(dp, TableNameStyle.LOWER_UNDERLINE);
		autoTableBindPlugin.setShowSql(true);
		autoTableBindPlugin.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		me.add(autoTableBindPlugin);
//		me.add(new SqlInXmlPlugin());

		me.add(new EhCachePlugin()); 
		me.add(new ShiroPlugin(routes));
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new Shiro());
		me.add(new Global());
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		me.add(new RewriteHandler());//将session里的参数传递到request中直接得到
		me.add(new SessionHandler());//将session里的参数传递到request中直接得到
		me.add(new CookieHandler());//将cookie里的参数传递到request中直接得到
	}
}
