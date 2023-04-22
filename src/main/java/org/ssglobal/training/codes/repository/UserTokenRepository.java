package org.ssglobal.training.codes.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ssglobal.training.codes.model.UserToken;

public interface UserTokenRepository {

	@Insert(value = "insert into user_tokens values (#{userId}, #{token})")
	public boolean createToken(Map<String, Object> parameters);
	
	@Update(value = "update user_tokens set token = #{token} where user_id = #{userId}")
	public boolean updateUserToken(Map<String, Object> parameters);
	
	@Delete(value = "delete from user_tokens where user_id = #{userId}")
	public boolean deleteUserToken(Integer userId);
	
	@Select(value = "select * from user_tokens where user_id = #{userId}")
	public UserToken isUserTokenIdExists(Integer userId);
	
	@Select(value = "select * from user_tokens where token = #{token}")
	public UserToken isUserTokenExists(String token);
}
