package com.cnvp.paladin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cnvp.paladin.core.BaseController;
import com.cnvp.paladin.model.SysRes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.JsonKit;

public class ResourceController extends BaseController {
	//TODO 完善资源表单高级配置：actionKey选择、类型选择等……
	public void index(){
		render("index.html");
	}
	public void create(){
		Integer pid = getParaToInt(0,0);
		if(isPost()){
			SysRes model =getModel(SysRes.class,"sysres");
			if(model.save()){
				Map<String, Object> r = new HashMap<String, Object>();
				r.put("success", true);
				r.put("data", model.toNodeData());
				renderJavascript(JsonKit.toJson(r));
				return;
			}
		}
		setAttr("aks", JFinal.me().getAllActionKeys());
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
				r.put("data", model.toNodeData());
				renderJavascript(JsonKit.toJson(r));
				return;
			}				
		}
		setAttr("aks", JFinal.me().getAllActionKeys());
		setAttr("data", SysRes.dao.findById(id).addParentCode());
		render("form.html");
	}
	public void delete(){
		int id = getParaToInt();
		if (SysRes.dao.findById(id).delete()){
			Map<String, Object> r = new HashMap<String, Object>();
			r.put("success", true);
			r.put("data", new HashMap<String, Object>().put("id", id));
			renderJavascript(JsonKit.toJson(r));
			return;
		}
		else{
			Map<String, Object> r = new HashMap<String, Object>();
			r.put("success", false);
			r.put("msg", "删除失败");
			renderJavascript(JsonKit.toJson(r));
			return;
		}
	}
	public void getlist(){
		Integer pid = getParaToInt("id",0);
		List<SysRes> models= SysRes.dao.where("pid=?",pid);
		List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
		Iterator<SysRes> it = models.iterator();
		while (it.hasNext()) {
			SysRes model = it.next();			
			nodes.add(model.toNodeData());
		}
		renderJson(nodes);
	}
	public void refresh(){
		List<SysRes> reslist = SysRes.dao.findAll();
		Iterator<SysRes> it = reslist.iterator();
		while (it.hasNext()) {
			SysRes model = it.next();
			model.addParentCode();
			model.set("code_route", model.get("parent_code")+":"+model.get("code"));
			model.update();
		}
		redirect(getControllerKey());
	}
}