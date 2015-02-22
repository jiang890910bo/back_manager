/**
 * 
 */
package com.cnvp.paladin.plugin.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.cnvp.paladin.model.SysUser;
import com.jfinal.core.JFinal;

public class ShiroDbRealm extends AuthorizingRealm{
 
     
     
    /**
     * 认证回调函数,登录时调用.
     * 提取当前用户角色和权限
     * com.jfaker.framework.security.shiro.ShiroAuthorizingRealm
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        SysUser sysUser = SysUser.dao.set("account", token.getUsername()).findFirstByModel();
//        System.err.println("ShiroDbRealm::AuthenticationInfo");
        if (sysUser != null) {
            return new SimpleAuthenticationInfo(sysUser, sysUser.getStr("password"),sysUser.getStr("account"));
        } else {
            return null;
        }
    }
 
    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	SysUser userInPrincipal = (SysUser) principals.getPrimaryPrincipal();
    	List<SysUser> user = SysUser.dao.where("account = ?",userInPrincipal.getStr("account"));
    	if(user.size()!=1){
    		if (JFinal.me().getConstants().getDevMode())
    			System.err.println("错误：用户不是唯一");
    		return null;
    	}
    	SysUser userInDb = user.get(0);
    	SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    	//TODO 根据用户获取角色=>根据角色获取权限
    	//TODO 如何构建权限数据？
//    	info.addRoles(userInDb.getRoleNameList());
//    	for (String rn : principals.getRealmNames() ) {
//		}
//        String loginName = (String) principals.fromRealm(getName()).iterator().next();
//        User user = User.dao.set("account", loginName).findFirstByModel();
//        if (user != null) {
//            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//            info.addRoles(user.getRoleNameList());
//            for (Role role : user.getRoleList()) {
//                info.addStringPermissions(role.getPermissionNameList());
//            }
//            return info;
//        } else {
//            return null;
//        }
    	return null;
    }
 
    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }
 
    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }
 
}
