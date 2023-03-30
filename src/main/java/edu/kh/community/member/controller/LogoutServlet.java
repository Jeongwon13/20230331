package edu.kh.community.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/member/logout")
public class LogoutServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ** 로그아웃 **
		// Session Scope에 세팅된 회원 정보를 없앰
		
		// Session 얻어오기
		HttpSession session = req.getSession();
		
		// 1) Session에서 회원 정보만 없앰
		// session.removeAttribute("loginMember");
		
		// 2) Session 전체를 없애고 새로운 Session 만들기(실무에서는 이거)
		session.invalidate(); // 세션 무효화 -> 현재 세션을 없앰 -> 자동으로 새로운 세션 생성됨.
		
		// 메인 페이지로 돌아가기
		resp.sendRedirect(req.getContextPath());
		
	}
}
