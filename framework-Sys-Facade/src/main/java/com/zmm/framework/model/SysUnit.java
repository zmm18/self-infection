package com.zmm.framework.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import top.ibase4j.core.base.BaseModel;

/**
 * @Name SysUnit
 * @Author 900045
 * @Created by 2019/7/5 0005
 */
@TableName("sys_unit")
public class SysUnit extends BaseModel {

	private String unitName;
	@TableField("principal_")
	private String principal;
	@TableField("phone_")
	private String phone;
	@TableField("address_")
	private String address;
	@TableField("sort_")
	private Integer sort;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
