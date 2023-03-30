// 유효성 검사 여부를 기록할 객체 생성
const checkObj = {
    "memberEmail" : false,
    "memberPw" : false,
    "memberPwConfirm" : false,
    "memberNickname" : false,
    "memberTel" : false,
    "sendEmail" : false,
};

// 이메일 유효성 검사
const memberEmail = document.getElementById("memberEmail");
const emailMessage = document.getElementById("emailMessage");

memberEmail.addEventListener("input", function () {
    // 입력이 되지 않은 경우
    if(memberEmail.value.trim().length == 0) {
        emailMessage.innerText = "이메일을 입력해주세요.";
        emailMessage.classList.remove("confirm", "error");
        
        checkObj.memberEmail = false; // 유효하지 않다. 기록 안할거임
        return;
    }

    // 입력이 된 경우
    const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$/;
    if(regExp.test(memberEmail.value)) { // 유효한 경우
        //**********  이메일 중복 검사 (ajax) 진행 예정 ************ 
        $.ajax({
            url : "emailDupCheck",
            // 필수 속성 url
            // 현재 주소: /community/member/signUp
            // 상대 경로: /community/member/emailDupCheck

            data: {"memberEmail" : memberEmail.value},
            // data 속성: 비동기 통신 시 서버로 전달한 값을 작성(JS 객체 형식)
            // -> 비동기 통신 시 input에 입력된 값을
            // "memberEmail" 이라는 key 값(파라미터)으로 전달

            success : function(result) {
                // 비동기 통신(ajax)가 오류 없이 요청/응답 성공한 경우

                // 매개변수 result : servlet에서 출력된 result 값이 담겨있음.
                console.log(result);

                if(result == 1) { // 중복임
                    emailMessage.innerText = "이미 사용중인 이메일 입니다.";
                    emailMessage.classList.add("error");
                    emailMessage.classList.remove("confirm");
                    checkObj.memberEmail = false;

                } else { // 중복 아님
                    emailMessage.innerText = "사용 가능한 이메일 입니다.";
                    emailMessage.classList.remove("error");
                    emailMessage.classList.add("confirm");
                    
                    checkObj.memberEmail = true; // 유효하다 기록된다
                }


            }, 

            error : function() {
                // 비동기 통신(ajax)중 오류가 발생한 경우
                console.log("에러 발생");
            }


        });

    } else {
        emailMessage.innerText = "이메일 형식이 유효하지 않습니다.";
        emailMessage.classList.add("error");
        emailMessage.classList.remove("confirm");

        checkObj.memberEmail = false; // 유효하지 않다. 기록 안할거임
    } 

});




// 인증번호 보내기
const sendBtn = document.getElementById("sendBtn");
const cMessage = document.getElementById("cMessage");

// 타이머에 사용될 변수
let min = 4;
let sec = 59;
let checkInterval; // setInterval을 저장할 변수



sendBtn.addEventListener("click", function() {

     if(checkObj.memberEmail) { // 유효한 이메일이 작성되어 있을 경우에만 메일 보내기
        $.ajax({
            url: "sendEmail",
            data: {"inputEmail" : memberEmail.value},
            success : function(result) {
                console.log("이메일 발송 성공");
                console.log(result);

                // 인증 버튼이 클릭되어 정상적으로 메일이 보내졌음을 
                checkObj.sendEmail = true;
                
            },
            error : function() {
                console.log("이메일 발송 실패");
            }


        });

        // Mail 발송 Ajax 코드는 동작이 느림... 통신해야되서...
        // -> 메일은 메일대로 보내지고, 타이머는 버튼이 클릭되자 마자 바로 실행
        // --> ajax 코드가 비동기이기 때문에 메일이 보내지는 것을 기다리지 않고
        //      바로 수행된다!!!!!

        // 5분 타이머
        // setInterval(함수, 지연시간): 지연시간이 지난 후 함수를 수행(반복)
        
        cMessage.innerText = "5:00"; // 초기값 5분~ㅋ
        
        min = 4;
        sec = 59;
        cMessage.classList.remove("confirm");
        cMessage.classList.remove("error");

        // 변수에 저장해야 해당 함수를 멈출 수 있음.
        checkInterval = setInterval(function(){
            if(sec < 10) sec = "0" + sec;
            cMessage.innerText = min + ":" + sec;

            if(Number(sec) === 0) {
                min--;
                sec = 59;
            } else {
                sec--;
            }

            if(min === -1) {
                cMessage.classList.add("error");
                cMessage.innerText = "인증번호가 만료되었습니다.";

                clearInterval(checkInterval); // setInterval 함수 반복을 지움

            }
            


        }, 1000); // 1초 지연 후 수행
        
        alert("인증번호가 발송되었습니다. 이메일을 확인해주세요.");


     }
});

// 인증번호 확인 클릭시 동작
const cNumber = document.getElementById("cNumber");
const cBtn = document.getElementById("cBtn");
// + cMessage, memberEmail 요소도 사용

cBtn.addEventListener("click", function() {

    // 1. 인증번호 받기 버튼이 클릭되어 이메일이 발송되었는지 확인
    if(checkObj.sendEmail) {
        
        // 2. 입력된 인증번호가 6자리가 맞는지 확인
        if(cNumber.value.length == 6) {
            // ajax
            $.ajax({
                url: "checkNumber",
                data: {"cNumber" : cNumber.value,
                        "inputEmail" : memberEmail.value},
                
                success : function(result) {
                    console.log(result);
                
                    // 1: 인증번호 일치 o, 시간도 o
                    // 2: 인증번호 일치 o, 시간이 지남
                    // 3: 인증번호 불일치
                    if(result == 1) {
                        clearInterval(checkInterval); // 타이머 멈춤
                        cMessage.innerText = "인증되었습니다.";
                        cMessage.classList.add("confirm");
                        cMessage.classList.remove("error");
                        checkObj.checkNumber = true;

                    } else if(result == 2) {
                        alert("만료된 인증 번호 입니다.");

                    } else {
                        alert("인증번호가 일치하지 않습니다.")
                    }

                },
                error : function() {
                    console.log("이메일 인증 실패");
                }
            }); 
        } else {
            alert("인증번호를 정확하게 입력해주세요.");
            cNumber.focus();
        }
    } else {
        alert("인증번호 받기 버튼을 먼저 클릭해주세요!");
    }

});


const memberPw = document.getElementById("memberPw");
const memberPwConfirm = document.getElementById("memberPwConfirm");
const pwMessage = document.getElementById("pwMessage");

memberPw.addEventListener("keyup", function() {
    
    if(memberPw.value.trim().length == 0) {
        pwMessage.innerText = "영어, 숫자, 특수문자(!,@,#,-,_) 6~30글자 사이로 작성해주세요.";
        pwMessage.classList.remove("confirm", "error");
    
    }

    const regEx = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+={}|[\]\\:;'"<>,.?/])[A-Za-z\d!@#$%^&*()_+={}|[\]\\:;'"<>,.?/]{6,30}$/;
    
    if(regEx.test(memberPw.value)) {
        if(memberPw.value == memberPwConfirm.value) {
            pwMessage.innerText = "비밀번호가 일치합니다.";
            pwMessage.classList.add("confirm");
            pwMessage.classList.remove("error");   
            checkObj.memberPw = true;
            checkObj.memberPwConfirm = true;
        } else {
            pwMessage.innerText = "비밀번호가 일치하지 않습니다.";
            pwMessage.classList.add("error");
            pwMessage.classList.remove("confirm");
        }
    } else {
        pwMessage.innerText = "양식에 맞게 입력해주세요."
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");
    } 
    
});

memberPwConfirm.addEventListener("keyup", function() {
    
    if(memberPwConfirm.value.trim().length == 0) {
        pwMessage.innerText = "영어, 숫자, 특수문자(!,@,#,-,_) 6~30글자 사이로 작성해주세요.";
        pwMessage.classList.remove("confirm", "error");
    
    }


    const regEx = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+={}|[\]\\:;'"<>,.?/])[A-Za-z\d!@#$%^&*()_+={}|[\]\\:;'"<>,.?/]{6,30}$/;

    if(regEx.test(memberPwConfirm.value)) {
        if(memberPw.value == memberPwConfirm.value) {
            pwMessage.innerText = "비밀번호가 일치합니다.";
            pwMessage.classList.add("confirm");
            pwMessage.classList.remove("error");  
            checkObj.memberPw = true;
            checkObj.memberPwConfirm = true;
        } else {
            pwMessage.innerText = "비밀번호가 일치하지 않습니다.";
            pwMessage.classList.add("error");
            pwMessage.classList.remove("confirm");
        }
    } else {
        pwMessage.innerText = "양식에 맞게 입력해주세요."
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");
    } 
    
});

const memberNickname = document.getElementById("memberNickname");
const nicknameMessage = document.getElementById("nicknameMessage");

memberNickname.addEventListener("input", function() {
    const regEx = /^[a-zA-Z0-9가-힣]{2,10}$/;

    if(regEx.test(memberNickname.value)) {
        nicknameMessage.innerText = "합격ㅋ"
        nicknameMessage.classList.add("confirm");
        nicknameMessage.classList.remove("error");   
        checkObj.memberNickname = true;
    } else {
        nicknameMessage.innerText = "불합격ㅋㅋ"
        nicknameMessage.classList.add("error");
        nicknameMessage.classList.remove("confirm");
    }
});



const memberTel = document.getElementById("memberTel");
const telMessage = document.getElementById("telMessage");

memberTel.addEventListener("input", function() {
    const regEx = /^[0][0-9]{1,2}[0-9]{3,4}[0-9]{4}$/;
    if(regEx.test(memberTel.value)) {
        telMessage.innerText = "올바르게 작성되었습니다."
        telMessage.classList.add("confirm");
        telMessage.classList.remove("error"); 
        checkObj.memberTel = true;
    } else {
        telMessage.innerText = "형식이 잘못되었습니다."
        telMessage.classList.add("error");
        telMessage.classList.remove("confirm");
    }
});



const memberAddress = document.getElementsByClassName("memberAddress");


document.getElementById("signUp-btn").addEventListener("click", function() {
    if(checkObj.emberEmail && checkObj.memberPw && checkObj.memberNickname && checkObj.memberTel){
    $.ajax({
        url: "/member/signUp",
        data: {"memberEmail" : memberEmail.value,
                "memberPw" : memberPw.value,
                "memberNickname" : memberNickname.value,
                "memberTel" : memberTel
        
        },
        type:'POST',
        dataType: 'json',
    
        success:function(result){
      
            alert("회원가입 축하해");
            return true;
              
            
        
        },
        error:function(result){
            console.log("실패")
        }
    })
    }



});
    



 
  
 

