package edu.uncc.ssdi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PERM_ID")
	private long permId;
	
	@Column(name = "DIGITAL_ID")
	private String id;
	
	@Column(name = "ACCESS_BY")
	private long accessByUserId;
	
	@Column(name = "STATUS")
	private int status;

	
	public Permission() {
	
	}
	
	
	
	public Permission(String id, long accessByUserId, int status) {
		super();
		this.id = id;
		this.accessByUserId = accessByUserId;
		this.status = status;
	}

	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public long getAccessByUserId() {
		return accessByUserId;
	}

	public void setAccessByUserId(long accessByUserId) {
		this.accessByUserId = accessByUserId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	
	
	
	
}
