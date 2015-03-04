package com.cnvp.paladin.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cnvp.paladin.kit.ConfigKit;
import com.cnvp.paladin.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

@SuppressWarnings({ "serial", "rawtypes" })
public class BaseModel<M extends BaseModel> extends Model<M>{
	private Integer isNew = 1;
	protected void setIsNew(boolean flg){
		if(flg)
			this.isNew = 1;
		else
			this.isNew = 0;
	}
	public boolean isNew(){
		if(this.isNew == 1){
			return true;
		}else if(this.isNew == 0){
			return false;
		}else
			return false;
	}
	public List<M> find(String sql, Object... paras) {
		List<M> r = super.find(sql, paras);
		for (M m : r) {
			m.setIsNew(false);
		}
		return r;
	}
	public Map<String, Object> getAttrs() {
		return super.getAttrs();
	}
	public Table getTable(){
		return TableMapping.me().getTable(this.getClass());
	}
	public String getTableName(){
		return getTable().getName();
	}
	public String getDbPerfix(){
		return ConfigKit.getDbPrefix();
	}
	public boolean exists(String whereSql,Object... args){
		return exists("id",whereSql,args);
	}
	public boolean exists(String pk,String whereSql,Object... args){
		Long count = Db.queryLong("select count("+ pk+") from "+getTableName() +" where "+whereSql,args);
		if (count==0)
			return false;
		else
			return true;
	}
	public List<M> findAll(){
		return find("select * from "+getTableName());
	}
	//TODO findAll 待完善暂时无法正常非使用
	public List<M> findAll(MysqlBuilder sqlBuilder){
		sqlBuilder.tableName = getTableName();
		if (sqlBuilder.params.size()==0) 
			return find(sqlBuilder.buildSql());
		else			
			return find(sqlBuilder.buildSql(),sqlBuilder.params);
	}
	//TODO deleteAll 待完善暂时无法正常非使用
	public Integer deleteAll(){		
		return Db.update("delete from "+ getTableName());
	}
	public Integer deleteAll(String sqlWhere){		
		return Db.update("delete from "+ getTableName()+" where " + sqlWhere);
	}
	public Integer deleteAll(String sqlWhere, Object... paras){		
		return Db.update("delete from "+ getTableName()+" where " + sqlWhere,paras);
	}
	public List<M> where(String sqlWhere){
		String sql = "select * from "+getTableName();
		if (!StringKit.isBlank(sqlWhere)) 
			sql+=" where "+sqlWhere;
		return find(sql);
	}
	public List<M> where(String sqlWhere, Object... paras){
		return find("select * from "+getTableName()+" where "+sqlWhere,paras);
	}
	/**
	 * 根据当前模型字段属性检索数据库
	 * @return 
	 */
	public List<M> findByModel(String order){
		Map<String, Object> attrs = getAttrs();
		ArrayList<Object> vals = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from "+getTableName()+" where 1=1");
		for(Map.Entry<String, Object> attr: attrs.entrySet()) {
			 sql.append(" and "+attr.getKey()+"=?");
			 vals.add(attr.getValue());
		}
		sql.append(" order by "+order);
		return find(sql.toString(), vals.toArray());		
	}
	public List<M> findByModel(){
		return findByModel("id asc");
	}

	public M findFirstByModel(){
		return findFirstByModel("id asc");
	}
	public M findFirstByModel(String order){
		Map<String, Object> attrs = getAttrs();
		ArrayList<Object> vals = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from "+getTableName()+" where 1=1");
		for(Map.Entry<String, Object> attr: attrs.entrySet()) {
			 sql.append(" and "+attr.getKey()+"=?");
			 vals.add(attr.getValue());
		}
		sql.append(" order by "+order);
		List<M> result = find(sql.toString(), vals.toArray());		
		return result.size() > 0 ? result.get(0) : null;
	}
	public Page<M> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from "+getTableName()+" order by id asc");
	}
//	List<M> result = find(sql, NULL_PARA_ARRAY);
//	return result.size() > 0 ? result.get(0) : null;
//	public void injectByPost(String modelName, HttpServletRequest request, boolean skipConvertError) {
//		Table table = TableMapping.me().getTable(this.getClass());
//		
//		String modelNameAndDot = modelName + ".";
//		
//		Map<String, String[]> parasMap = request.getParameterMap();
//		for (Entry<String, String[]> e : parasMap.entrySet()) {
//			String paraKey = e.getKey();
//			if (paraKey.startsWith(modelNameAndDot)) {
//				String paraName = paraKey.substring(modelNameAndDot.length());
//				Class colType = table.getColumnType(paraName);
//				if (colType == null)
//					throw new ActiveRecordException("The model attribute " + paraKey + " is not exists.");
//				String[] paraValue = e.getValue();
//				try {
//					// Object value = Converter.convert(colType, paraValue != null ? paraValue[0] : null);
//					Object value = paraValue[0] != null ? TypeConverter.convert(colType, paraValue[0]) : null;
//					this.set(paraName, value);
//				} catch (Exception ex) {
//					if (skipConvertError == false)
//						throw new RuntimeException("Can not convert parameter: " + modelNameAndDot + paraName, ex);
//				}
//			}
//		}
//	}
}
