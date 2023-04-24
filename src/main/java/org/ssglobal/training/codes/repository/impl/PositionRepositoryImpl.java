package org.ssglobal.training.codes.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.ssglobal.training.codes.model.Position;

public class PositionRepositoryImpl {
	
	private SqlSessionFactory ssf;
	
	public PositionRepositoryImpl(SqlSessionFactory ssf) {
		this.ssf = ssf;
	}

	public List<Position> selectAllpositionImpl(){
		List<Position> records = new ArrayList<>();
		SqlSession session  = null;
				
		try {	
			session  = ssf.openSession();
			
			records = session.selectList("selectAllpos");
			
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
	
	public Position getPosByIdImpl(Integer posId) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			
			Position record = session.selectOne("getPosbyId", posId);
			 
			session.close();
			return record;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}
	
	public List<Position> getPosByDepartmentIdImpl(Integer departmentId){
		List<Position> records = new ArrayList<>();
		SqlSession session  = null;
				
		try {	
			session  = ssf.openSession();
			
			records = session.selectList("getPosByDepartmentId", departmentId);
			
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
	
	public boolean deletePosByIdImpl(Integer positionId) {
		SqlSession session = null;
		try {
			session = ssf.openSession();

			session.delete("deletePosById", positionId);
			
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
	
	public boolean updatePosImpl(Position currentPos) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			
			HashMap<String, Object> dataMap = new HashMap<>();
			dataMap.put("positionName", currentPos.getPositionName());
			dataMap.put("departmentId", currentPos.getDepartmentId());
			dataMap.put("positionId", currentPos.getPositionId());

			session.update("updatePosition", dataMap);
			
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
	
	public boolean insertPosImpl(Integer departmentId, String posName) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			Position user = new Position(null, departmentId, posName);

			session.insert("insertPosition", user);

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
