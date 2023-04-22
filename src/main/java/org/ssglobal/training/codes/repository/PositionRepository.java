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
	
	@Select(value = "select * from positions p")
	@Results(value = {
			@Result(property = "positionId", column = "position_id"),
			@Result(property = "positionName", column = "position_name"),
	})
	public List<Position> selectAllpos();
	
	@Select(value = "select * from positions where position_id = #{positionId}")
	@Results(value = {
			@Result(property = "positionId", column = "position_id"),
			@Result(property = "positionName", column = "position_name"),
	})
	public Position getPosbyId(Integer positionId);
	
	@Delete(value = "delete from positions where position_id = #{positionId}")
	public boolean deletePosById(Integer positionId);
	
	@Update(value = """
			update positions set position_name = #{positionName} where position_id = #{positionId}
			""")
	public boolean updatePosition(Map<String, Object> parameters);
	
	@Insert(value = """
			insert into positions(position_name) values (#{positionName})
			""")
	public boolean insertPosition();
}
