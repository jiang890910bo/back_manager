package com.cnvp.paladin.model;

import java.util.List;

import com.cnvp.paladin.core.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class SysUserRole extends BaseModel<SysUserRole> {
	public static final SysUserRole dao = new SysUserRole();
	public Page<SysUserRole> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from "+getTableName()+" order by id asc");
	}	
	public int deleteAllByUserId(int user_id){
		return Db.update("delete from "+getTableName()+" where user_id=?",user_id);
	}
	public List<SysUserRole> findByUserId(int user_id){
		return where("user_id = ?",user_id);
	}
}