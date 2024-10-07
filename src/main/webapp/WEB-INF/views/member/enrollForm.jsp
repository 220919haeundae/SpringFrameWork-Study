<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EnrollForm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style>

        .outer {
            background-color: #e7e7e7;
            width: 80%;
            margin: auto;
        }
        .inner-area {
            border: 1px solid #000025;
            width: 80%;
            margin: auto;
            padding: 5% 15%;
            background: #e7ecf7;
        }
    </style>
</head>
<body>
    <%-- header --%>
    <%-- <jsp:include page="../common/header.jsp" /> // 변수를 가져오지 못함(contextPath) --%> 
    <%@ include file="../common/header.jsp" %>

    <div class="outer">
        <br><br>
        <div class="inner-area">
            
            <h2>회원가입</h2>
            <br>
            <form action="<%= contextPath %>/member/insert" method="post">

                <div class="form-group">
                    <label for="userId">* ID </label>
                    <input type="text" class="form-control mb-3" name="userId" id="userId" placeholder="Enter ID.." required>

                    <label for="userPwd">* Password </label>
                    <input type="password" class="form-control mb-3" name="userPwd" id="userPwd" placeholder="Enter Password.." required>

                    <label for="checkPwd">* Password Check </label>
                    <input type="password" class="form-control mb-3" id="checkPwd" placeholder="Enter Password.." required>
                    
                    <label for="userName">* Name </label>
                    <input type="text" class="form-control mb-3" name="userName" id="userName" placeholder="Enter Name.." required>

                    <label for="email"> &nbsp; Email </label>
                    <input type="email" class="form-control mb-3" name="email" id="email" placeholder="Enter Email..">  
                    <button type="button" id="mail-Check-Btn">이메일 인증하기</button>
                    
                    <label for="age"> &nbsp; 인증번호 </label>
                    <input type="number" class="form-control mb-3 mail-check-input" name="identifyNumber" id="identifyNumber" maxlength="6" disabled placeholder="인증번호를 입력하세요">
                    <div id=""></div>
                    <label for="age"> &nbsp; Age </label>
                    <input type="number" class="form-control mb-3" name="age" id="age" placeholder="Enter Age..">  
                    
                    <label for="phone"> &nbsp; Phone </label>
                    <input type="tel" class="form-control mb-3" name="phone" id="phone" placeholder="Enter Phone(-제외)..">  
                    
                    <label for="address"> &nbsp; Address </label>
                    <input type="text" class="form-control mb-3" name="address" id="address" placeholder="Enter Address..">

                    <label for=""> &nbsp; Gender</label> &nbsp;&nbsp;
                    <input type="radio" class="mb-3" name="gender" id="Male" value="M">
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" class="mb-3" name="gender" id="Female" value="F">
                    <label for="Female">여자</label><br>

                </div>
                <br>
                <div class="btns"  align="center">
                    <button type="submit" class="btn btn-primary">회원가입</button>
                    <button type="reset" class="btn btn-danger">초기화</button>
                </div>
            </form>
        </div>
        <br><br>
    </div>

    <%-- footer --%>
    <jsp:include page="../common/footer.jsp" />
    
    <script>
    $('#mail-Check-Btn').click(function() {
		const eamil = $('#email').val(); // 이메일 주소값 얻어오기!
		console.log('완성된 이메일 : ' + eamil); // 이메일 오는지 확인
		const checkInput = $('.mail-check-input') // 인증번호 입력하는곳 
		
		$.ajax({
			type : 'get',
			url : '<c:url value ="/member/mailCheck?email="/>'+eamil, // GET방식이라 Url 뒤에 email을 뭍힐수있다.
			success : function (data) {
				console.log("data : " +  data);
				checkInput.attr('disabled',false);
				code =data;
				alert('인증번호가 전송되었습니다.')
			}			
		}); // end ajax
	}); // end send eamil
    
	$('.mail-check-input').blur(function () {
		const inputCode = $(this).val();
		const $resultMsg = $('#mail-check-warn');
		
		if(inputCode === code){
			$resultMsg.html('인증번호가 일치합니다.');
			$resultMsg.css('color','green');
			$('#mail-Check-Btn').attr('disabled',true);
			$('#userEamil1').attr('readonly',true);
			$('#userEamil2').attr('readonly',true);
			$('#userEmail2').attr('onFocus', 'this.initialSelect = this.selectedIndex');
	         $('#userEmail2').attr('onChange', 'this.selectedIndex = this.initialSelect');
		}else{
			$resultMsg.html('인증번호가 불일치 합니다. 다시 확인해주세요!.');
			$resultMsg.css('color','red');
		}
	});
    </script>
</body>
</html>