package org.ssglobal.training.codes.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.ssglobal.training.codes.MySecretKey;
import org.ssglobal.training.codes.model.User;

public interface UserRepository {

	@Select(value = """
				select u.employee_id, u.email, u.mobile_number, u.user_type, u.first_name,
				  	   u.middle_name, u.last_name, d.department_name, u.birth_date, u.gender,
				  	   p.position_name, u.dept_id, u.position_id from users u
				  	   inner join departments d on u.dept_id = d.department_id
				  	   inner join positions p on u.position_id = p.position_id
			""")
	@Results(value = { @Result(property = "employeeId", column = "employee_id"),
			@Result(property = "email", column = "email"), @Result(property = "mobileNumber", column = "mobile_number"),
			@Result(property = "userType", column = "user_type"),
			@Result(property = "firstName", column = "first_name"),
			@Result(property = "middleName", column = "middle_name"),
			@Result(property = "lastName", column = "last_name"),
			@Result(property = "birthDate", column = "birth_date"), @Result(property = "gender", column = "gender"),
			@Result(property = "departmentName", column = "department_name", javaType = String.class),
			@Result(property = "positionName", column = "position_name", javaType = String.class), })
	public List<User> selectUsersInnerJoinDepartmentAndPosition();

	@Select(value = "select * from users u")
	@Results(value = { @Result(property = "employeeId", column = "employee_id"),
			@Result(property = "email", column = "email"), @Result(property = "mobileNumber", column = "mobile_number"),
			@Result(property = "userType", column = "user_type"),
			@Result(property = "firstName", column = "first_name"),
			@Result(property = "middleName", column = "middle_name"),
			@Result(property = "lastName", column = "last_name"),
			@Result(property = "departmentId", column = "dept_id"),
			@Result(property = "birthDate", column = "birth_date"), @Result(property = "gender", column = "gender"),
			@Result(property = "positionId", column = "position_id"), })
	public List<User> selectAllUsers();

	@Select(value = """
				select u.employee_id, u.email, u.mobile_number, u.user_type, u.first_name,
				  	   u.middle_name, u.last_name, d.department_name, u.birth_date, u.gender,
				  	   p.position_name, u.dept_id, u.position_id from users u
				  	   inner join departments d on u.dept_id = d.department_id
				  	   inner join positions p on u.position_id = p.position_id
				  	   where cast(employee_id as text) like concat('%', #{search}, '%') 
				  	   or lower(first_name) like concat('%', lower(#{search}), '%')
				  	   or lower(middle_name) like concat('%', lower(#{search}), '%')
				  	   or lower(last_name) like concat('%', lower(#{search}), '%')
			""")
	@Results(value = { @Result(property = "employeeId", column = "employee_id"),
			@Result(property = "email", column = "email"), @Result(property = "mobileNumber", column = "mobile_number"),
			@Result(property = "userType", column = "user_type"),
			@Result(property = "firstName", column = "first_name"),
			@Result(property = "middleName", column = "middle_name"),
			@Result(property = "lastName", column = "last_name"),
			@Result(property = "birthDate", column = "birth_date"), @Result(property = "gender", column = "gender"),
			@Result(property = "departmentName", column = "department_name", javaType = String.class),
			@Result(property = "positionName", column = "position_name", javaType = String.class), })
	public List<User> searchUsersInnerJoinDepartmentAndPositionUsingLike(Map<String, String> parameter);

	@Select(value = """
			select u.employee_id, u.email, u.mobile_number, u.user_type, u.first_name,
			 	   u.middle_name, u.last_name, d.department_name, u.birth_date, u.gender,
			 	   p.position_name, u.dept_id, u.position_id from users u
			 	   inner join departments d on u.dept_id = d.department_id
			  	   inner join positions p on u.position_id = p.position_id
			  	   where employee_id = #{employeeId}
			""")
	@Results(value = { @Result(property = "employeeId", column = "employee_id"),
			@Result(property = "email", column = "email"), @Result(property = "mobileNumber", column = "mobile_number"),
			@Result(property = "userType", column = "user_type"),
			@Result(property = "firstName", column = "first_name"),
			@Result(property = "middleName", column = "middle_name"),
			@Result(property = "lastName", column = "last_name"),
			@Result(property = "departmentId", column = "dept_id"),
			@Result(property = "birthDate", column = "birth_date"), @Result(property = "gender", column = "gender"),
			@Result(property = "positionId", column = "position_id"),
			@Result(property = "departmentName", column = "department_name", javaType = String.class),
			@Result(property = "positionName", column = "position_name", javaType = String.class), })
	public User getUserById(Integer employeeId);

	@SelectProvider(type = MyPostgresQueriesWithSecretKey.class, method = "searchUserByEmailAndPassQuery")
	@Results(value = { @Result(property = "employeeId", column = "employee_id"),
			@Result(property = "email", column = "email"), @Result(property = "userType", column = "user_type") })
	public User searchUserByEmailAndPass(Map<String, String> parameter);

	@SelectProvider(type = MyPostgresQueriesWithSecretKey.class, method = "searchPasswordByIdQuery")
	@Results(value = { @Result(property = "employeeId", column = "employee_id"),
			@Result(property = "password", column = "password"), })
	public User searchPasswordById(Integer employeeId);

	@Delete(value = "delete from users where employee_id = #{employeeId}")
	public boolean deleteUserById(Integer employeeId);

	@Update(value = """
			update users set email = #{email}, mobile_number = #{mobileNumber}, user_type = initcap(#{userType}),
							 first_name = initcap(#{firstName}), middle_name = initcap(#{middleName}), last_name = initcap(#{lastName}),
							 dept_id = #{deptId}, birth_date = #{birthDate}, gender = initcap(#{gender}),
							 position_id = #{positionId} where employee_id = #{employeeId}
			""")
	public boolean updateUser(Map<String, Object> parameters);

	@UpdateProvider(type = MyPostgresQueriesWithSecretKey.class, method = "changePasswordQuery")
	public boolean changePassword(Map<String, Object> parameters);

	@InsertProvider(type = MyPostgresQueriesWithSecretKey.class, method = "insertUserQuery")
	public boolean inserUser();

	public static class MyPostgresQueriesWithSecretKey {
		private static final MySecretKey secretKey = new MySecretKey();

		public static final String searchUserByEmailAndPassQuery() {
			return """
					select employee_id, email, user_type from users
					where email = #{email} and pgp_sym_decrypt(password::bytea, '%s') = #{password}
					""".formatted(secretKey.getSecretKey());
		}

		public static final String searchPasswordByIdQuery() {
			return """
					select employee_id, pgp_sym_decrypt(password::bytea, '%s') as password
					from users where employee_id = #{employeeId}
					""".formatted(secretKey.getSecretKey());
		}

		public static final String changePasswordQuery() {
			return """
					update users set password = pgp_sym_encrypt(#{password}, '%s')
					where email = #{email}
					""".formatted(secretKey.getSecretKey());
		}

		public static final String insertUserQuery() {
			return """
					insert into users(email, mobile_number, password, user_type, first_name, middle_name, last_name, dept_id, birth_date, gender, position_id)
					values(#{email}, #{mobileNumber}, pgp_sym_encrypt(#{password}, '%s'), initcap(#{userType}), initcap(#{firstName}), initcap(#{middleName}), initcap(#{lastName}), #{departmentId}, #{birthDate}, initcap(#{gender}), #{positionId})
					"""
					.formatted(secretKey.getSecretKey());
		}
	}
}
