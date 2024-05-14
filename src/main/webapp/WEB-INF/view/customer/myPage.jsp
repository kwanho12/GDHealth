<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- Latest compiled and minified CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<link rel="stylesheet" href="/css/myPage.css" type="text/css">
<title>GD HEALTH</title>
</head>
<body>
	
	<div class="wrap">
		<div class="greenContainer">
			<div>
				<div class="name">${info.customerName}</div>
			</div>
			<div class="modify"></div>
		</div>
		<div class="summaryContainer">
			<div class="item">
				<div class="number">${attendanceCount}</div>
				<div>출석횟수</div>
			</div>
			<div class="item">
				<div class="number">${reviewCount}</div>
				<div>리뷰</div>
			</div>
			<div class="item">
				<div class="number">${questionCount}</div>
				<div>문의</div>
			</div>
			<div class="item">
				<div class="number">${membership}</div>
				<div>멤버십</div>
			</div>
		</div>
		
		
		<div class="shippingStatusContainer">
			<div class="title">개인 정보</div>
			<div class="status">
			<div class="item">
				<div class="img">
				<img src="/upload/customer/${imgInfo.customerImgFileName}" alt="Profile Image">
			</div>
			</div>
			<div class="item">
			<div>
				<div class="center">아이디</div>
				<div class="Id">${info.customerId}</div>	
				<div class="center" style="margin-top: 80px;">이름</div>
				<div class="name">${info.customerName}</div>
			</div>
			</div>
			<div class="item">
			<div>
				<div class="center">생년월일</div>
				<div class="birth">${info.customerBirth}</div>	
				<div class="center" style="margin-top: 80px;">성별</div>
				<div class="gender">${info.customerGender}</div>
			</div>
			</div>
			<div class="item">
    		<div>
        		<div class="center">주소</div>
        		<div class="address">${info.customerAddress}</div>
        		<div class="row">
        			<div class="col-md-9"> 
        				<div class="center" style="margin-top: 80px;">휴대폰</div>
        				<div class="phone">${phoneCustom}</div>
        				
        			</div>
        			<div class="col-md-3 text-right">
        				<div class="center" style="margin-top: 80px;">이메일</div>
        				<div class="email" >${info.customerEmail}</div>
        			</div>
        		</div>
    		</div>
    		<div>
        		
    		</div>
			</div>
			<div class="item">
			
			</div>
	
			</div>
		</div>
		<div class="shippingStatusContainer">
			<div class="title">신체 정보</div>
			<div class="status">
			<div class="item">
			<div>
				<div class="number">${info.customerHeight}</div>
				<div class="center">신장</div>
			</div>
			</div>
			<div class="item">
			<div>
				<div class="number">${info.customerWeight}</div>
					<div class="center">체중</div>
			</div>
			</div>
			<div class="item">
			<div>
				<div class="number">${info.customerBmi}</div>
					<div class="center">BMI</div>
			</div>
			</div>
			<div class="item">
			<div>
				<div class="number"></div>
					<div class="center">업데이트</div>
					<div class="">${info.customerUpdatedate}</div>
			</div>
			</div>
			</div>
		</div>
		
		<div class="listContainer">
			<div class="item">
				<a href="/customer/updateMyPage"><div class="icon">ii &nbsp; 내 정보 수정</div></a> <a
					class="right" href="/customer/updateMyPage"><div>></div></a>
			</div>
			<div class="item">
				<a href="/customer/myreservation"><div class="icon">ii &nbsp; 스케줄</div></a> <a
					class="right" href="/customer/myreservation"><div>></div></a>
			</div>
			<div class="item">
				<a href="/review/reviewList"><div class="icon">ii &nbsp; 리뷰</div></a> <a
					class="right" href="/review/reviewList"><div>></div></a>
			</div>
			<div class="item">
				<a href="/question/questionList"><div class="icon">ii &nbsp; 문의사항</div></a> <a
					class="right" href="/question/questionList"><div>></div></a>
			</div>
			<div class="item">
				<a href="/customer/membershipList"><div class="icon">ii &nbsp; 멤버십 구매</div></a> <a
					class="right" href="/customer/membershipList"><div>></div></a>
			</div>
			<div class="item">
				<a href="#" class="chat">
					<div class="icon">ii &nbsp; 본사 직원과 채팅하기</div>
				</a> 
				<a href='#' class="right chat">
					<div>></div>
				</a>
			</div>
		</div>
		<div class="listContainer">
			
			<div class="item">
				<a href="/customer/delete"><div class="icon">ii &nbsp; 회원탈퇴</div></a> <a
					class="right" href="#"><div>></div></a>
			</div>
		</div>
		<div class="infoContainer">
			<div class="item">

				<div>
					<h3>
						<a href="/notice/noticeList" class="black">공지사항</a>
					</h3>
				</div>
			</div>

			<div class="item">

				<div>
					<h3>
						<a href="/customer/home" class="black">홈으로</a>
					</h3>
				</div>
			</div>
		</div>
	</div>
</body>

<script>

	// 채팅방 팝업창 생성
	$('.chat').click(function(event){
		event.preventDefault();
		url = '/chat/customerRoom';
		const options = 'top=10, left=10, width=600, height=700, status=no, menubar=no, toolbar=no';
		window.open(url, '_blank', options);
	})
	
</script>
</html>

