package com.niit.collaboration.backend.dao;

import com.niit.collaboration.backend.model.UploadFile;

public interface FileUploadDAO 
{

	void save(UploadFile uploadFile);

	UploadFile getFile(String username);
}