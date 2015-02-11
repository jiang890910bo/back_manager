package com.cnvp.paladin.utils.beetl.fn;

import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * FileSize(123123123)
 * FileSize(123123123,"B")
 * FileSize(123123123,"KB")
 * FileSize(123123123,"MB")
 * FileSize(123123123,"GB")
 */
public class FileSize implements Function{
	
	public Object call(Object[] paras, Context ctx){
		if (paras.length>2) {
			throw new RuntimeException("最多只能传递2个参数");
		}
		Object o = paras[0];
		Long size = Long.parseLong(o.toString());
		if (paras.length==2) {
			String unitInput = paras[0].toString().toUpperCase();
			if ("KB".equals(unitInput)) {
				size = size*1024;
			}else if("MB".equals(unitInput)){
				size = size*1024*1024;
			}else if("GB".equals(unitInput)){
				size = size*1024*1024*1024;
			}			
		}
		long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
	}
}
