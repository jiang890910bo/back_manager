package com.cnvp.paladin.controller;

import com.cnvp.paladin.core.BaseController;
import com.cnvp.paladin.kit.StringKit;
import com.cnvp.paladin.model.User;
import com.jfinal.kit.EncryptionKit;

public class UserController extends BaseController {
	public void index(){
		setAttr("page", User.dao.paginate(getParaToInt(0, 1), 10));
	}
	public void create(){
		if("POST".equals(getRequest().getMethod())){
			User model = getModel(User.class,"user");
			model.set("create_time", System.currentTimeMillis());
			model.set("create_user_id", 1);
			if(model.save())
				redirect(getControllerKey());
				return;
		}
		setAttr("data", User.dao);
		render("form.html");
	}
	public void update(){
		if("POST".equals(getRequest().getMethod())){
			User model = getModel(User.class,"user");
			if (StringKit.isBlank(getPara("user.password")))
				model.set("password", User.dao.findById(getParaToInt()).get("password"));
			else{
				String psw = model.getStr("password");
				model.set("password",EncryptionKit.md5Encrypt(psw));				
			}
			model
			.set("id", getParaToInt())
			.set("update_time", System.currentTimeMillis())
			.set("update_user_id", 1);
			if(model.update())
				redirect(getControllerKey());
			return;
		}
		setAttr("data",User.dao.findById(getParaToInt()) );
		render("form.html");
	}
	public void delete(){
		User model = User.dao.findById(getParaToInt());
		if(model==null){
			goBack();return;
		}			
		if(User.dao.findById(getParaToInt()).delete())
			redirect(getControllerKey());
		else
			goBack();
	}
	public void deleteAll(){
		Integer[] ids=getParaValuesToInt("id");
		for (Integer id : ids) {
			User.dao.findById(id).delete();
//			System.out.println(id);
		}redirect(getControllerKey());
	}
}
