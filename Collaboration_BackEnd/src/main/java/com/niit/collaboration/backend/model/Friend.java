package com.niit.collaboration.backend.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "C_FRIEND")
@Component
public class Friend extends BaseDomain {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "from_id")
	private String fromId;

	@Column(name = "to_id")
	private String toId;

	private char status;
	
	@Column(name = "is_online")
	private char isOnline;
	
	@Column(name = "LAST_SEEN_TIME")
	private Date lastseentime;
	

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}// 'A' , 'D', 'P'
	public char getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(char isOnline) {
		this.isOnline = isOnline;
	}

	public Date getLastseentime() {
		return lastseentime;
	}

	public void setLastseentime(Date lastseentime) {
		this.lastseentime = lastseentime;
	}

}