package edu.kh.community.member.model.dao;
import static edu.kh.community.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;

import edu.kh.community.member.model.vo.Member;

public class MemberDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public MemberDAO() {
		try {
			prop = new Properties();
			
			String filePath = MemberDAO.class.getResource
					("/edu/kh/community/sql/member-sql.xml").getPath();
			
			prop.loadFromXML(new FileInputStream(filePath));
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public Member selectOne(Connection conn, String memberEmail) throws Exception {
		
		Member member = null;
		
		try {
			String sql = prop.getProperty("selectOne");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberEmail);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new Member();
				
				member.setMemberEmail(rs.getString(1));
				member.setMemberEmail(rs.getString(2));
				member.setMemberTel(rs.getString(3));
				member.setMemberAddress(rs.getString(4));
				member.setEnrollDate(rs.getString(5));
				
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return member;
	}
	
	
	public List<Member> selectTwo(Connection conn) throws Exception, ServletException {
		 
		List<Member> selectTwo = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("selectTwo");
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			int	memberNo = rs.getInt("MEMBER_NO");
			String memberEmail = rs.getString("MEMBER_EMAIL");
			String memberNick = rs.getString("MEMBER_NICK");
			
			selectTwo.add(
					new Member(memberNo, memberEmail, memberNick)
					);
			}
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return selectTwo;
	}

	/** 로그인 DAO
	 * @param conn
	 * @param mem
	 * @return loginmember
	 */
	public Member login(Connection conn, Member mem) throws Exception {
		// 결과 저장용 변수
		Member loginMember = null;
		
		try {
			// SQL 얻어오기
			String sql = prop.getProperty("login");
			
			// PreparedStatement 생성 및 SQL 적재(?가 있으니까)
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mem.getMemberEmail());
			pstmt.setString(2, mem.getMemberPw());
			
			// SQL 수행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				loginMember = new Member();
				
				loginMember.setMemberNo(  rs.getInt("MEMBER_NO") );
				loginMember.setMemberEmail( 	rs.getString("MEMBER_EMAIL") );
				loginMember.setMemberNickname( 	rs.getString("MEMBER_NICK")	 );
				loginMember.setMemberTel( 		rs.getString("MEMBER_TEL") 	 );
				loginMember.setMemberAddress( 	rs.getString("MEMBER_ADDR")  );
				loginMember.setProfilemage( 	rs.getString("PROFILE_IMG")  );
				loginMember.setEnrollDate( 		rs.getString("ENROLL_DT") 	 );
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return loginMember;
	
	}

	public int emailDupCheck(Connection conn, String memberEmail) throws Exception {
		
		// 결과 저장용 변수 선언
		int result = 0;
		
		try {
			// SQL 얻어오기
			String sql = prop.getProperty("emailDupCheck");
			
			// pstmt 생성
			pstmt = conn.prepareStatement(sql);
			
			// 위치 홀더 알맞은 값 세팅
			pstmt.setString(1, memberEmail);
			
			// SQL 실행 후 결과 반환 받기
			rs = pstmt.executeQuery();
			
			// 아래 1은 count(*)의 값이야
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		return result;
	}

	/** 인증번호, 발급일 수정 DAO
	 * @param conn
	 * @param inputEmail
	 * @param cNumber
	 * @return
	 */
	public int updateCertification(Connection conn, String inputEmail, String cNumber) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateCertification");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, cNumber);
			pstmt.setString(2, inputEmail);
			
			result = pstmt.executeUpdate();
		
		} finally {
			 
			close(pstmt);
		}
		return result;
	}

	
	/** 인증번호 생성 DAO
	 * @param conn
	 * @param inputEmail
	 * @param cNumber
	 * @return
	 */
	public int insertCertification(Connection conn, String inputEmail, String cNumber) throws Exception {
		int result = 0;
		
		try { 
			String sql = prop.getProperty("insertCertification");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, inputEmail);
			pstmt.setString(2, cNumber);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
		 
	}

	/** 인증번호 확인 DAO
	 * @param conn
	 * @param inputEmail
	 * @param cNumber
	 * @return
	 */
	public int checkNumber(Connection conn, String inputEmail, String cNumber) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("checkNumber");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, inputEmail);
			pstmt.setString(2, cNumber);
			pstmt.setString(3, inputEmail);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} finally {
			 close(rs);
			 close(pstmt);
		}
		
		return result;
	}

	
	public int signUp(Connection conn, String memberEmail, String memberPw, String memberNickname, String memberTel) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("signUp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberEmail);
			pstmt.setString(2, memberPw);
			pstmt.setString(3, memberNickname);
			pstmt.setString(4, memberTel);
			
			result = pstmt.executeUpdate();
			conn.commit();
	 
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/** 프로필 이미지 변경 DAO
	 * @param conn
	 * @param memberNo
	 * @param profileImage
	 * @return result
	 * @throws SQLException 
	 */
	public int updateProfileImage(Connection conn, int memberNo, String profileImage) throws SQLException {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateProfileImage");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, profileImage);
			pstmt.setInt(2, memberNo);
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	
}
