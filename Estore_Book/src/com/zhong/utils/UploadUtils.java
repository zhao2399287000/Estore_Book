package com.zhong.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtils {
	
	public static String upload(MultipartFile file) {
		//访问路径
		String fwPath = "http://localhost:8080/book/pic/";
		String filename = "";
		String path="e:/upload/";
		try {
				File filepath = new File(path);
				if(!filepath.exists()) {
					filepath.mkdirs();
				}
				//获取上传文件的真实名称
				filename = file.getOriginalFilename();
				//获取上传文件的后缀名
				filename = filename.substring(filename.lastIndexOf('.'));
				System.out.print(filename);
				
				//上传文件的名称
				filename = UUIDUtils.getUUID()+filename;
				
				path = path + filename;
				file.transferTo(new File(path));	
		}catch(IllegalStateException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return fwPath + filename;
	}
}
