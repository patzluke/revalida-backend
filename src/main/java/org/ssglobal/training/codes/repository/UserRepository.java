package org.ssglobal.training.codes.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ssglobal.training.codes.model.User;

public interface UserRepository {
	
	@Select(value = """
				select u.employee_id, u.email, u.mobile_number, u.user_type, u.first_name,
				  	   u.middle_name, u.last_name, d.department_name, u.birth_date, u.gender,
				  	   p.position_name, u.dept_id, u.position_id from users u inner join departments d on u.dept_id = d.department_id
				  	    	   						inner join positions p on u.position_id = p.position_id
			""")
	@Results(value = {
			@Result(property = "employeeId", column = "employee_id"),
			@Result(property = "email", column = "email"),
			@Result(property = "mobileNumber", column = "mobile_number"),
			@Result(property = "userType", column = "user_type"),
			@Result(property = "firstName", column = "first_name"),
			@Result(property = "middleName", column = "middle_name"),
			@Result(property = "lastName", column = "last_name"),
			@Result(property = "birthDate", column = "birth_date"),
			@Result(property = "gender", column = "gender"),
			@Result(property = "departmentName", column = "department_name", javaType = String.class),
			@Result(property = "positionName", column = "position_name", javaType = String.class),
	})
	public List<User> selectUsersInnerJoinDepartmentAndPosition();
	
	@Select(value = "select * from users u")
	@Results(value = {
			@Result(property = "employeeId", column = "employee_id"),
			@Result(property = "email", column = "email"),
			@Result(property = "mobileNumber", column = "mobile_number"),
			@Result(property = "userType", column = "user_type"),
			@Result(property = "firstName", column = "first_name"),
			@Result(property = "middleName", column = "middle_name"),
			@Result(property = "lastName", column = "last_name"),
			@Result(property = "departmentId", column = "dept_id"),
			@Result(property = "birthDate", column = "birth_date"),
			@Result(property = "gender", column = "gender"),
			@Result(property = "positionId", column = "position_id"),
	})
	public List<User> selectAllUsers();
	
	
	@Select(value = "select * from users where employee_id = #{employeeId}")
	@Results(value = {
			@Result(property = "employeeId", column = "employee_id"),
			@Result(property = "email", column = "email"),
			@Result(property = "mobileNumber", column = "mobile_number"),
			@Result(property = "userType", column = "user_type"),
			@Result(property = "firstName", column = "first_name"),
			@Result(property = "middleName", column = "middle_name"),
			@Result(property = "lastName", column = "last_name"),
			@Result(property = "departmentId", column = "dept_id"),
			@Result(property = "birthDate", column = "birth_date"),
			@Result(property = "gender", column = "gender"),
			@Result(property = "positionId", column = "position_id"),
	})
	public User getUserById(Integer employeeId);
	
	@Select(value = "select employee_id, email, user_type from users where email = #{email} and password = #{password}")
	@Results(value = {
			@Result(property = "employeeId", column = "employee_id"),
			@Result(property = "email", column = "email"),
			@Result(property = "userType", column = "user_type")
	})
	public User searchUserByEmailAndPass(Map<String, String> parameter);
	
	@Delete(value = "delete from users where employee_id = #{employeeId}")
	public boolean deleteUserById(Integer employeeId);
	
	@Update(value = """
			update users set email = #{email}, mobile_number = #{mobileNumber}, user_type = #{userType}, 
							 first_name = #{firstName}, middle_name = #{middleName}, last_name = #{lastName},
							 dept_id = #{deptId}, birth_date = #{birthDate}, gender = #{gender},
							 position_id = #{positionId} where employee_id = #{employeeId}
			""")
	public boolean updateUser(Map<String, Object> parameters);
	
	@Insert(value = """
			insert into users(email, mobile_number, password, user_type, first_name, middle_name, last_name, dept_id, birth_date, gender, position_id)
			values(#{email}, #{mobileNumber}, #{password}, #{userType}, #{firstName}, #{middleName}, #{lastName}, #{departmentId}, #{birthDate}, #{gender}, #{positionId})
			""")
	public boolean inserUser();
}
