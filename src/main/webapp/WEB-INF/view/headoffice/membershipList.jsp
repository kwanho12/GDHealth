<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html class="no-js" lang="zxx">

<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>회원권 추가하기</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!------------------- favicon start ------------------>
<link type="image/png" sizes="32x32" rel="icon" href="/admin/workoutFavicon.png">
<!------------------- favicon end -------------------->

<!-- Google Fonts
		============================================ -->
<link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,700,900" rel="stylesheet">
<!-- Bootstrap CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<!-- Bootstrap CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/font-awesome.min.css">
<!-- owl.carousel CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/owl.carousel.css">
<link rel="stylesheet" href="/admin/css/owl.theme.css">
<link rel="stylesheet" href="/admin/css/owl.transitions.css">
<!-- animate CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/animate.css">
<!-- normalize CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/normalize.css">
<!-- meanmenu icon CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/meanmenu.min.css">
<!-- main CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/main.css">
<!-- educate icon CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/educate-custon-icon.css">
<!-- morrisjs CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/morrisjs/morris.css">
<!-- mCustomScrollbar CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/scrollbar/jquery.mCustomScrollbar.min.css">
<!-- metisMenu CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/metisMenu/metisMenu.min.css">
<link rel="stylesheet" href="/admin/css/metisMenu/metisMenu-vertical.css">
<!-- calendar CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/calendar/fullcalendar.min.css">
<link rel="stylesheet" href="/admin/css/calendar/fullcalendar.print.min.css">
<!-- style CSS
		============================================ -->
<link rel="stylesheet" href="/admin/style.css">
<!-- responsive CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/responsive.css">
<!-- modernizr JS
		============================================ -->
<script src="/admin/js/vendor/modernizr-2.8.3.min.js"></script>

</head>

<body>

	<!---------------------- 사이드바 start --------------------->
	<c:import url="/WEB-INF/view/headoffice/include/sideBar.jsp"></c:import>
	<!---------------------- 사이드바 end ----------------------->

	<!-- Start Welcome area -->
	<div class="all-content-wrapper">
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<h1 style="color: #2E64FE; margin-top: 20px;">본사 페이지</h1>
				</div>
			</div>
		</div>
		<div class="header-advance-area">
			<!---------------------- 상단바 start --------------------->
			<c:import url="/WEB-INF/view/headoffice/include/topBar.jsp"></c:import>
			<!---------------------- 상단바 end ----------------------->
		</div>
	</div>


	<!-- 회원권 추가 화면 start-->
	<section>
		<div id="membershipList">
			<c:forEach var="m" items="${membershipList}">
				<div style="border-radius: 10px; border-style: solid; border-color: black; background-color: #F6F6F6; margin-left: 17%; margin-top: 1%; margin-bottom: 1%; margin-right: 1%;">
					<div style="margin: 1%;">
						<p>회원권 제목 : ${m.membershipName}</p>
						<p>회원권 개월수 : ${m.membershipMonth}개월</p>
						<p>회원권 가격 : ${m.membershipPrice}원</p>
					</div>

					<div style="margin: 1%;">
						<c:if test="${m.active eq 'N'}">
							<a href="#" onclick="activeY(${m.membershipNo}, '${m.active}')" class="btn btn-primary">비활성화</a>
						</c:if>

						<c:if test="${m.active eq 'Y'}">
							<a href="#" onclick="activeN(${m.membershipNo}, '${m.active}')" class="btn btn-primary">활성화</a>
						</c:if>
					</div>
				</div>


			</c:forEach>
		</div>
	</section>
	<!-- 회원권 추가 화면 end-->

	<!-- jquery
		============================================ -->
	<!--  <script src="/admin/js/vendor/jquery-1.12.4.min.js"></script>      -->

	<!-- bootstrap JS
		============================================ -->
	<script src="/admin/js/bootstrap.min.js"></script>
	<!-- wow JS
		============================================ -->
	<script src="/admin/js/wow.min.js"></script>
	<!-- price-slider JS
		============================================ -->
	<script src="/admin/js/jquery-price-slider.js"></script>
	<!-- meanmenu JS
		============================================ -->
	<script src="/admin/js/jquery.meanmenu.js"></script>
	<!-- owl.carousel JS
		============================================ -->
	<script src="/admin/js/owl.carousel.min.js"></script>
	<!-- sticky JS
		============================================ -->
	<script src="/admin/js/jquery.sticky.js"></script>
	<!-- scrollUp JS
		============================================ -->
	<script src="/admin/js/jquery.scrollUp.min.js"></script>
	<!-- mCustomScrollbar JS
		============================================ -->
	<script src="/admin/js/scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
	<script src="/admin/js/scrollbar/mCustomScrollbar-active.js"></script>
	<!-- metisMenu JS
		============================================ -->
	<script src="/admin/js/metisMenu/metisMenu.min.js"></script>
	<script src="/admin/js/metisMenu/metisMenu-active.js"></script>
	<!-- morrisjs JS
		============================================ -->
	<script src="/admin/js/sparkline/jquery.sparkline.min.js"></script>
	<script src="/admin/js/sparkline/jquery.charts-sparkline.js"></script>
	<!-- calendar JS
		============================================ -->
	<script src="/admin/js/calendar/moment.min.js"></script>
	<script src="/admin/js/calendar/fullcalendar.min.js"></script>
	<script src="/admin/js/calendar/fullcalendar-active.js"></script>
	<!-- maskedinput JS
		============================================ -->
	<script src="/admin/js/jquery.maskedinput.min.js"></script>
	<script src="/admin/js/masking-active.js"></script>
	<!-- datepicker JS
		============================================ -->
	<script src="/admin/js/datepicker/jquery-ui.min.js"></script>
	<script src="/admin/js/datepicker/datepicker-active.js"></script>
	<!-- form validate JS
		============================================ -->
	<script src="/admin/js/form-validation/jquery.form.min.js"></script>
	<script src="/admin/js/form-validation/jquery.validate.min.js"></script>
	<script src="/admin/js/form-validation/form-active.js"></script>
	<!-- tab JS
		============================================ -->
	<script src="/admin/js/tab.js"></script>
	<!-- plugins JS
		============================================ -->
	<script src="/admin/js/plugins.js"></script>
	<!-- main JS
		============================================ -->
	<script src="/admin/js/main.js"></script>



	<script>
    	function activeY(membershipNo,active){
            event.preventDefault();

			console.log("클릭");
			
    		$.ajax({
    			async : true,
    			url : '/headoffice/deleteMembership',
    			type : 'POST',
    			data : {membershipNo : membershipNo, active : active},
    			success : function(jsonData){
					console.log(jsonData, " <--jsonData");
					
    				$('#membershipList').load(location.href + " #membershipList");
    			}
    		});
    	};
    	
    	function activeN(membershipNo,active){
            event.preventDefault();

			console.log("클릭");
			
    		$.ajax({
    			async : true,
    			url : '/headoffice/deleteMembership',
    			type : 'POST',
    			data : {membershipNo : membershipNo, active : active},
    			success : function(jsonData){
					console.log(jsonData, " <--jsonData");
					
    				$('#membershipList').load(location.href + " #membershipList");
    			}
    		});
    	};
</script>
</body>
</html>