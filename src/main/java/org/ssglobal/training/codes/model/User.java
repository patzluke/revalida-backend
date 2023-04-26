package org.ssglobal.training.codes.model;

import java.time.LocalDate;

public class User {

	private Integer employeeId;
	private String email;
	private String mobileNumber;
	private String password;
	private String userType;
	private String firstName;
	private String middleName;
	private String lastName;
	private Integer departmentId;
	private LocalDate birthDate;
	private String gender;
	private Integer positionId;

	// for inner join foreign_key values
	// for table departments
	private String departmentName;

	// for table positions
	private String positionName;

	public User() {
	}

	public User(Integer employeeId, String email, String mobileNumber, String password, String userType,
			String firstName, String middleName, String lastName, Integer department, LocalDate birthDate,
			String gender, Integer positionId) {
		super();
		this.employeeId = employeeId;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.userType = userType;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.departmentId = department;
		this.birthDate = birthDate;
		this.gender = gender;
		this.positionId = positionId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
}
