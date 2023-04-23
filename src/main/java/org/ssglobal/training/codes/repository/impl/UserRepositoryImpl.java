package org.ssglobal.training.codes.repository.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.ssglobal.training.codes.model.User;
import org.ssglobal.training.codes.model.UserPasswordChange;

public class UserRepositoryImpl {
	
	private SqlSessionFactory ssf;

	public UserRepositoryImpl(SqlSessionFactory ssf) {
		this.ssf = ssf;
	}
	
	public List<User> selectUsersInnerJoinDepartmentAndPositionImpl() {
		List<User> records = new ArrayList<>();
		SqlSession session = null;
		try {
			session = ssf.openSession();
			
			records = session.selectList("selectUsersInnerJoinDepartmentAndPosition");
			
			session.close();
			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return Collections.unmodifiableList(records);
	}

	public List<User> selectAllUsersImpl(){
		List<User> records = new ArrayList<>();
		SqlSession session  = null;
				
		try {	
			session  = ssf.openSession();
			
			records = session.selectList("selectAllUsers");
			
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
	
	public User getUserByIdImpl(Integer employeeId) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			
			User record = session.selectOne("getUserById", employeeId);
			 
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
	
	public User searchUserByEmailAndPassImpl(String email, String password) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			HashMap<String, String> dataMap = new HashMap<>();
			dataMap.put("email", email);
			dataMap.put("password", password);

			User record = session.selectOne("searchUserByEmailAndPass", dataMap);
			
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
	
	public User searchPasswordByIdImpl(Integer employeeId) {
		SqlSession session = null;
		try {
			session = ssf.openSession();

			User record = session.selectOne("searchPasswordById", employeeId);
			
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
	
	public boolean deleteUserByIdImpl(Integer employeeId) {
		SqlSession session = null;
		try {
			session = ssf.openSession();

			session.delete("deleteUserById", employeeId);
			
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
	
	public boolean updateUserImpl(User currentUser) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			
			HashMap<String, Object> dataMap = new HashMap<>();
			dataMap.put("email", currentUser.getEmail());
			dataMap.put("mobileNumber", currentUser.getMobileNumber());
			dataMap.put("userType", currentUser.getUserType());
			dataMap.put("firstName", currentUser.getFirstName());
			dataMap.put("middleName", currentUser.getMiddleName());
			dataMap.put("lastName", currentUser.getLastName());
			dataMap.put("deptId", currentUser.getDepartmentId());
			dataMap.put("birthDate", currentUser.getBirthDate());
			dataMap.put("gender", currentUser.getGender());
			dataMap.put("positionId", currentUser.getPositionId());
			dataMap.put("employeeId", currentUser.getEmployeeId());

			session.update("updateUser", dataMap);
			
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
	
	public boolean changePasswordImpl(UserPasswordChange user) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			
			HashMap<String, Object> dataMap = new HashMap<>();
			dataMap.put("password", user.getPassword());
			dataMap.put("email", user.getEmail());

			session.update("changePassword", dataMap);
			
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
	
	public boolean insertUser(String email, String mobileNumber, 
			String password, String userType, String firstName, 
			String middleName, String lastName, Integer departmentId, 
			LocalDate birthDate, String gender, Integer positionId) {
		SqlSession session = null;
		try {
			session = ssf.openSession();
			User user = new User(null, email, mobileNumber, password, userType, firstName, middleName, lastName, departmentId, birthDate, gender, positionId);

			session.insert("inserUser", user);

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
