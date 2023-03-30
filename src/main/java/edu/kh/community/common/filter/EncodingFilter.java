package edu.kh.community.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

/* 필터(Filter)
 * 
 * - 클라이언트 요청 시 생성되는
 * 	 HttpServletRequest, HttpServletResponse가
 * 	 요청을 처리하는 Servlet에 도달하기 전에
 * 	 특정 코드를 수행하는 클래스이다.
 * 
 * 	 [요청흐름]
 * 	 클라이언트 -> 요청 -> HttpServletRequest/HttpServletResponse 생성
 * 	 -> 필터 거친 후 -> 요청 처리 Servlet
 * 	
 * 	 - 여러 필터 만들어서 연쇄적으로 연결해서 수행 할 수 있음.(FilterChain)
 * 
 * @WebFilter("url 패턴")
 * - 해당 클래스를 필터 클래스로 등록
 * - url 패턴에 일치하는 요청이 있을 경우 해당 요청을 필터링함.
 * 
 * filterName 속성: 필터의 이름을 지정, 필터 순서 시 사용
 * urlPatterns 속성: 요청 주소 패턴
 */

//		/ == /community  둘이 뜻이 같음 == 최상위 주소임.
//		* == 모든
//		/* == 최상위 주소 하위 모든 == 모든 요청

@WebFilter(filterName="encodingFilter", urlPatterns="/*")
public class EncodingFilter extends HttpFilter implements Filter {
	
	// 서버 실행 시 또는 필터 코드 변경 시
	// 필터 객체가 자동 생성되는데
	// 그 때, 필터에 필요한 내용을 초기화하는 메서드
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("문자 인코딩 필터 초기화 되었셈~");
	}
	
	// 서버 실행 중 필터 코드가 변경되어
	// 기존 필터를 없애야 할 때 수행되는 메서드
	public void destroy() {
		System.out.println("문자 인코딩 필터 파괴술");
	}
	
	// 필터 역할 수행하는 메서드
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// ServletRequest == HttpServletRequest의 부모 타입
		// ServletResponse == HttpServletResponse의 부모 타입
		// -> 필요 시 다운 캐스팅
		
		// 모든 요청의 문자 인코딩을 UTF-8로 지정해줄 것이다.
		request.setCharacterEncoding("UTF-8");
		
		// 모든 응답의 문자 인코딩을 UTF-8로도 지정해줘
		response.setCharacterEncoding("UTF-8");
	
		// -----------------------------------------------------
		
		// application scope로 최상위 경로를 얻어올 수 있는 값 세팅
		
		// 1) application 내장 객체 얻어오기
		ServletContext application = request.getServletContext();
		
		// 2) 최상위 주소 얻어오기
		String contextPath = ((HttpServletRequest)request).getContextPath();
		
		// 3) 세팅
		application.setAttribute("contextPath", contextPath);
		
		
		// 연결된 다음 필터 수행(없으면 Servlet 수행)
		chain.doFilter(request, response);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
