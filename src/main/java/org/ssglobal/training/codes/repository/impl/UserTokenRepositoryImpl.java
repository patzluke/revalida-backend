package org.ssglobal.training.codes.repository.impl;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.ssglobal.training.codes.model.UserToken;

public class UserTokenRepositoryImpl {
	private SqlSessionFactory ssf;

	public UserTokenRepositoryImpl(SqlSessionFactory ssf) {
		this.ssf = ssf;
	}

	public boolean createTokenImpl(Integer userId, String token) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			HashMap<String, Object> dataMap = new HashMap<>();
			dataMap.put("userId", userId);
			dataMap.put("token", token);

			session.insert("createToken", dataMap);

			session.commit();
			session.close();
			return true;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}
	
	public boolean updateUserTokenImpl(String token, Integer userId) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			HashMap<String, Object> dataMap = new HashMap<>();
			dataMap.put("token", token);
			dataMap.put("userId", userId);

			session.update("updateUserToken", dataMap);
			
			session.commit();
			session.close();
			return true;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}
	
	public boolean deleteUserTokenImpl(Integer userId) {
		SqlSession session = null;
		try {
			session = ssf.openSession();

			session.delete("deleteUserToken", userId);
			
			session.commit();
			session.close();
			return true;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}

	public boolean isUserTokenIdExistsImpl(Integer userId) {
		SqlSession session = null;
		try {
			session = ssf.openSession();

			UserToken record = session.selectOne("isUserTokenIdExists", userId);
			
			session.close();
			if (record != null) { 
				return true; 
			}
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}
	
	public boolean isUserTokenExistsImpl(String token) {
		SqlSession session = null;
		try {
			session = ssf.openSession();

			UserToken record = session.selectOne("isUserTokenExists", token);
			
			session.close();
			if (record != null) { 
				return true; 
			}
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}
}
