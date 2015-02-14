package com.cnvp.paladin.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.TableMapping;

@SuppressWarnings({ "serial", "rawtypes" })
public class BaseModel<M extends BaseModel> extends Model<M>{
	public boolean isNew(){
		if(getAttrs().size()==0)
			return true;
		else
			return false;		
	}
	public Map<String, Object> getAttrs() {
		return super.getAttrs();
	}
	public String getTableName(){
		return TableMapping.me().getTable(this.getClass()).getName();
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
