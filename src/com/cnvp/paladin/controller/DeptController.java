package com.cnvp.paladin.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnvp.paladin.core.BaseController;
import com.cnvp.paladin.model.SysDept;

public class DeptController extends BaseController {
	
	public void index(){
		setAttr("page", SysDept.dao.paginate(getParaToInt(0, 1), 10));
	}
	public void getlist(){
		Map<String, Object> json =  new HashMap<String, Object>();
		List<SysDept> data = SysDept.dao.where("");
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
		Integer pid = getParaToInt(0, 0);
		if(isPost()){
			if(getModel(SysDept.class,"sysdept").save())
				redirect(getControllerKey());
				return;
		}
		SysDept data = new SysDept();
		data.set("pid", pid);
		setAttr("data", data);
		render("form.html");
	}

	public void update(){
		if(isPost()){
			if(getModel(SysDept.class,"sysdept").set("id", getParaToInt()).update())
				redirect(getControllerKey());
				return;
		}
		setAttr("data", SysDept.dao.findById(getParaToInt()));
		render("form.html");
	}
	public void delete(){
		if (SysDept.dao.findById(getParaToInt()).delete()) 
			redirect(getControllerKey());
		else
			renderText("删除失败");
	}
	public void deleteAll(){
		Integer[] ids=getParaValuesToInt("id");
		for (Integer id : ids) {
			SysDept.dao.findById(id).delete();
		}
		redirect(getControllerKey());
	}
	
}