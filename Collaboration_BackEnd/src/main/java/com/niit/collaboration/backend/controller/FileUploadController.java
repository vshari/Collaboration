package com.niit.collaboration.backend.controller;

import java.io.File;

import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.collaboration.backend.dao.FileUploadDAO;
import com.niit.collaboration.backend.model.UploadFile;
import com.niit.collaboration.backend.model.User;

@RestController
public class FileUploadController {
	@Autowired
	private FileUploadDAO fileUploadDAO;
	
	@RequestMapping(value = "/doUpload", method = RequestMethod.POST)
	public String handleFileUpload(HttpServletRequest request, HttpSession session,
			@RequestParam CommonsMultipartFile fileUpload) throws Exception {

		User user = (User) session.getAttribute("user");
		if (user == null)
			throw new RuntimeException("Not logged in");
		System.out.println("USER is " + user.getUsername());

		if (fileUpload != null) {
			CommonsMultipartFile aFile = fileUpload;

			System.out.println("Saving file: " + aFile.getOriginalFilename());

			UploadFile uploadFile = new UploadFile();
			uploadFile.setFileName(aFile.getOriginalFilename());
			uploadFile.setData(aFile.getBytes());// image
			uploadFile.setUsername(user.getUsername());// login details
			fileUploadDAO.save(uploadFile);

			UploadFile getUploadFile = fileUploadDAO.getFile(user.getUsername());
			String name = getUploadFile.getFileName();
			System.out.println(getUploadFile.getData());
			byte[] imagefiles = getUploadFile.getData(); // image

			try {
				String path = "E:/Collaboration_Project2/Collaboration_BackEnd/src/main/webapp/WEB-INF/resources/images/"
						+ user.getUsername();
				File file = new File(path);
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(imagefiles);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "Successfully uploaded the Profile Picture";
	}

}
		
		
		
		
		
		
		
		
		
