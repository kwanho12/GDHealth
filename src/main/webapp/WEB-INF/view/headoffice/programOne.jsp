<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html class="no-js" lang="zxx">

<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>프로그램 상세</title>
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


		<div class="blog-details-area mg-b-15">
			<div class="container-fluid">
				<div class="row" style="margin-top: 20px;">
					<div class="col-lg-3 col-md-2 col-sm-1 col-xs-12"></div>
					<div class="col-lg-6 col-md-8 col-sm-10 col-xs-12">

						<div class="blog-details-inner">
							<div class="row">
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
									<div class="latest-blog-single blog-single-full-view">
										<div class="blog-image col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
											<img src="${pageContext.request.contextPath}/upload/program/${programOne.filename}" alt="${pageContext.request.contextPath}/noImg" style="width: 450px; height: 450px;" />
										</div>

										<div class="blog-details blog-sig-details">
											<div class="details-blog-dt blog-sig-details-dt courses-info row" style="text-align: left;">
												<div class="col-lg-12">
													<span><i class="fa fa-heart"></i> <b>수용 인원 :</b> ${programOne.maxCustomer}</span>
												</div>
												<div class="col-lg-12">
													<span><i class="fa fa-user"></i> <b>담당 본사 직원 :</b> ${programOne.empName}</span>
												</div>
												<div class="col-lg-12">
													<span><i class="fa fa-heart"></i> <b>개설 날짜 :</b> ${programOne.programDate}</span>
												</div>
												<div class="col-lg-12">
													<c:if test="${programOne.active == 'Y'}">
														<span><i class="fa fa-heart"></i> <b>상태 :</b> 활성화</span>
													</c:if>
													<c:if test="${programOne.active == 'N'}">
														<span><i class="fa fa-heart"></i> <b>상태 :</b> 비활성화</span>
													</c:if>
												</div>

											</div>
											<div style="text-align: left;">
												<h1>${programOne.programName}</h1>
												<p>${programOne.programDetail}</p>
											</div>
										</div>

										<div style="text-align: center;">
											<a href="${pageContext.request.contextPath}/headoffice/program/update/${programOne.programNo}/${programOne.programDate}" class="btn btn-primary">수정하기</a>
											<c:if test="${programOne.active == 'Y'}">
												<button type="button" id="deactivateBtn" class="btn btn-primary">비활성화하기</button>
											</c:if>
											<c:if test="${programOne.active == 'N'}">
												<button type="button" id="activateBtn" class="btn btn-primary">활성화하기</button>
											</c:if>
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
					<div class="col-lg-3 col-md-2 col-sm-1 col-xs-12"></div>
				</div>
			</div>
		</div>

	</div>

	<!-- jquery
		============================================ -->
	<script src="/admin/js/vendor/jquery-1.12.4.min.js"></script>
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

</body>

<script>
	$('#deactivateBtn')
			.click(
					function() {
						let result = confirm('비활성화 하시겠습니까?');

						if (result) {
							location.href = '${pageContext.request.contextPath}/headoffice/program/deactivate/${programOne.programNo}/${programOne.programDate}';
						}
					});

	$('#activateBtn')
			.click(
					function() {
						let result = confirm('활성화 하시겠습니까?');

						if (result) {
							location.href = '${pageContext.request.contextPath}/headoffice/program/activate/${programOne.programNo}/${programOne.programDate}';
						}
					})
</script>

</html>