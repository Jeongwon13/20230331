console.log("main.js loaded.");

// 회원 정보 조회 비동기 통신(AJAX)
document.getElementById("select1").addEventListener("click", function() {
    const input = document.getElementById("in1");
    const div = document.getElementById("result1");

    // AJAX 코드 작성(jQuery 방식)
    $.ajax({
        url: "member/selectOne",
        data: {"memberEmail" : input.value},
        type: "POST",
        dataType: "JSON", // dataType: 응답 데이터 형식을 지정
                        // -> JSON으로 지정 시 자동으로 JS 객체로 변환
        success: function(member) {
                    // DB까지 갔다가 반환해온 객체임 member는
            console.log(member);
            // 1) div에 작성된 내용 모두 삭제
           
           
            div.innerHTML = "";

            if(member != null){ // 회원 정보 존재 O

                // 2) ul 요소 생성
                const ul = document.createElement("ul"); // <ul></ul>

                // 3) li 요소 생성 * 5 + 내용 추가		//  
                const li1 = document.createElement("li"); // <li></li>
                li1.innerText = "이메일 : " + member.memberEmail;

                const li2 = document.createElement("li");
                li2.innerText = "닉네임 : " + member.memberNickname;

                const li3 = document.createElement("li");
                li3.innerText = "전화번호 : " + member.memberTel;

                const li4 = document.createElement("li");
                li4.innerText = "주소 : " + member.memberAddress;

                const li5 = document.createElement("li");
                li5.innerText = "가입일 : " + member.enrollDate;

                // 4) ul에 li를 순서대로 추가
                ul.append(li1, li2, li3, li4, li5);
		 
                // 5) div에 ul 추가
                div.append(ul);

            } else { // 회원 정보 존재 X

                // 1) h4 요소 생성
                const h4 = document.createElement("h4");

                // 2) 내용 추가
                h4.innerText = "일치하는 회원이 없습니다";

                // 3) 색 추가
                h4.style.color = "red";

                // 4) div에 추가
                div.append(h4);
            }
            
        },
        error: function(request) {
            console.log("AJAX 에러 발생");
            console.log("상태코드: " + request.status) // 404, 500
        }             
    })
    

});

 

function selectTwo() {
	
	$.ajax({
    url: "member/selectTwo",
    dataType: "JSON", // dataType: 응답 데이터 형식을 지정
                    // -> JSON으로 지정 시 자동으로 JS 객체로 변환
    success: function(member) {
                // DB까지 갔다가 반환해온 객체임 member는
        console.log(member);
        // 1) div에 작성된 내용 모두 삭제
        
        const memberList = document.getElementById("memberList");
		
		
        if(member != null){ // 회원 정보 존재 O
        memberList.innerHTML = "";
 		 
		$.each(member, function(index, value) {
   		 const tr = document.createElement("tr");

   		 const td1 = document.createElement("td");
   		 td1.innerText = value.memberNo;

   		 const td2 = document.createElement("td");
   		 td2.innerText = value.memberEmail;

   		 const td3 = document.createElement("td");
   		 td3.innerText = value.memberNickname;

  		 tr.append(td1, td2, td3);

 	 	 memberList.append(tr);
 	 	 
 	 
  		 
});
    
    
    
        } else { // 회원 정보 존재 X

            // 1) h4 요소 생성
            const h4 = document.createElement("h4");

            // 2) 내용 추가
            h4.innerText = "일치하는 회원이 없습니다";

            // 3) 색 추가
            h4.style.color = "red";

            // 4) div에 추가
            div.append(h4);
        }
        
    },
    error: function(request) {
        console.log("AJAX 에러 발생");
        console.log("상태코드: " + request.status) // 404, 500
    }             
})
 
	}

	

// 즉시 실행 함수
(function() {
	selectTwo();
	window.setInterval(selectTwo, 10000);
})();
 