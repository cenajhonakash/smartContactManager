package com.SmartManager.Utility;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	public void uploadFile(MultipartFile file) throws IllegalStateException, IOException
	{
		file.transferTo(new File("D:\\JavaProjects_Eclipse\\STSprojects\\SmartContactManager\\src\\main\\resources\\static\\IMG\\UploadedIMG\\"+file.getOriginalFilename()));
	}
}
