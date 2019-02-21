package com.xwd.product.provider;

import com.xwd.product.model.Product;

public interface ProductProvider {

	public void save(Product entity);
	
	public void update(Product entity);
	
	public void delete(Long code);
	
	public Product get(Long code);
}
