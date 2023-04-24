package org.ssglobal.training.codes.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ssglobal.training.codes.model.Department;

public interface DeparmentRepository {
	
	@Select(value = "select * from departments d where department_id > 0")
	@Results(value = {
			@Result(property = "departmentId", column = "department_id"),
			@Result(property = "departmentName", column = "department_name"),
	})
	public List<Department> selectAllDep();
	
	@Select(value = "select * from departments where department_id = #{departmentId}")
	@Results(value = {
			@Result(property = "departmentId", column = "department_id"),
			@Result(property = "departmentName", column = "department_name"),
	})
	public Department getDepartmentbyId(Integer departmentId);
	
	@Delete(value = "delete from departments where department_id = #{departmentId}")
	public boolean deleteDepById(Integer depId);
	
	@Update(value = """
			update departments set department_name = #{departmentName} where department_id = #{departmentId}
			""")
	public boolean updateDepartment(Map<String, Object> parameters);
	
	@Insert(value = """
			insert into departments(department_name) values (#{departmentName})
			""")
	public boolean insertDepartment();
	
}
