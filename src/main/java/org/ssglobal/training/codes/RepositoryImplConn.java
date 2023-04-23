package org.ssglobal.training.codes;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.KeyGenerator;

import org.apache.ibatis.session.SqlSessionFactory;
import org.ssglobal.training.codes.repository.impl.DepartmentRepositoryImpl;
import org.ssglobal.training.codes.repository.impl.PositionRepositoryImpl;
import org.ssglobal.training.codes.repository.impl.UserRepositoryImpl;
import org.ssglobal.training.codes.repository.impl.UserTokenRepositoryImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class RepositoryImplConn {
	private static SqlSessionFactory ssf() {
		DbConnection db = new DbConnection();
		String url = "jdbc:postgresql://localhost:5432/users";
		String username = "postgres";
		String password = "admin2255";
		String driver = "org.postgresql.Driver";
		
		db.buildConnectivity(url, username, password, driver);
		db.createTxFactory();
		db.buildConfiguration();
		SqlSessionFactory ssf = db.createSqlSessionFactory();
		return ssf;
	}
	
	public static UserRepositoryImpl userRepositoryImpl() {
		return new UserRepositoryImpl(ssf());
	}
	
	public static UserTokenRepositoryImpl userTokenRepositoryImpl() {
		return new UserTokenRepositoryImpl(ssf()); 
	}
	
	public static DepartmentRepositoryImpl departmentRepoImpl() {
		return new DepartmentRepositoryImpl(ssf());
	}
	
	public static PositionRepositoryImpl positionRepoImpl() {
		return new PositionRepositoryImpl(ssf());
	}
	
	public static String generateToken(Integer userId, String username) {
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("HmacSHA256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		Key key = keyGenerator.generateKey();
		String jwtToken = Jwts.builder()
							  .claim("userId", userId)
							  .setIssuedAt(new Date())
							  .setExpiration(Date.from(LocalDateTime.now().plusMinutes(10L).atZone(ZoneId.systemDefault()).toInstant()))
							  .signWith(key, SignatureAlgorithm.HS256)
							  .compact();
		userTokenRepositoryImpl().createTokenImpl(userId, jwtToken);
		return jwtToken;
	}

}
