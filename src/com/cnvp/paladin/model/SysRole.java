package com.cnvp.paladin.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	public List<String> getResidList(){
		List<String> r = new ArrayList<String>();
		List<SysRoleRes> rs = new SysRoleRes().set("role_id", get("id")).findByModel();
		Iterator<SysRoleRes> it = rs.iterator();
		while (it.hasNext()) {
			SysRoleRes m = (SysRoleRes) it.next();
			r.add(m.get("res_id").toString());
		}
		return r;		
	}
	public void checkAll(Integer role_id,boolean flg){
		SysRoleRes.dao.deleteAll("role_id=?",role_id);
		if(flg==false) return;
		List<SysRes> srs =  SysRes.dao.findAll();
		Iterator<SysRes> it = srs.iterator();
		while (it.hasNext()) {
			SysRes sr = (SysRes) it.next();
			SysRoleRes srr = new SysRoleRes();
			srr.set("role_id", role_id);
			srr.set("res_id", sr.getInt("id"));
			srr.save();
		}
	}
}