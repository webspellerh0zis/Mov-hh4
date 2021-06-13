package qlsl.androiddesign.constant;

public interface WhatConstant extends SoftwareConstant {

	/**
	 * 网络数据成功状态<br/>
	 */
	int WHAT_NET_DATA_SUCCESS = 0x100;

	/**
	 * 网络数据失败状态<br/>
	 */
	int WHAT_NET_DATA_FAIL = 0x101;

	/**
	 * 数据库数据成功状态<br/>
	 */
	int WHAT_DB_DATA_SUCCESS = 0x200;

	/**
	 * 数据库数据失败状态<br/>
	 */
	int WHAT_DB_DATA_FAIL = 0x201;

	/**
	 * 费时数据成功状态<br/>
	 */
	int WHAT_OTHER_DATA_SUCCESS = 0x300;

	/**
	 * 费时数据失败状态<br/>
	 */
	int WHAT_OTHER_DATA_FAIL = 0x301;

	/**
	 * 数据例外状态<br/>
	 */
	int WHAT_EXCEPITON = 0x400;

	/**
	 * 数据取消状态<br/>
	 */
	int WHAT_CANCEL = 0x500;
}
