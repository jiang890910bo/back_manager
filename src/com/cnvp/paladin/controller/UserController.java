package com.cnvp.paladin.controller;

import com.cnvp.paladin.core.BaseController;
import com.cnvp.paladin.kit.StringKit;
import com.cnvp.paladin.kit.tree.TreeKit;
import com.cnvp.paladin.model.SysDept;
import com.cnvp.paladin.model.SysRole;
import com.cnvp.paladin.model.SysUser;
import com.cnvp.paladin.model.SysUserRole;
import com.jfinal.kit.EncryptionKit;

public class UserController extends BaseController {
	public void index(){
		setAttr("page", SysUser.dao.paginate(getParaToInt(0, 1), 10));
	}
	public void create(){
		if(isPost()){
			SysUser model = getModel(SysUser.class,"user");
			String psw = model.getStr("password");
			model.set("password",EncryptionKit.md5Encrypt(psw));	
			model.set("create_time", System.currentTimeMillis());
			model.set("create_user_id", 1);
			if(model.save()){
				model.deleteAllRoles();
				String[] role_ids = getParaValues("roles");
				if(role_ids!=null)
				for (String role_id : role_ids)
					new SysUserRole().set("user_id", model.getInt("id")).set("role_id", role_id).save();
				redirect(getControllerKey());
				return;
			}
		}
		TreeKit deptTree = new TreeKit();
		deptTree.importModels(new SysDept().findByModel());
		//所有角色数据
		setAttr("allroles",SysRole.dao.findAll());
		setAttr("depts",deptTree.getSelectMap());
		setAttr("data", new SysUser());
		render("form.html");
	}
	public void update(){
		if(isPost()){
			SysUser model = getModel(SysUser.class,"user");
			if (StringKit.isBlank(getPara("user.password")))
				model.set("password", SysUser.dao.findById(getParaToInt()).get("password"));
			else{
				String psw = model.getStr("password");
				model.set("password",EncryptionKit.md5Encrypt(psw));				
			}
			model.set("id", getParaToInt()).set("update_time", System.currentTimeMillis()).set("update_user_id", 1);
			if(model.update()){
				model.deleteAllRoles();
				String[] role_ids = getParaValues("roles");
				if(role_ids!=null)
				for (String role_id : role_ids)
					new SysUserRole().set("user_id", model.getInt("id")).set("role_id", role_id).save();
				redirect(getControllerKey());
			}
			return;
		}
		//当前用户数据
		SysUser user = SysUser.dao.findById(getParaToInt());
		setAttr("data",user);
		//部门select数据
		TreeKit deptTree = new TreeKit();
		deptTree.importModels(new SysDept().findByModel());
		setAttr("depts",deptTree.getSelectMap());
		//所有角色数据
		setAttr("allroles",SysRole.dao.findAll());
		//用户现有角色数据
		setAttr("useroles",user.getRoles());
		render("form.html");
	}
	public void delete(){
		SysUser model = SysUser.dao.findById(getParaToInt());
		if(model==null){
			goBack();return;
		}			
		if(SysUser.dao.findById(getParaToInt()).delete())
			redirect(getControllerKey());
		else
			goBack();
	}
	public void deleteAll(){
		Integer[] ids=getParaValuesToInt("id");
		for (Integer id : ids) {
			SysUser.dao.findById(id).delete();
//			System.out.println(id);
		}
		redirect(getControllerKey());
	}
}
