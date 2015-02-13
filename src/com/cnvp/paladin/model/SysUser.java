package com.cnvp.paladin.model;

import com.cnvp.paladin.core.BaseModel;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class SysUser extends BaseModel<SysUser> {
	public static final SysUser dao = new SysUser();
	/**
	 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public Page<SysUser> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from "+getTableName()+" order by id asc");
	}
}
