package com.cnvp.paladin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnvp.paladin.kit.ConfigKit;
import com.cnvp.paladin.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class GeneratoService {

	public static List<Record> getFields(String tableName) {
		List<Record> cols = Db.find("SHOW FULL COLUMNS FROM "+tableName+" where Extra<>'auto_increment'");
		System.out.println(cols.size());
		for (int i = 0; i < cols.size(); i++) {
			Map<String, Object> field = new HashMap<String, Object>();
			field = cols.get(i).getColumns();
			String fieldType = field.get("Type").toString();
			String[] type = StringKit.getFieldTypeLength(fieldType);
			field.put("Type", type[0]);
			field.put("Length", type[1]);
			ArrayList<String> validate = new ArrayList<String>();
			if ("NO".equals(field.get("Null").toString())) {
				validate.add("required:请填写"+field.get("Comment"));
			}
			field.put("validate", StringKit.implode(validate, ","));

		}
		return cols;
	}	
	public static List<Record> getTables() {
		String dbPrefix = ConfigKit.get("jdbc.dbPrefix");
		return getTables(dbPrefix);
	}
	public static List<Record> getTables(String dbPrefix) {
		List<Record> tables = Db.find("SHOW TABLE STATUS LIKE '" + dbPrefix
				+ "%'");
		for (int i = 0; i < tables.size(); i++) {
			Map<String, Object> table = new HashMap<String, Object>();
			table = tables.get(i).getColumns();
			String tableName = table.get("name").toString();			
			table.put("modelname", StringKit.tableName_2_modelName(tableName));

		}
		return tables;
	}
	public static Record getTable(String dbPrefix,String tableName) {
		return Db.findFirst("SHOW TABLE STATUS where Name=?",(dbPrefix + tableName));	
	}
	public static Record getTable(String tableName) {
		return Db.findFirst("SHOW TABLE STATUS where Name=?",tableName);	
	}
}
