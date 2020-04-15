package com.gdev.boot.config.security;

public enum RoleAttribute {

	ROLE_DEV("개발자"),
	ROLE_USER("사용자"),
	ROLE_READER("스터디장"),
	ANONYMOUS("미인증자"),
	ALL("모든권한");
	
	private String desc;
	
	private RoleAttribute(String desc) {
		this.desc = desc;
	}
	
}
