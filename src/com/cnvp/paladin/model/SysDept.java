package com.cnvp.paladin.model;

import com.cnvp.paladin.core.BaseModel;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class SysDept extends BaseModel<SysDept> {
	public static final SysDept dao = new SysDept();
	public Page<SysDept> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from "+getTableName()+" order by id asc");
	}
	public boolean hasChild(){
		if (SysDept.dao.set("pid", get("id")).findByModel().size()>0) 
			return true;
		else
			return false;
	}
}