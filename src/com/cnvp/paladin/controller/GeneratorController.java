package com.cnvp.paladin.controller;

import java.util.List;

import com.cnvp.paladin.core.BaseController;
import com.cnvp.paladin.service.GeneratoService;
import com.jfinal.plugin.activerecord.Record;

public class GeneratorController extends BaseController {
	
	public void index() {
		List<Record> tables = GeneratoService.getTables();
		setAttr("tables", tables);
		setAttr("type", getPara(0));
		render("tables.html");
	}	
	public void model_code(){
		String tableName = getPara(0);
		String modelName = getPara(1);
		List<Record> cols =  GeneratoService.getFields(tableName);
		setAttr("table", GeneratoService.getTable(tableName));
		setAttr("modelName", modelName);
		setAttr("cols", cols);
	}
	public void view_code(){
		String tableName = getPara(0);
		String modelName = getPara(1);
		List<Record> cols =  GeneratoService.getFields(tableName);
		setAttr("table", GeneratoService.getTable(tableName));
		setAttr("modelName", modelName);
		setAttr("cols", cols);
	}

	
}
