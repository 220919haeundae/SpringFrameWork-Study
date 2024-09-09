<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MenuBar</title>
    <style>
        #top-area { display: flex; justify-content: space-around; padding: 1em;}

        a { text-decoration: none; color: black; }

        nav {margin:0;padding:0;}
        nav div {font-weight: bold; height: 50px; line-height: 50px; }
        nav div:hover, nav div.active { cursor: pointer;}
        nav div.active { background-color: #485ba6; color: white; }
    </style>
</head>
<body>
	<%
		String contextPath = request.getContextPath();
	%>
	
	<script>
		<c:if test="${ !empty alertMsg }">
			alert("${alertMsg}");
		</c:if>
		<c:remove var="alertMsg" scope="session"/>
	</script>
	
	
    <div id="top-area">
        <img src="https://khedu.co.kr/resources/images/main/logo.svg" alt="kh_logo" width="150" />
        <div></div>
        <!-- 로그인 전 보여질 화면 -->
        <c:choose>
	        <c:when test="${ empty loginUser }">
	        <div>
	            <a href="<%= contextPath %>/member/loginform">회원가입</a> &nbsp;|&nbsp;
	            <a href="#" data-bs-toggle="modal" data-bs-target="#loginModal">로그인</a>
	        </div>
			</c:when>
			<c:otherwise>
	        <!-- 로그인 후 보여질 화면 -->
	        <div>
	            <label>${ loginUser.userName } 님 환영합니다.</label> &nbsp;&nbsp;
	            <a href="">마이페이지</a>
	            <a href="<%= contextPath %>/member/logout">로그아웃</a>        
	        </div>
	        </c:otherwise>
        </c:choose>
    </div>
    <hr>
    <nav class="container text-center">
        <div class="row">
            <div class="active col-3">HOME</div>
            <div class="col">공지사항</div>
            <div class="col">자유게시판</div>
            <div class="col">사진게시판</div>
        </div>
    </nav>
    <!-- 로그인 모달 -->
    <div class="modal fade" id="loginModal">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5">LOGIN</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="<%= contextPath %>/member/login" method="post">
                <div class="modal-body">
                    <div class="mb-3">
                    <label for="userId" class="col-form-label">ID:</label>
                    <input type="text" class="form-control" placeholder="Enter ID.." id="userId" name="userId" />
                    </div>
                    <div class="mb-3">
                    <label for="userPwd" class="col-form-label">PASSWORD:</label>
                    <input type="password" class="form-control" placeholder="Enter Password.." id="userPwd" name="userPwd" />
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">로그인</button>
                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">취소</button>
                </div>
            </form>
          </div>
        </div>
      </div>
      <script>
        window.onload = () => {
            const menuList = document.querySelectorAll("nav div");
            menuList.forEach((menu)=>{              
                menu.addEventListener('click', (ev)=>{           
                    removeActiveClass(menuList);         
                    ev.target.classList.add("active");
                });
            })
            
        }
        const removeActiveClass = (list) => {
            list.forEach((menu)=>{
                menu.classList.remove("active");
            })
        }
      </script>
</body>
</html>