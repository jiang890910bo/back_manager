package com.cnvp.paladin.plugin;

import java.util.List;

import javax.sql.DataSource;

import com.cnvp.paladin.core.BaseModel;
import com.cnvp.paladin.kit.ClassKit;
import com.cnvp.paladin.kit.ConfigKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
/***
 * 自动绑定model与数据库表
 */
public class AutoTableBindPlugin extends ActiveRecordPlugin {
	private TableNameStyle tableNameStyle;
	public AutoTableBindPlugin(DataSource dataSource) {
		super(dataSource);
	}

	public AutoTableBindPlugin(IDataSourceProvider dataSourceProvider, TableNameStyle tableNameStyle) {
		super(dataSourceProvider);
		this.tableNameStyle = tableNameStyle;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean start() {
		try {
			List<Class> modelClasses = ClassKit.findClasses(BaseModel.class);
			TableBind tb = null;
			for (Class modelClass : modelClasses) {
				tb = (TableBind) modelClass.getAnnotation(TableBind.class);
				if (tb == null) {
					this.addMapping(tableName(modelClass), modelClass);
				} else {
					if(StrKit.notBlank(tb.name())){
						if (StrKit.notBlank(tb.pk())) {
							this.addMapping(tb.name(), tb.pk(), modelClass);
						} else {
							this.addMapping(tb.name(), modelClass);
						}
					}else{
						if (StrKit.notBlank(tb.pk())) {
							this.addMapping(tableName(modelClass), tb.pk(), modelClass);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
//			throw new RuntimeException(e);
		}
		return super.start();
	}

	@Override
	public boolean stop() {
		return super.stop();
	}
	
//	根据类名生成Table名
	private String tableName(Class<?> clazz) {
		String tableName = clazz.getSimpleName();
		if (tableNameStyle == TableNameStyle.UP) {
			tableName = tableName.toUpperCase();
		} else if (tableNameStyle == TableNameStyle.LOWER) {
			tableName = tableName.toLowerCase();
		} else if (tableNameStyle == TableNameStyle.LOWER_UNDERLINE) {
			tableName = tableName.replaceAll("[A-Z]", "_$0");
			tableName = tableName.substring(1,tableName.length()).toLowerCase();
		}else {
			tableName = StrKit.firstCharToLowerCase(tableName);
		}
		String tablePrefix =ConfigKit.get("jdbc.dbPrefix");
		if (!StrKit.isBlank(tablePrefix)) 
			tableName = tablePrefix + tableName;
		System.out.println("DATABASE_TABLE_MAPPING---->"+clazz.getName()+"=>"+tableName);
		return tableName;
	}
}
