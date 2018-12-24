package com.zhong.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtils {
	
	public static String upload(MultipartFile file) {
		//����·��
		String fwPath = "http://localhost:8080/book/pic/";
		String filename = "";
		String path="e:/upload/";
		try {
				File filepath = new File(path);
				if(!filepath.exists()) {
					filepath.mkdirs();
				}
				//��ȡ�ϴ��ļ�����ʵ����
				filename = file.getOriginalFilename();
				//��ȡ�ϴ��ļ��ĺ�׺��
				filename = filename.substring(filename.lastIndexOf('.'));
				System.out.print(filename);
				
				//�ϴ��ļ�������
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
