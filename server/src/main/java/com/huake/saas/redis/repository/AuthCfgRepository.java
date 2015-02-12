package com.huake.saas.redis.repository;

import com.huake.saas.auth.entity.AuthCfg;

/**
 * appportal - 租户-uid - 某项配置 - 配置项
 * @author zjy
 *
 */
public interface AuthCfgRepository <T extends AuthCfg>{
	/**
	 * 添加配置  添加时 authCfg 对象里面的各项配置  为各项配置的 初始已用数额 即都为0
	 */
	public void putAuthCfg(String uid);
	
	/**
	 * 删除配置
	 */
	public void removeAuthCfg(String uid);
	
	/**
	 * 更新配置  更新时 authCfg 对象里面的各项配置  为各项配置的 已用数额
	 */
	public void updateAuthCfg(T authCfg);
	
	/**
	 * 获得配置
	 * @param uid
	 * @return
	 */
	public T getAuthCfg(String uid);
	
	/**
	 * 获取所有租户当前配置的的使用 情况  并put到redis中
	 */
	public void putCurrentAuthCfg();
	
	/**
	 * 清空当前配置的使用情况  具体配置项清空   根据业务需求而定
	 */
	public void cleanAuthCfg(String uid);
}
