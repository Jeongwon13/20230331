package edu.kh.community.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.kh.community.member.model.service.MemberService;
import edu.kh.community.member.model.vo.Member;
 

@WebServlet("/member/selectTwo")
public class SelectTwoServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NumberFormatException {
 
	 
	 
		
		
		try {
			MemberService service = new MemberService();
			
			
			List<Member> selectTwo = service.selectTwo();
		 
 
			new Gson().toJson(selectTwo, resp.getWriter());
			
			
 
			} catch (Exception e) {
				 
				e.printStackTrace();
			}
		 
	}
}
