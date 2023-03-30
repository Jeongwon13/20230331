package edu.kh.community.member.model.service;

import static edu.kh.community.common.JDBCTemplate.close;
import static edu.kh.community.common.JDBCTemplate.commit;
import static edu.kh.community.common.JDBCTemplate.getConnection;
import static edu.kh.community.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import edu.kh.community.member.model.dao.MemberDAO;
import edu.kh.community.member.model.vo.Member;

public class MemberService {

	private MemberDAO dao = new MemberDAO();
	
	public Member selectOne(String memberEmail) throws Exception {
		Connection conn = getConnection();
		
		Member member = dao.selectOne(conn, memberEmail);
		
		close(conn);
		
		return member;
	}

	
	public List<Member> selectTwo() throws Exception {
		Connection conn = getConnection();
		
		List<Member> selectTwo = dao.selectTwo(conn);
		
		close(conn);
		
		return selectTwo;
		
	}
	
	
	/** 로그인 서비스
	 * @param mem
	 * @return
	 */
	public Member login(Member mem) throws Exception {
		// Connection 얻어오기
		Connection conn = getConnection();
		
		// DAO 수행
		Member loginMember = dao.login(conn, mem);
		
		// Connection 반환
		close(conn);
		
		
		// 결과 반환
		return loginMember;
		
	}


	/** 이메일 중복검사 Service
	 * @param memberEmail
	 * @return
	 */
	public int emailDupCheck(String memberEmail) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.emailDupCheck(conn, memberEmail);
		
		close(conn);
		
		return result;
		
	}


	/** 인증번호 DB 추가 Service
	 * @param inputEmail
	 * @param cNumber
	 * @return
	 */
	public int insertCertification(String inputEmail, String cNumber) throws Exception {
		
		Connection conn = getConnection();
		
		// 1) 입력한 이메일과 일치하는 값이 있으면 수정(UPDATE)해라
		int result = dao.updateCertification(conn, inputEmail, cNumber);
		
		// 2) 일치하는 이메일이 없는 경우 -> 처음으로 인증번호 발급받음 -> 삽입(INSERT)
		if(result == 0) {
			result = dao.insertCertification(conn, inputEmail, cNumber); 			
		}
		
		// 트랜잭션 제어! (DML 구문)
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
				
				
		return result;
		
		
	
	}


	/** 인증번호 확인하는 서비스
	 * @param inputEmail
	 * @param cNumber
	 * @return
	 * @throws Exception
	 */
	public int checkNumber(String inputEmail, String cNumber) throws Exception {
		 
		Connection conn = getConnection();
		
		int result = dao.checkNumber(conn, inputEmail, cNumber);
		
		close(conn);
		
		return result;
		
		
	}

	
	public int signUp(String memberEmail, String memberPw, String memberNickname, String memberTel) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.signUp(conn, memberEmail, memberPw, memberNickname, memberTel);
		
		close(conn);
		
		return result;
				
	}


	/** 프로필 이미지 변경 Service
	 * @param memberNo
	 * @param profileImage
	 * @return
	 */
	public int updateProfileImage(int memberNo, String profileImage) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updateProfileImage(conn, memberNo, profileImage);
		
		// 트랜잭션 제어 처리
		if(result == 0) commit(conn);
		else			rollback(conn);
		
		close(conn);
				
		return result;
	}

	 
	}
 
