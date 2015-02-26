package com.cnvp.paladin.model;

import java.util.Collection;
import java.util.List;

import com.cnvp.paladin.core.BaseModel;
import com.jfinal.plugin.activerecord.Db;
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
	public int deleteAllRoles(){
		return SysUserRole.dao.deleteAllByUserId(getInt("id"));
	}
	public List<SysUserRole> getRoles(){
		return SysUserRole.dao.findByUserId(getInt("id"));
	}
	public Collection<String> getRoleNameList(){
		List<String> r = Db.queryColumn("select cname from "+ getDbPerfix() +"sys_role r "
				+ "left join "+ getDbPerfix() +"sys_user_role ur on r.id=ur.role_id "
				+ "where ur.userid=?", getInt("id"));
		return r;
	}
}
