package com.cnvp.paladin.kit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cnvp.paladin.core.BaseModel;

public class JstreeKit {
	public static <T extends BaseModel<T>> List<JstreeNode> getNode(List<T> models){
		Map<String,String> fieldMapping = new HashMap<String, String>();
		fieldMapping.put("id", "id");
		fieldMapping.put("parent", "parent");
		fieldMapping.put("text", "text");
		return getNode(models,fieldMapping);		
	}
	public static <T extends BaseModel<T>> List<JstreeNode> getNode(List<T> models,Map<String,String> fieldMapping){
		Iterator<T> it = models.iterator();
		List<JstreeNode> nodes = new ArrayList<JstreeNode>();
		while (it.hasNext()) {
			JstreeNode node = new JstreeNode();
			T model = (T) it.next();
			node.id = model.get(fieldMapping.get("id")).toString();
			node.parent = model.get(fieldMapping.get("parent")).toString();
			node.text = model.get(fieldMapping.get("text")).toString();
			if (fieldMapping.get("icon")!=null) 
				node.icon = model.get(fieldMapping.get("icon")).toString();
			if (fieldMapping.get("opened")!=null) 
				node.state.opened = model.get(fieldMapping.get("opened")).toString();
			if (fieldMapping.get("disabled")!=null) 
				node.state.disabled = model.get(fieldMapping.get("disabled")).toString();
			if (fieldMapping.get("selected")!=null) 
				node.state.selected = model.get(fieldMapping.get("selected")).toString();
			nodes.add(node);
		}
		return nodes;
	}
}