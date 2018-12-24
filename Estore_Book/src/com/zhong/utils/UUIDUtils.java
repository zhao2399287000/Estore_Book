package com.zhong.utils;

import java.util.UUID;

public class UUIDUtils {

	
	public static String getUUID() {
		String str = UUID.randomUUID().toString().replace("-", "");
		return str;
	}
}
