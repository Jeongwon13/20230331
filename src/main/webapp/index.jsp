
<%@ page language="java" contentType= "text/html; charset=UTF-8" 
	pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>17_웹 문서 구조</title>

    <link rel="stylesheet" href="resources/css/main-style.css">
    <link rel="stylesheet" href="resources/css/myPage-style.css">
    <link rel="stylesheet" href="resources/css/signUp-style.css">
    

    <!--  fontawesome 사이트 아이콘 이용   -->
    <script src="https://kit.fontawesome.com/f7459b8054.js" crossorigin="anonymous"></script>
</head>
<body>
    <!-- 
        기존 영역 분할에 사용한 div, span 태그는
        태그의 이름만 봤을 때 나눈다는 것 이외의 의미를 파악할 수 없다.
        -> id, 또는 class 속성을 필수적으로 추가해야하는 번거로움이 있음

        이러한 문제점을 해결하고자
        태그의 이름만으로 어느정도 어떤 역할을 하는지 알 수 있고
        추가적으로 웹 접근성 향상에 도움이되는
        시맨틱 태그(Semantic Tag, 의미있는 태그)가 HTML5에서 추가됨.

        * 시맨틱 태그는 div 태그의 이름만 바뀐 것으로 생각하는게 좋다!
         
        [시맨틱 태그 종류]
        <main> : 현재 문서의 주된 콘텐츠를 작성하는 영역

        <header> : 문서의 제목, 머리말 영역

        <footer> : 문선의 하단 부분, 꼬리말, 정보 작성 영역

        <nav> : 나침반 역할(다른 페이지, 사이트 이동)의 링크 작성 영역

        <aside> : 사이드바, 광고 등을 표시하는 양쪽 영역
        
        <section> : 구역을 구분하기 위한 영역

        <article> : 본문과 독립된 콘텐츠를 작성하는 영역
    -->

    <main>
       
       <!-- 
       	jsp:include 태그
       	다른 jsp파일의 내용을 해당 위치에 포함시킴
       	 * 경로 작성 시
       	 	내부 접근 경로(최상위: /webapp)  
      
        -->

		 
       <jsp:include page="/WEB-INF/views/common/header.jsp" />

        <section class="content">
            <section class="content-1">
            	<h3>회원 정보 조회(AJAX)</h3>
            	<p>이메일을 입력받아 일치하는 회원 정보를 출력</p>
            	
            	이메일: <input type="text" id="in1">
            	<button id="select1">조회</button>
            	<div id="result1" style="height:150px;"></div>
            
            
            </section>



            <section class="content-2">

			<%-- if/else --%>
			<c:choose>
				<%-- choose 내부에는 jsp용 주석만 사용 --%>
				
				<%-- 로그인이 되어있지 않은 경우 --%>
				<c:when test="${empty sessionScope.loginMember}">
				 <form action="member/login" method="POST" name="login-form" onsubmit="return loginValidate()">
        
                 
                    <fieldset id="id-pw-area">
                        <section>
                            <input type="text" name="inputEmail"
                             placeholder="이메일" autocomplete="off"
                             value="${cookie.saveId.value}"
                             >
                            <!-- autocomplete="off" : 자동완성 사용 X -->
        
                            <input type="password" name="inputPw" placeholder="비밀번호">
                        </section>
        
                        <section>
                          
                            <button>로그인</button>
                        </section>
                    </fieldset>
        
        
           
                    <label>
                        <input type="checkbox" name="saveId"> 아이디 저장
                    </label>
        			
        		<!-- 
        			WEB-INF 폴더는 외부로부터 직접적으로 요청할 수 없는 폴더
        			왜? 중요한 코드(설정관련, 자바..)
        			외부로부터 접근을 차단하기 위해서(보안상)
        			
        			대신 Servlet을 이용해 내부 접근(forward) 가능
        			
        		 -->
                    <!-- 회원가입 / ID/PW 찾기 -->
                    <article id="signUp-find-area">
                        <a href="${contextPath}/member/signUp">회원가입</a>
                        <span>|</span>
                        <a href="#">ID/PW찾기</a>
                    </article>
                </form>	
				</c:when>
			
				<%-- 로그인이 되어있는 경우 --%>
				<c:otherwise>
					<article class="login-area">
                            <!-- 회원 프로필 이미지 -->
            				<a href="${pageContext.request.contextPath}/member/myPage/profile">
                                
                                <c:if test="${empty loginMember.profilemage}">
                                    <img src="${pageContext.request.contextPath}/resources/images/user.png" id="member-profile">
                                </c:if>

                                <c:if test="${!empty loginMember.profilemage}">
                                    <img src="${pageContext.request.contextPath}/${loginMember.profilemage}" id="member-profile">
                                </c:if>

            				</a>

                            <!-- 회원 정보 + 로그아웃 버튼 -->                                       
                            <div class="my-info">
                                <div>
                                    <a href="${pageContext.request.contextPath}/member/myPage/info" id="nickname">${loginMember.memberNickname}</a>

                                    <a href="/community/member/logout" id="logout-btn">로그아웃</a>
                                </div>

                                <p>
                                    ${loginMember.memberEmail}
                                </p>
                            </div>
            			</article>
				
				</c:otherwise>
			</c:choose>
            </section>
            
        
           


        </section>
        <hr>
        <br>
         <section class="content3" style="margin-bottom:200px">
				
            	<h3>회원 목록 조회</h3>
            	<br>
            	<p>일정 시간 마다 비동기로 회원 목록(회원 번호, 이메일, 닉네임) 조회</p>
                <br>
                <table border="1px solid black" width="500px" id="table1">
         			<thead>
         		    <tr>
                        <th>회원 번호</th>
                        <th>이메일</th>
                        <th>닉네임</th>
                    </tr>
                    </thead>
                	<tbody id="memberList">
                	
                	
                	</tbody>
                </table>

            </section>
		
    </main>
 
		
 
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
    <script src="${contextPath}/resources/js/main.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
</body>
</html>