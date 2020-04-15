package com.gdev.boot.config.security;

public enum Menu {

	//1depth
	READER("/reader", RoleAttribute.ROLE_READER),
	USER("/user", RoleAttribute.ROLE_USER, RoleAttribute.ROLE_READER),
	HOME("/home", RoleAttribute.ALL),
	REGIST("/regist", RoleAttribute.ANONYMOUS);
	
	private String url;
	private RoleAttribute[] role;
	
	private Menu(String url, RoleAttribute ...role) {
		this.url = url;
		this.role = role;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public RoleAttribute[] getRole() {
		return role;
	}

	public void setRole(RoleAttribute[] role) {
		this.role = role;
	}
	
	public String[] getMappingRole() {
		String[] returnRole = new String[role.length];
		for(int i = 0; i < role.length; i++) {
			returnRole[i] = role[i].toString();
		}
		return returnRole;
	}

}
