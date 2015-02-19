package com.cnvp.paladin.model;

import com.cnvp.paladin.core.BaseModel;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class SysGroup extends BaseModel<SysGroup> {
	public static final SysGroup dao = new SysGroup();
	public Page<SysGroup> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from "+getTableName()+" order by cname asc");
	}
	public boolean hasChild(){
		if (SysGroup.dao.where("pid=?",getInt("id")).size()>0) 
			return true;
		else
			return false;
	}	
}