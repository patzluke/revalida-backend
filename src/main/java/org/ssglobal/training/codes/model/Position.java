package org.ssglobal.training.codes.model;

public class Position {
	private Integer positionId;
	private Integer departmentId;
	private String positionName;

	public Position() {
	}	
	
	public Position(Integer positionId, Integer departmentId, String positionName) {
		super();
		this.positionId = positionId;
		this.departmentId = departmentId;
		this.positionName = positionName;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
}
