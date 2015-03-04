package com.cnvp.paladin.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cnvp.paladin.core.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

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
	public List<String> getRes(){
		List<SysUserRole> roles = getRoles();
		StringBuffer sql = new StringBuffer();
		sql.append("select res.id,res.id,res.code_route from cnvp_sys_res res ");
		sql.append("left join cnvp_sys_role_res role_res on  role_res.res_id = res.id ");
		sql.append("right join cnvp_sys_role role on role_res.role_id = role.id ");
		sql.append("where role.id in(");
		for (int i = 0; i < roles.size(); i++) {
			if(i!=0)sql.append(",");
			sql.append(roles.get(i).get("role_id"));
		}
		sql.append(") ");
		sql.append("group by res.id ");
		sql.append("order by id asc ");
		List<Record> code_routes = Db.find(sql.toString());
		List<String> reses =  new ArrayList<String>();
		Iterator<Record> it = code_routes.iterator();
		while (it.hasNext()) {
			Record r = (Record) it.next();
			reses.add(r.getStr("code_route"));
		}
		return reses;

	}
}
