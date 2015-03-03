package com.cnvp.paladin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cnvp.paladin.core.BaseController;
import com.cnvp.paladin.model.SysRes;
import com.jfinal.kit.JsonKit;

public class Resource extends BaseController {
	public void index(){
		render("index.html");
	}
	public void create(){
		Integer pid = getParaToInt(0,0);
		if(isPost()){
			SysRes model =getModel(SysRes.class,"sysres");
			if(getModel(SysRes.class,"sysres").save()){
				Map<String, Object> r = new HashMap<String, Object>();
				r.put("success", true);
				//TODO 无法获取添加后的主键
				r.put("data", model);
				renderJavascript(JsonKit.toJson(r));
				return;
			}
		}
		setAttr("data", new SysRes().set("pid", pid).addParentCode());
		render("form.html");
	}
	public void update(){
		Integer id = getParaToInt(0);
		if(isPost()){
			SysRes model = getModel(SysRes.class,"sysres").set("id",id);
			if(model.update()){
				Map<String, Object> r = new HashMap<String, Object>();
				r.put("success", true);
				r.put("data", model);
				renderJavascript(JsonKit.toJson(r));
				return;
			}				
		}
		setAttr("data", SysRes.dao.findById(id).addParentCode());
		render("form.html");
	}
	public void getlist(){
		Integer pid = getParaToInt("id",0);
		List<SysRes> models= SysRes.dao.where("pid=?",pid);
		List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
		Iterator<SysRes> it = models.iterator();
		while (it.hasNext()) {
			SysRes model = it.next();
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("id",model.get("id").toString());
			node.put("isParent",model.hasChild());
			node.put("name",model.get("cname").toString()+"["+model.get("code").toString()+"]");			
			nodes.add(node);
		}
		renderJson(nodes);
	}
}
