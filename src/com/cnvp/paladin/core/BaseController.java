package com.cnvp.paladin.core;

import com.jfinal.core.Controller;

/**
 * 用途：controller基类，所有controller必须继承 说明：
 */
public class BaseController extends Controller {

	protected String getControllerKey() {
		return this.getAttr("ControllerKey");
	}
	protected boolean isPost(){		
		return "post".equals(getRequest().getMethod().toLowerCase());
	}
	protected void goBack() {
		goBack(-1);
	}
	protected void goBack(int step) {
		renderJS("history.go(" + step + ")");
	}
	protected void alertAndGoback(String msg) {
		alertAndGoback(msg,-1);
	}
	protected void alertAndGoback(String msg,int step) {
		renderJS("alert('"+msg+"');history.go(" + step + ")");
	}
	protected void renderJS(String jsContent){
		renderHtml("<script type=\"text/javascript\">"+jsContent+"</script>");
	}
}
