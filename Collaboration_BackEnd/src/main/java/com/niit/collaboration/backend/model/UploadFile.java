package com.niit.collaboration.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "C_UPLOADFILE")
public class UploadFile {

	@Id
	@GeneratedValue
	@Column(name = "FILE_ID")
	private int id;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Lob // to store some image files, Large Object
	@Column(name = "FILE_DATA")
	private byte[] data;

	private String username;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}

