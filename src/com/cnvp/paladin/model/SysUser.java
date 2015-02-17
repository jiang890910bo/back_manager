package com.cnvp.paladin.model;

import com.cnvp.paladin.core.BaseModel;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class SysUser extends BaseModel<SysUser> {
	public static final SysUser dao = new SysUser();
	public Page<SysUser> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from "+getTableName()+" order by id asc");
	}
	public String getDeptName(){
		Integer dept_id = getInt("dept_id");
		SysDept dept = SysDept.dao.findById(dept_id);
		if (dept==null)
			return null;
		else{
			return dept.getStr("cname");
		}
	}
}
