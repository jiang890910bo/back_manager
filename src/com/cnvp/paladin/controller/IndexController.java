package com.cnvp.paladin.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.cnvp.paladin.core.BaseController;
import com.cnvp.paladin.kit.PropertyKit;
import com.cnvp.paladin.kit.StringKit;
import com.cnvp.paladin.model.SysNav;
import com.cnvp.paladin.model.SysUser;
import com.cnvp.paladin.service.NavService;
import com.jfinal.kit.EncryptionKit;

public class IndexController extends BaseController {
	public void index() {
		List<SysNav> tree = NavService.getTreeMap(0);
		setAttr("tree", tree);
	}	
	public void welcome() {
	}
	public void profile() {
		SysUser currentUser  = (SysUser) SecurityUtils.getSubject().getPrincipal();
		if("POST".equals(getRequest().getMethod())){
			if(getModel(SysUser.class,"user").set("id", currentUser.getInt("id")).update())
				redirect("/profile");
				return;
		}
		setAttr("data", SysUser.dao.findById(currentUser.getInt("id")));
	}
	public void closed() {
		setAttr("app_remark", PropertyKit.get("app_remark"));
		render("common/close.html");
	}
}
