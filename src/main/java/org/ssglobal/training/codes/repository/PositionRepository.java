package org.ssglobal.training.codes.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ssglobal.training.codes.model.Position;

public interface PositionRepository {
	
	@Select(value = "select * from positions p where position_id > 0")
	@Results(value = {
			@Result(property = "positionId", column = "position_id"),
			@Result(property = "departmentId", column = "dept_id"),
			@Result(property = "positionName", column = "position_name"),
	})
	public List<Position> selectAllpos();
	
	@Select(value = """
			select p.position_id, d.department_name, p.position_name from positions p
			inner join departments d on p.dept_id = d.department_id
			where position_id > 0;
			""")
	@Results(value = {
			@Result(property = "positionId", column = "position_id"),
			@Result(property = "departmentName", column = "department_name", javaType = String.class),
			@Result(property = "positionName", column = "position_name"),
	})
	public List<Position> selectAllPosInnerJoinDepartment();
	
	@Select(value = """
			select p.position_id, p.position_name, p.dept_id, d.department_name from positions p
			inner join departments d on p.dept_id = d.department_id
			where position_id = #{positionId} and position_id > 0;
			""")
	@Results(value = {
			@Result(property = "positionId", column = "position_id"),
			@Result(property = "departmentId", column = "dept_id"),
			@Result(property = "departmentName", column = "department_name", javaType = String.class),
			@Result(property = "positionName", column = "position_name"),
	})
	public Position getPosbyId(Integer positionId);
	
	@Select(value = """
			select p.position_id, p.position_name, p.dept_id, d.department_name from positions p
			inner join departments d on p.dept_id = d.department_id 
			where dept_id = #{departmentId} and position_id > 0
			""")
	@Results(value = {
			@Result(property = "positionId", column = "position_id"),
			@Result(property = "departmentId", column = "dept_id"),
			@Result(property = "departmentName", column = "department_name", javaType = String.class),
			@Result(property = "positionName", column = "position_name"),
	})
	public List<Position> getPosByDepartmentId(Integer departmentId);
	
	@Delete(value = "delete from positions where position_id = #{positionId}")
	public boolean deletePosById(Integer positionId);
	
	@Update(value = """
			update positions set dept_id = #{departmentId}, position_name = initcap(#{positionName}) 
			where position_id = #{positionId}
			""")
	public boolean updatePosition(Map<String, Object> parameters);
	
	@Insert(value = """
			insert into positions(dept_id, position_name) values (#{departmentId}, initcap(#{positionName}))
			""")
	public boolean insertPosition();
}
