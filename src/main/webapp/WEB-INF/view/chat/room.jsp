<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Room</title>
<style>
* {
	margin: 0;
	padding: 0;
}

.container {
	width: 500px;
	margin: 0 auto;
	padding: 25px
}

.container h1 {
	text-align: left;
	padding: 5px 5px 5px 15px;
	color: #58ACFA;
	border-left: 3px solid #58ACFA;
	margin-bottom: 20px;
}

.roomContainer {
	background-color: #F6F6F6;
	width: 500px;
	height: 500px;
	overflow: auto;
}

.roomList {
	border: none;
}

.roomList th {
	border: 1px solid #58ACFA;
	background-color: #fff;
	color: #58ACFA;
}

.roomList td {
	border: 1px solid #58ACFA;
	background-color: #fff;
	text-align: left;
	color: #58ACFA;
}

.roomList .num {
	width: 75px;
	text-align: center;
}

.roomList .room {
	width: 350px;
}

.roomList .go {
	width: 71px;
	text-align: center;
}

button {
	background-color: #58ACFA;
	font-size: 14px;
	color: #000;
	border: 1px solid #000;
	border-radius: 5px;
	padding: 3px;
	margin: 3px;
}

.inputTable th {
	padding: 5px;
}

.inputTable input {
	width: 330px;
	height: 25px;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<div class="container">
		<h1>채팅방</h1>
		<div id="roomContainer" class="roomContainer">
			<table id="roomList" class="roomList">
				<tr>
					<th class='num'>번호</th>
					<th class='room'>ID</th>
					<th class='go'></th>
				</tr>
				<c:forEach var="m" items="${roomList}">
					<tr>
						<td class='num'>${m.roomNo}</td>
						<td class='room'>${m.customerId}</td>
						<td class='go'>
							<button type='button' onclick='location.href="/chat/headofficeRoom?customerId=${m.customerId}"'>참여</button>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>

<script type="text/javascript">
	
</script>
</html>