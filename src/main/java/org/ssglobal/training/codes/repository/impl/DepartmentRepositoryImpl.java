package org.ssglobal.training.codes.repository.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.ssglobal.training.codes.model.Department;


public class DepartmentRepositoryImpl {
	private static Logger logger = Logger.getLogger(DepartmentRepositoryImpl.class.getName());
	private SqlSessionFactory ssf;
	
	public DepartmentRepositoryImpl(SqlSessionFactory ssf) {
		this.ssf = ssf;
	}
	
	public List<Department> selectAllDeparmentImpl(){
		List<Department> records = new ArrayList<>();
		SqlSession session  = null;
				
		try {	
			session  = ssf.openSession();
			
			records = session.selectList("selectAllDep");
			
			session.close();
			return Collections.unmodifiableList(records);
		} catch(Exception e) {
			session.rollback();
			logger.severe("DepartmentRepositoryImpl Line 36 exception: %s".formatted(e.getMessage()));
		} finally {
			session.close();
		}
		return new ArrayList<>();
	}
	
	public Department getDepByIdImpl(Integer depId) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			
			Department record = session.selectOne("getDepartmentbyId", depId);
			 
			session.close();
			return record;
		} catch (Exception e) {
			session.rollback();
			logger.severe("DepartmentRepositoryImpl Line 54 exception: %s".formatted(e.getMessage()));
		} finally {
			session.close();
		}
		return null;
	}
	
	public boolean deleteDepByIdImpl(Integer depId) {
		SqlSession session = null;
		try {
			session = ssf.openSession();

			session.delete("deleteDepById", depId);
			
			session.commit();
			session.close();
			return true;
		} catch (Exception e) {
			session.rollback();
			logger.severe("DepartmentRepositoryImpl Line 73 exception: %s".formatted(e.getMessage()));
		} finally {
			session.close();
		}
		return false;
	}
	
	public boolean updateDepImpl(Department currentDep) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			
			HashMap<String, Object> dataMap = new HashMap<>();
			dataMap.put("departmentName", currentDep.getDepartmentName());
			dataMap.put("departmentId", currentDep.getDepartmentId());

			session.update("updateDepartment", dataMap);
			
			session.commit();
			session.close();
			return true;
		} catch (Exception e) {
			session.rollback();
			logger.severe("DepartmentRepositoryImpl Line 96 exception: %s".formatted(e.getMessage()));
		} finally {
			session.close();
		}
		return false;
	}
	
	public boolean insertDepImpl(String depName) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			Department user = new Department(null, depName);

			session.insert("insertDepartment", user);

			session.commit();
			session.close();
			return true;
		} catch (Exception e) {
			session.rollback();
			logger.severe("DepartmentRepositoryImpl Line 116 exception: %s".formatted(e.getMessage()));
		} finally {
			session.close();
		}
		return false;
	}
}
