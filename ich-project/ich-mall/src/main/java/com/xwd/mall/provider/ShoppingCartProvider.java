package com.xwd.mall.provider;

import com.xwd.mall.dto.ShoppingCart;

/**
 * 购物车缓存接口
 * @author admin
 *
 */
public interface ShoppingCartProvider {
	
	/**
	 * 保存购物车到缓存
	 * id不需要处理
	 * @param entity
	 */
	public String save(ShoppingCart entity);
	
	/**
	 * 更新购物车
	 * @param entity
	 */
	public void update(ShoppingCart entity);
	
	/**
	 * 登录用户获得购物车
	 * @return
	 */
	public ShoppingCart get();
	
	/**
	 * 未登录用户或者根据id获得购物车
	 * @param id
	 * @return
	 */
	public ShoppingCart get(String id);
	
	/**
	 * 登录用户清除购物车
	 */
	public void delete();
	
	/**
	 * 未登录用户清除购物车
	 * 一般是cook里面的值
	 * @param id
	 */
	public void delete(String id);
}
