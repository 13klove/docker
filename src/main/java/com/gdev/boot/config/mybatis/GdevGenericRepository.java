package com.gdev.boot.config.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GdevGenericRepository<T, E> implements GdevCrudRepository<T,E>{

	@Autowired
	public SqlSession sqlSession;

	@Override
	public int insert(String id, T param) {
		return sqlSession.insert(id, param);
	}

	@Override
	public int update(String id, T param) {
		return sqlSession.update(id, param);
	}

	@Override
	public int delete(String id, T param) {
		return sqlSession.delete(id, param);
	}

	@Override
	public int deleteKey(String id, E key) {
		return sqlSession.delete(id, key);
	}

	@Override
	public T selectOne(String id, T param) {
		return sqlSession.selectOne(id, param);
	}

	@Override
	public T selectOneKey(String id, E key) {
		return sqlSession.selectOne(id, key);
	}

	@Override
	public List<T> selectSrch(String id, T param) {
		return sqlSession.selectList(id, param);
	}

	@Override
	public List<T> selectAll(String id) {
		return sqlSession.selectList(id);
	}
	
}
