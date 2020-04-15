package com.gdev.boot.config.security;

import java.util.List;

import com.google.common.collect.Lists;

public enum RoleGroup {

	GROUP_DEV(RoleAttribute.ROLE_DEV, RoleAttribute.ROLE_USER, RoleAttribute.ROLE_READER),
	GROUP_USER(RoleAttribute.ROLE_USER),
	GROUP_READER(RoleAttribute.ROLE_READER);
	
	private List<RoleAttribute> role;

	private RoleGroup(RoleAttribute ...roleAttribute) {
		this.role = Lists.newArrayList(roleAttribute);
	}

	public List<RoleAttribute> getRole() {
		return role;
	}

	public void setRole(List<RoleAttribute> role) {
		this.role = role;
	}
	
}
