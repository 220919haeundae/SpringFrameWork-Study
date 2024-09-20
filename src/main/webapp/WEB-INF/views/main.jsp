<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>
</head>
<body>
	<jsp:include page="common/header.jsp"></jsp:include>

	<div class="outer">
		<div class="inner-area">
			<table id="boardList" class="table table-hover" align="center">
				<thead>
					<th>글번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>조회수</th>
					<th>작성일</th>
					<th>첨부파일</th>
				</thead>
				
				<tbody></tbody>
			</table>
		</div>
	</div>

	<script>
		$(function() {
			boardTopList();
		});
		
		function boardTopList() {
			$.ajax({
				url: 'board/top5',
				success: function(result) {
					let content = '';
					console.log(result);
					for(let r of result) {
						content += 	'<tr>'
									+ '<td>' + r.boardNo + '</td>'
									+ '<td>' + r.boardTitle + '</td>'
									+ '<td>' + r.boardWriter + '</td>' 
									+ '<td>' + r.count + '</td>'
									+ '<td>' + r.createDate + '</td>'
									+ '<td>';
									if(r.originName != null) {
										content += '■';
									}	
									content += '</td></tr>';
					}
					console.log(content);
					$("#boardList tbody").html(content);
				},
				error: function() {
					
				}
			});
		}
	</script>

	<jsp:include page="common/footer.jsp"></jsp:include>
</body>
</html>