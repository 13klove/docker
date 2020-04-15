package com.gdev.boot.config.mybatis;

import java.util.List;

public interface GdevCrudRepository<T, E> {

	public int insert(String id, T param);
	public int update(String id, T param);
	public int delete(String id, T param);
	public int deleteKey(String id, E key);
	public T selectOne(String id, T param);
	public T selectOneKey(String id, E key);
	public List<T> selectSrch(String id, T param);
	public List<T> selectAll(String id);
	
}
