package com.cnvp.paladin.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnvp.paladin.core.BaseModel;

@SuppressWarnings("serial")
public class SysRes extends BaseModel<SysRes> {
	public static final SysRes dao = new SysRes();
	public boolean hasChild(){		
		return exists("pid=?",get("id"));		
	}
	public boolean hasParent(){		
		return exists("id=?",get("pid"));		
	}
	public SysRes addParentCode(){
		Map<String,Object> attrs = getAttrs();
		if(attrs.get("id")!=null&&!this.hasParent())
			return this;
		List<String> codes = getParentCodes(getInt("pid"));
		if(codes.size()==0) return this;
		StringBuilder codeStr = new StringBuilder();
		for (int i = 0; i < codes.size(); i++) {
			if (i!=0)
				codeStr.append(":");
			codeStr.append(codes.get(i));			
		}
		attrs.put("parent_code", codeStr.toString());
		return this;
	}
	public List<String> getParentCodes(Integer pid){
		List<String> codes = new ArrayList<String>();
		SysRes parent = SysRes.dao.findById(pid);
		if(parent==null) return codes;
		codes.add(0,parent.getStr("code"));
		if(parent.hasParent())
			getParentCode(parent.getInt("pid"),codes);
		return codes;
	}
	public void getParentCode(Integer pid,List<String> codes){
		SysRes parent = SysRes.dao.findById(pid);
		if(parent==null) return;
		codes.add(0,parent.getStr("code"));
		if(parent.hasParent())		
			getParentCode(parent.getInt("pid"),codes);
	}
	public Map<String, Object> toNodeData(){
		Map<String, Object> node = new HashMap<String, Object>();
		node.put("id",get("id").toString());
		node.put("isParent",hasChild());
		node.put("cname",get("cname").toString());
		node.put("cname",get("code").toString());
		node.put("name",get("cname").toString()+" [<font color=\"blue\">"+get("code").toString()+"</font>] ");
		node.put("pid",get("pid").toString());
		return node;
	}
}
	