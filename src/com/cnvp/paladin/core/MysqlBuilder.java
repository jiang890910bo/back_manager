package com.cnvp.paladin.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cnvp.paladin.kit.ConfigKit;
import com.cnvp.paladin.kit.StringKit;

/**
 * 用途：
 * 说明：
 */
public class MysqlBuilder {

	public String dbPerfix = ConfigKit.getDbPrefix();
	public String optName = "select";
	public String select = "*";
	public boolean distinct = false;
	public String condition = "";
	public List<Object> params;
	public Integer limit = -1;
	public Integer offset = -1;
	public String order = "";
	public String group = "";
	public String join = "";
	public String having = "";
	public String tableName = "";

	public MysqlBuilder addCondition(String condition) {
		return addCondition(condition,"AND");
	}

	public MysqlBuilder addCondition(String condition, Object... params) {
		return addCondition(condition,"AND", params);
	}

	public MysqlBuilder addCondition(String condition,String operator,Object... params) {
		if (StringKit.notBlank(condition)) {
			this.condition = "(" + this.condition + ") " + operator + " ("
					+ condition + ")";
			if (params.length>0)for (int i = 0; i < params.length; i++)
				this.params.add(params[i]);
		} else {
			this.condition = condition;
			if (params.length>0)for (int i = 0; i < params.length; i++)
				this.params.add(params[i]);
		}
		return this;
	}

	public MysqlBuilder addSearchCondition(String column, String keyword) {
		return addSearchCondition(column, keyword, "AND");
	}

	public MysqlBuilder addSearchCondition(String column, String keyword,String operator) {
		return addSearchCondition(column, keyword, operator, "LIKE");
	}

	public MysqlBuilder addSearchCondition(String column, String keyword,
			String operator, String like) {
		like = like.toUpperCase();
		if ("LIKE".equals(like) && "NOT LIKE".equals(like))
			like = "LIKE";
		if (StringKit.isBlank(keyword))
			return this;
		keyword.replace("%", "\\%").replace("_", "\\_").replace("\\", "\\\\");
		String condition = column + " " + like + " ?";
		keyword = "%" + keyword + "%";
		return addCondition(condition,operator,keyword);
	}
	public MysqlBuilder addInCondition(String column,Collection<Object> values,String operator)
	{
		if (values.size()==0) 
			return this;
		StringBuffer condition = new StringBuffer(column+" in(");
		for (int i = 0; i < values.size(); i++) {
			if(i!=0)
				condition.append(",");
			condition.append("?");
		}
		condition.append(")");
		this.condition = "(" + this.condition + ") " + operator + " ("+ condition.toString() + ")";
		Iterator<Object> it = values.iterator();
		while (it.hasNext()) {
			Object obj = (Object) it.next();
			this.params.add(obj);
		}
		return this;
	}
	public MysqlBuilder addNotInCondition(String column,Collection<Object> values,String operator)
	{
		if (values.size()==0) 
			return this;
		StringBuffer condition = new StringBuffer(column+" NOT IN(");
		for (int i = 0; i < values.size(); i++) {
			if(i!=0)
				condition.append(",");
			condition.append("?");
		}
		condition.append(")");
		this.condition = "(" + this.condition + ") " + operator + " ("+ condition.toString() + ")";
		Iterator<Object> it = values.iterator();
		while (it.hasNext()) {
			Object obj = (Object) it.next();
			this.params.add(obj);
		}
		return this;
	}
	public MysqlBuilder addBetweenCondition(String column,Object valueStart,Object valueEnd,String operator){
		if(StringKit.isBlank(valueStart.toString())||StringKit.isBlank(valueEnd.toString()))
			return this;
		String condition = column +" BETWEEN ? AND ?";		
		return this.addCondition(condition,valueStart,valueEnd);
	}
	
	/**
	 * 在此之前，必须给给实例的表名赋值
	 */
	public String buildSql(){
		if(StringKit.isBlank(this.tableName)){
			if(ConfigKit.isDevMode()) System.err.println("tableName为空，无法生成sql");
			return null;
		}
		if("select".equals(optName.toLowerCase()))
			return bulidSelect();
		else if("delete".equals(optName.toLowerCase()))
			return bulidDelete();
		return null;		
	}
	private String bulidDelete(){
		StringBuffer sql = new StringBuffer();
		sql.append("delete from "+this.tableName);
		if(StringKit.notBlank(this.condition))
			sql.append(" where "+ this.condition);
		return sql.toString();
	}
	private String bulidSelect(){
		StringBuffer sql = new StringBuffer();
		sql.append("select "+this.select +" t from "+this.tableName);
		if(StringKit.notBlank(this.join))
			sql.append(" "+this.join);
		if(StringKit.notBlank(this.condition))
			sql.append(" where "+ this.condition);
		if(StringKit.notBlank(this.group))
			sql.append(" group by "+ this.group);
		if(StringKit.notBlank(this.having))
			sql.append(" having by "+ this.having);
		if(StringKit.notBlank(this.order))
			sql.append(" order by "+ this.order);
		return sql.toString();
	}
	//TODO 继续完善SQL生成相关函数，形成类似YII中的criteria
}
