package edu.kh.community.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.kh.community.member.model.service.MemberService;
import edu.kh.community.member.model.vo.Member;

@WebServlet("/member/selectOne")
public class SelectOneServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 파라미터 얻어오기
		String memberEmail = req.getParameter("memberEmail");
		
		try {
			MemberService service = new MemberService();
			
			Member member = service.selectOne(memberEmail);
			
			// ** Java 객체를 Javascript 객체로 변환해서 응답(출력) **
			// Java 객체 -> Javascript 형태 문자열인 JSON -> Javascript 객체
			
			// 1) JSON 직접 작성 -> 오타가 아주 심각할 듯~ㅋ
			
			// 2) JSON-simple이라는 라이브러리 JSONObject 사용
			
			// 3) GSON 라이브러리 이용한 Java 객체 -> JSON 변환
			// 이게 제일 짱이야
			// new GSON().toJson(객체, 응답스트림);
			// -> 매개변수에 작성된 객체를 JSON 형태로 변환하고 
			//나서 응답스트림 통해 출력
			new Gson().toJson(member, resp.getWriter());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	
}
