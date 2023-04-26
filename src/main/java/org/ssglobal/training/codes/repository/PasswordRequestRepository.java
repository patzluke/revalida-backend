package org.ssglobal.training.codes.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.ssglobal.training.codes.model.PasswordRequest;

public interface PasswordRequestRepository {

	@Select("""
			select p.id, p.status, p.emp_id, concat(u.first_name, ' ', u.last_name) as employee_name, u.email
			from password_requests p inner join users u on p.emp_id = u.employee_id;
			""")
	@Results(value = { 
			@Result(property = "id", column = "id"), 
			@Result(property = "status", column = "status"),
			@Result(property = "empId", column = "emp_id"),
			@Result(property = "employeeName", column = "employee_name"),
			@Result(property = "email", column = "email"), })
	public List<PasswordRequest> getAllPasswordRequestsJoinByUsers();

	@Insert(value = """
			insert into password_requests(emp_id) values(#{empId})
			""")
	public boolean insertPasswordRequest();
}
