package com.cnvp.paladin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnvp.paladin.core.BaseController;
import com.cnvp.paladin.model.SysRole;
import com.cnvp.paladin.model.SysRoleRes;
import com.jfinal.kit.JsonKit;

public class RoleController extends BaseController {
	public void index(){
		setAttr("page", SysRole.dao.paginate(getParaToInt(0, 1), 10));
	}
	public void getlist(){
		Map<String, Object> json =  new HashMap<String, Object>();
		List<SysRole> data = SysRole.dao.where("");
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).hasChild())
				data.get(i).getAttrs().put("hasChild",true);
			else
				data.get(i).getAttrs().put("hasChild",false);
		}
		json.put("data", data);
		renderJson(json);
	}
	public void create(){
		if(isPost()){
			if(getModel(SysRole.class,"sysgroup").save())
				redirect(getControllerKey());
				return;
		}
		setAttr("data", new SysRole());
		render("form.html");
	}

	public void update(){
		if(isPost()){
			if(getModel(SysRole.class,"sysgroup").set("id", getParaToInt(0)).update())
				redirect(getControllerKey());
				return;
		}
		SysRole model = SysRole.dao.findById(getParaToInt(0));
		setAttr("data", model);
		setAttr("res", JsonKit.toJson(model.getResidList()));
		render("form.html");
	}
	public void delete(){
		if (SysRole.dao.findById(getParaToInt(0)).delete()) 
			redirect(getControllerKey());
		else
			alertAndGoback("删除失败");
	}
	public void deleteAll(){
		Integer[] ids=getParaValuesToInt("id");
		for (Integer id : ids) {
			SysRole.dao.findById(id).delete();
		}
		redirect(getControllerKey());
	}
	public void set_res(){
		int role_id = getParaToInt(0);
		int res_id = getParaToInt(1);
		boolean  flg = getParaToBoolean("checked");
		if (flg) {
			SysRoleRes rr = new SysRoleRes().set("role_id", role_id).set("res_id",res_id);
			if (rr.findByModel().size()==0) 
				rr.save();
		}else{
			//删除
			SysRoleRes rr = new SysRoleRes().set("role_id", role_id).set("res_id",res_id).findFirstByModel();
			if(rr!=null) rr.delete();
		}
		Map<String, Object> r = new HashMap<String, Object>();
		r.put("success", true);
		renderJavascript(JsonKit.toJson(r));
		return;
				
	}
	public void set_res_all(){
		int role_id = getParaToInt(0);
		boolean flg = getParaToBoolean("checked");
		SysRole.dao.checkAll(role_id, flg);
		
		SysRole model = SysRole.dao.findById(role_id);
		renderJavascript(JsonKit.toJson(model.getResidList()));
	}
}
			