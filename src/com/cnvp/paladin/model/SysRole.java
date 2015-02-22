package com.cnvp.paladin.model;

import com.cnvp.paladin.core.BaseModel;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class SysRole extends BaseModel<SysRole> {
	public static final SysRole dao = new SysRole();
	public Page<SysRole> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from "+getTableName()+" order by cname asc");
	}
	public boolean hasChild(){
		if (SysRole.dao.where("pid=?",getInt("id")).size()>0) 
			return true;
		else
			return false;
	}	
}