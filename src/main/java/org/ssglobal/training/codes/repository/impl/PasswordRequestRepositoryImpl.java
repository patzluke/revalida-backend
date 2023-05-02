package org.ssglobal.training.codes.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.ssglobal.training.codes.model.PasswordRequest;

public class PasswordRequestRepositoryImpl {
	private static Logger logger = Logger.getLogger(PasswordRequestRepositoryImpl.class.getName());
	private SqlSessionFactory ssf;

	public PasswordRequestRepositoryImpl(SqlSessionFactory ssf) {
		this.ssf = ssf;
	}
	
	public List<PasswordRequest> getAllPasswordRequestsJoinByUsersImpl(){
		List<PasswordRequest> records = new ArrayList<>();
		SqlSession session  = null;
				
		try {	
			session  = ssf.openSession();
			
			records = session.selectList("getAllPasswordRequestsJoinByUsers");
			
			session.close();
			return Collections.unmodifiableList(records);
		} catch(Exception e) {
			session.rollback();
			logger.severe("PasswordRequestRepositoryImpl Line 34 exception: %s".formatted(e.getMessage()));
		} finally {
			session.close();
		}
		return new ArrayList<>();
	}
	
	public boolean insertPasswordRequestImpl(Integer empId) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			PasswordRequest passwordRequest = new PasswordRequest(null, empId, null);

			session.insert("insertPasswordRequest", passwordRequest);

			session.commit();
			session.close();
			return true;
		} catch (Exception e) {
			session.rollback();
			logger.severe("PasswordRequestRepositoryImpl Line 54 exception: %s".formatted(e.getMessage()));
		} finally {
			session.close();
		}
		return false;
	}
	
	public boolean updateStatusImpl(PasswordRequest pr) {
		SqlSession session = null;
		
		try {
			session = ssf.openSession();
			
			HashMap<String, Object> dataMap = new HashMap<>();
			dataMap.put("id", pr.getId());
			dataMap.put("status", pr.getStatus());
			
			session.update("updateStatus", dataMap);
			session.commit();
			session.close();
			return true;
		} catch(Exception e) {
			session.rollback();
			logger.severe("PasswordRequestRepositoryImpl Line 77 exception: %s".formatted(e.getMessage()));
		} finally {
			session.close();
		}
		return false;
	}
	
	public PasswordRequest getRequestByIdImpl(Integer id) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			
			PasswordRequest record = session.selectOne("getPasswordRequestbyId", id);
			 
			session.close();
			return record;
		} catch (Exception e) {
			session.rollback();
			logger.severe("PasswordRequestRepositoryImpl Line 95 exception: %s".formatted(e.getMessage()));
		} finally {
			session.close();
		}
		return null;
	}
}
