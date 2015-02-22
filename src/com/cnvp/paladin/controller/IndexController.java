package com.cnvp.paladin.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;

import com.cnvp.paladin.core.BaseController;
import com.cnvp.paladin.kit.PropertyKit;
import com.cnvp.paladin.kit.StringKit;
import com.cnvp.paladin.model.SysNav;
import com.cnvp.paladin.model.SysUser;
import com.cnvp.paladin.service.NavService;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.JFinal;
import com.jfinal.kit.EncryptionKit;

public class IndexController extends BaseController {
	public void index() {
		List<SysNav> tree = NavService.getTreeMap(0);
		setAttr("tree", tree);
	}	
	public void welcome() {
	}
	public void password(){
		if("POST".equals(getRequest().getMethod())){
			SysUser currentUser  = (SysUser) SecurityUtils.getSubject().getPrincipal();
			currentUser = SysUser.dao.findById(currentUser.get("id"));
			String old = getPara("old");
			String new1 = getPara("new1");
			String new2 = getPara("new2");
			if (StringKit.isBlank(old))
				alertAndGoback("原始密码不能为空");
			else if (!currentUser.getStr("password").equals(EncryptionKit.md5Encrypt(old))) 
				alertAndGoback("原始密码错误");			
			else if (!new1.equals(new2))
				alertAndGoback("新密码两次输入不一致");
			else{
				SysUser.dao
				.set("id", currentUser.get("id"))
				.set("password", EncryptionKit.md5Encrypt(new1))
				.update();
				alertAndGoback("密码修改成功");
			}
		}
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
	@ClearInterceptor(ClearLayer.ALL)
	public void test(){
		List<String> actionKeys = JFinal.me().getAllActionKeys();
		for (String ak : actionKeys) {
			System.out.println(ak);
		}
//		List<SysDept> deptModels = new SysDept().findByModel();		
//		TreeKit deptTree = new TreeKit();
//		deptTree.importModels(deptModels);
//		deptTree.getSelectMap();
//		renderNull();
	}
}
