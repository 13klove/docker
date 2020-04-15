package com.gdev.boot.domain.member;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.gdev.boot.config.mybatis.GdevGenericRepository;

@Repository
public class MemberRepository extends GdevGenericRepository<MemberVo, String>{

	public Optional<MemberVo> finduUserById(String id, String username) {
		return Optional.ofNullable(sqlSession.selectOne(id, username));
	}
	
	public Optional<MemberVo> findMemberById(String id, MemberVo param){
		return Optional.ofNullable(selectOne(id, param));
	}
	
}