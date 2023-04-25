package org.ssglobal.training.codes.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.ssglobal.training.codes.model.PasswordRequest;

public class PasswordRequestRepositoryImpl {
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
			e.printStackTrace();
		} finally {
			session.close();
		}
		return new ArrayList<>();
	}
	
	public boolean insertPasswordRequestImpl(Integer employeeId) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			PasswordRequest passwordRequest = new PasswordRequest(null, employeeId, null);

			session.insert("insertPasswordRequest", passwordRequest);

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
}
