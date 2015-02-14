package com.cnvp.paladin.core;

import com.jfinal.core.Controller;

/**
 * 用途：controller基类，所有controller必须继承 说明：
 */
public class BaseController extends Controller {
	public void index() {

	}

	public String getControllerKey() {
		return this.getAttr("ControllerKey");
	}
	public boolean isPost(){		
		if("post".equals(getRequest().getMethod().toLowerCase()))
			return true;
		else
			return false;
	}
	public void goBack() {
		goBack(-1);
	}

	public void goBack(int step) {
		renderJS("history.go(" + step + ")");
	}
	public void alertAndGoback(String msg) {
		alertAndGoback(msg,-1);
	}
	public void alertAndGoback(String msg,int step) {
		renderJS("alert('"+msg+"');history.go(" + step + ")");
	}
	public void renderJS(String jsContent){
		renderHtml("<script type=\"text/javascript\">"+jsContent+"</script>");
	}
}
