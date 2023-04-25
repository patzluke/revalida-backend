package org.ssglobal.training.codes.model;

public class PasswordRequest {
	private Integer id;
	private Integer empId;
	private String status;

	// for inner join foreign_key values
	// for table users
	private String employeeName;

	public PasswordRequest() {
	}

	public PasswordRequest(Integer id, Integer empId, String status) {
		super();
		this.id = id;
		this.empId = empId;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}	
}
