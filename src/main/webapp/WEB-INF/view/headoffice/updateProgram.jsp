<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html class="no-js" lang="zxx">

<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>프로그램 수정</title>
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

<!-- 달력 API -->
<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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

									<form action="${pageContext.request.contextPath}/headoffice/program/update" id="updateForm" method="post" enctype="multipart/form-data">
										<input type="hidden" value="${programOne.programDate}" name="originDate"> 
										<input type="hidden" value="${programOne.programNo}" name="programNo"> 
										<input type="hidden" value="${programOne.filename}" name="filename">
										<div class="latest-blog-single blog-single-full-view">
											<div class="blog-details blog-sig-details">
												<div class="blog-details" style="text-align: center;">
													<h3 style="margin-bottom: 50px;">프로그램 수정하기</h3>
													<input type="file" name="programFile" id="programFile" style="display: block; margin: 0 auto;" accept="image/*">
												</div>

												<div class="details-blog-dt blog-sig-details-dt courses-info mobile-sm-d-n">
													<span> <b>수용 인원 :</b> <input type="number" min="1" max="100" maxlength="3" oninput="maxLengthCheck(this)" value="${programOne.maxCustomer}" name="programMaxCustomer" id="programMaxCustomer" style="width: 60px;">
													 <b style="margin-left: 20px;">개설 날짜 :</b> 
													<input type="text" value="${programOne.programDate}" name="programDate" id="programDate" style="width: 100px; background-color: white;" readonly>
													</span>
												</div>
												<div style="text-align: center;">
													<h1>
														<input type="text" value="${programOne.programName}" maxlength="40" id="programName" name="programName">
													</h1>
													<textarea style="resize: none; width: 70%;" rows="15" maxlength="1000" name="programDetail" id="programDetail">${programOne.programDetail}</textarea>
												</div>
											</div>
											<div style="text-align: center;">
												<button type="button" class="btn btn-primary" id="updateBtn">수정하기</button>
											</div>
										</div>
									</form>

								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-2 col-sm-1 col-xs-12"></div>
				</div>
			</div>
		</div>

	</div>

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
	$('#programMaxCustomer').focus();

	// 숫자 입력시 글자 수 제한
	function maxLengthCheck(object) {
		if (object.value.length > object.maxLength) {
			object.value = object.value.slice(0, object.maxLength);
		}
	}

	// 이미지 파일의 형식을 검사하는 함수
	function isImageFile(file) {

		var validImageTypes = [ 'image/jpg', 'image/jpeg', 'image/png',
				'image/gif', 'image/webp' ];
		return validImageTypes.includes(file.type);
	}

	// 달력 API
	$(function() {
		$("#programDate").datepicker(
				{
					dateFormat : 'yy-mm-dd',
					dayNamesMin : [ "일", "월", "화", "수", "목", "금", "토" ],
					monthNames : [ "1월", "2월", "3월", "4월", "5월", "6월", "7월",
							"8월", "9월", "10월", "11월", "12월" ],
					showMonthAfterYear : true,
					changeYear : true,
					changeMonth : true,
					monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월',
							'7월', '8월', '9월', '10월', '11월', '12월' ],
					yearRange : 'c-10:c+10',
					showButtonPanel : true,
					currentText : '오늘 날짜',
					closeText : '닫기',
					minDate : 0
				});
	});

	$('#updateBtn')
			.click(
					function() {

						let fileInput = $('#programFile')[0];
						let file = fileInput.files[0];

						if (file !== undefined) {

							if (!isImageFile(file)) {
								alert('사진은 이미지 파일만 첨부 가능합니다.');
								$('#programFile').focus();
								return;
							}

						}

						if ($('#programMaxCustomer').val().trim() == '') {
							alert('수용 인원을 입력하세요.');
							$('#programMaxCustomer').val('');
							$('#programMaxCustomer').focus();
							return;
						}

						if (Number($('#programMaxCustomer').val()) > 100
								|| Number($('#programMaxCustomer').val()) < 1) {
							alert('입력 가능한 수용 인원은 1~100명입니다.');
							$('#programMaxCustomer').val('');
							$('#programMaxCustomer').focus();
							return;
						}

						if ($('#programDate').val().length == 0) {
							alert('개설 날짜를 입력하세요.');
							$('#programDate').focus();
							return;
						}

						// yyyy-mm-dd 형식의 정규식
						let dateFormat = /^\d{4}-\d{2}-\d{2}$/;

						if (!dateFormat.test($('#programDate').val())) {
							alert('개설 날짜의 형식이 올바르지 않습니다.');
							$('#programDate').focus();
							return;
						}

						let today = new Date();
						let validateDate = new Date($('#programDate').val());

						if (today > validateDate) {
							alert('날짜는 오늘 혹은 그 이후로만 설정 가능합니다.');
							$('#programDate').focus();
							return;
						}

						if ($('#programName').val().trim() == '') {
							alert('프로그램 제목을 입력하세요.');
							$('#programName').val('');
							$('#programName').focus();
							return;
						}

						if ($('#programName').val().length < 3) {
							alert('프로그램 제목은 최소 3자 이상 입력하여야 합니다.');
							$('#programName').focus();
							return;
						}

						if ($('#programDetail').val().trim() == '') {
							alert('내용을 입력하세요.');
							$('#programDetail').val('');
							$('#programDetail').focus();
							return;
						}

						if ($('#programDetail').val().length < 5) {
							alert('내용은 최소 5자 이상 입력하여야 합니다.');
							$('#programDetail').focus();
							return;
						}

						let programDate = $('#programDate').val();

						// 선택한 개설 날짜가 DB에 이미 존재하는지 확인
						$
								.ajax({
									url : '${pageContext.request.contextPath}/headoffice/program/checkDateOne',
									method : 'post',
									data : {
										programDate : programDate
									},
									success : function(result) {
										// 결과값이 true이고 입력한 날짜가 원래 저장된 날짜와 다를 때
										if (result == true
												&& programDate != '${programOne.programDate}') {
											alert('선택한 개설 날짜가 이미 존재합니다. 날짜를 수정해주세요.')
											$('#programDate').focus();
											return false;
										} else {
											alert('수정 완료되었습니다.');
											$('#updateForm').submit();
										}
									},
									error : function(err) {
										console.log(err);
									}
								});

					});
</script>

</html>