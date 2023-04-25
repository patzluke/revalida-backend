package org.ssglobal.training.codes;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;

import org.ssglobal.training.codes.model.Department;
import org.ssglobal.training.codes.model.PasswordRequest;
import org.ssglobal.training.codes.model.Position;
import org.ssglobal.training.codes.model.User;
import org.ssglobal.training.codes.model.UserToken;
import org.ssglobal.training.codes.repository.DeparmentRepository;
import org.ssglobal.training.codes.repository.PasswordRequestRepository;
import org.ssglobal.training.codes.repository.PositionRepository;
import org.ssglobal.training.codes.repository.UserRepository;
import org.ssglobal.training.codes.repository.UserTokenRepository;

public class DbConnection {
	private BasicDataSource ds;
	private JdbcTransactionFactory tx;
	private Configuration config;
	
	// step 1: setup its configuration database configuration
	public boolean buildConnectivity(String url, String username, String password, String driver) {
		try {
			ds = new BasicDataSource();
			ds.setDriverClassName(driver);
			ds.setUrl(url);
			ds.setUsername(username);
			ds.setPassword(password);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//step 2: setup transaction management
	public boolean createTxFactory() {
		try {
			tx = new JdbcTransactionFactory();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//step 3: build the configuration class
	public boolean buildConfiguration() {
		try {
			//a setup config
			Environment env = new Environment("training", tx, ds);
			config = new Configuration(env);
			
			//b entity model registration
			TypeAliasRegistry tar = config.getTypeAliasRegistry();
			tar.registerAlias("user", User.class);
			tar.registerAlias("userToken", UserToken.class);
			tar.registerAlias("department", Department.class);
			tar.registerAlias("position", Position.class);
			tar.registerAlias("passwordRequest", PasswordRequest.class);

			//c dao or Repository registration
			config.addMapper(UserRepository.class);
			config.addMapper(UserTokenRepository.class);
			config.addMapper(DeparmentRepository.class);
			config.addMapper(PositionRepository.class);
			config.addMapper(PasswordRequestRepository.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//step 4: create the session factory
	public SqlSessionFactory createSqlSessionFactory() {
		try {
			SqlSessionFactoryBuilder sfBuilder = new SqlSessionFactoryBuilder();
			return sfBuilder.build(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
