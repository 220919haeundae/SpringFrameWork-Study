<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 상세보기</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    
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
        
        table {width:100%;}
        table * {margin:5px;}
    </style>    
</head>
<body>
    <%-- header --%>
    <jsp:include page="../common/header.jsp" />

    <div class="outer">
        <br><br>
        <div class="inner-area">
            <h2>게시글 상세보기</h2>
            <br>
            <a href="list" class="btn btn-secondary" style="float:right;">목록보기</a>
            <br><br>

            <table align="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">
                        ${b.boardTitle}
                    </td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>
                        ${b.boardWriter}
                    </td>
                    <th>작성일</th>
                    <td>
                        ${ b.createDate }
                    </td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
                    	<c:choose>
                    	<c:when test="${ !empty b.originName }">
                    		<a href="/spring/${ b.changeName }" download="${ b.originName }">${b.originName}</a>
                    	</c:when>
                    	<c:otherwise>
                    		첨부파일 없음
                    	</c:otherwise>
                    	</c:choose>
                        
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <p style="height:150px;">
                            ${b.boardContent}
                        </p>
                    </td>
                </tr>
            </table>
            <br>

            <div align="center">
                <!-- 작성자와 로그인한 계정이 동일한 경우만 표시 -->
                <c:if test="${ loginUser.userId == b.boardWriter }">
                <a class="btn btn-primary" onclick="postSubmit('update');">수정</a>
                <a class="btn btn-danger" onclick="postSubmit('delete');">삭제</a>
                </c:if>
            </div>
            <br><br>
			<form action="" method="post" id="postForm">
	            <input type="hidden" name="boardNo" value="${ b.boardNo }"/>
	        </form>

			<script>
			
				function postSubmit(type) {
					const postForm = document.getElementById('postForm');
					
					if(type = 'update') {
						postForm.action = 'updateForm';
					} else if(type = 'delete') {
						postForm.action = "delete";
					}
					postForm.submit();
				}
			</script>

            <table id="replyArea" class="table" align="center">
                <thead>
                    <tr>
                        <th colspan="2">
                            <textarea name="" id="content" cols="55" rows="2" class="form-control" style="resize: none;"></textarea>
                        </th>
                        <th style="vertical-align:middle;">
                            <button class="btn btn-secondary">등록</button>
                        </th>
                    </tr>
                    <tr>
                        <td colspan="3">댓글 (<span id="rcount">${rArr.size()}</span>)</td>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach var="r" items="${ rArr }">
                    <tr>
                        <th>${ r.replyWriter }</th>
                        <td>${ r.replyContent }</td>
                        <td>${ r.createDate }</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>     
            <br><br>
        </div>


    </div>

    <%-- footer --%>
    <jsp:include page="../common/footer.jsp" />    
</body>
</html>