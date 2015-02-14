package com.cnvp.paladin.controller;

import com.cnvp.paladin.core.BaseController;
import com.cnvp.paladin.model.SysDept;

public class DeptController extends BaseController {
	
	public void index(){
		setAttr("page", SysDept.dao.paginate(getParaToInt(0, 1), 10));
	}
	
	public void create(){
		if(isPost()){
			if(getModel(SysDept.class,"sysdept").save())
				redirect(getControllerKey());
				return;
		}
		setAttr("data", new SysDept());
	}

	public void update(){
		if(isPost()){
			if(getModel(SysDept.class,"sysdept").save())
				redirect(getControllerKey());
				return;
		}
		setAttr("data", SysDept.dao.findById(getParaToInt()));
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