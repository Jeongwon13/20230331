package edu.kh.community.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
 

public class JDBCTemplate {
 
	
	private static Connection conn = null;
	 
	/** DB 연결 정보를 담고있는 Connection 객체 생성 및 반환 메서드
	 * @return conn
	 */
	public static Connection getConnection() {
		
		try {
			// JNDI(Java Naming and Directory Interface API)
			// - 디렉토리에 접근하는데 사용하는 Java API
			// - 애플리케이션(프로그램, 웹앱)은 JNDI를 이용해 파일 또는 서버 Resource를 찾을 수 있음.
			Context initContext = new InitialContext();
			
			// servers -> context.xml 파일 찾기
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			
			// DBCP 세팅의 <Resource>태그를 찾아서 DataSource 형식의 객체로 얻어오기
			// DataSource란? : Connection Pool을 구현하는 객체(만들어둔 Connection 얻어오기 가능하도록 해줌)
			DataSource ds = (DataSource)envContext.lookup("jdbc/oracle");
			
			ds.getConnection();
			
			conn = ds.getConnection();
			
			conn.setAutoCommit(false);
			
		} catch(Exception e) {
			System.out.println("[Connection 생성 중 예외 발생]");
			e.printStackTrace();
		}
		
		return conn;
	}


	/** Connection 객체 자원 반환 메소드
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			
			if(conn != null && !conn.isClosed()) conn.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Statement(부모), PreparedStatment(자식) 객체 자원 반환 메서드
	 * (다형성, 동적바인딩)
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) stmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** ResultSet 객체 자원 반환 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if(rs != null && !rs.isClosed()) rs.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 트랜잭션 Commit 메서드
	 * @param conn
	 */
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) conn.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 트랜잭션 Rollback 메서드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) conn.rollback();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
 