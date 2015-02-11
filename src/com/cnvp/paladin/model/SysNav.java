package com.cnvp.paladin.model;

import com.cnvp.paladin.core.BaseModel;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class SysNav extends BaseModel<SysNav> {
	public static final SysNav dao = new SysNav();
	/**
	 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public Page<SysNav> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from cnvp_sys_nav order by id asc");
	}
}