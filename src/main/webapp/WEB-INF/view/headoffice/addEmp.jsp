<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html class="no-js" lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>직원 추가</title>
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
<!-- forms CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/form/all-type-forms.css">
<!-- dropzone CSS
		============================================ -->
<link rel="stylesheet" href="/admin/css/dropzone/dropzone.css">
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

		<!-- 직원 추가 화면 start-->
		<div class="single-pro-review-area mt-t-30 mg-b-15">
			<div class="container-fluid">
				<div class="row" style="margin-top: 20px;">
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 mt-3"></div>
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 mt-3">

						<div class="product-payment-inner-st">
							<h3 style="font-size: 20px;">직원 추가하기</h3>
							<div id="myTabContent" class="tab-content custom-product-edit">
								<div class="product-tab-list tab-pane fade active in" id="description">
									<div class="row">
										<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
											<div class="review-content-section">
												<div id="dropzone1" class="pro-ad">
													<form action="${pageContext.request.contextPath}/headoffice/emp/add" class="dropzone dropzone-custom needsclick add-professors" id="insertForm" method="post" enctype="multipart/form-data">
														<div class="row">
															<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
																<div class="form-group">
																	<input name="employeeId" type="text" class="form-control" id="employeeId" placeholder="ID" maxlength="15">
																</div>
															</div>
															<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
																<div class="form-group">
																	<button type="button" class="btn form-control" id="idCheck">중복체크</button>
																</div>
															</div>
															<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
																<div class="form-group">
																	<input name="employeePw" id="employeePw" type="password" class="form-control" placeholder="비밀번호" maxlength="15">
																</div>
																<div class="form-group">
																	<input type="password" class="form-control" id="pwCheck" placeholder="비밀번호 확인" maxlength="15">
																</div>
																<div class="form-group">
																	<input name="employeeName" id="employeeName" type="text" class="form-control" placeholder="이름" maxlength="20">
																</div>
																<div class="form-group">
																	<input name="employeePhone" id="employeePhone" type="text" class="form-control" placeholder="휴대폰 번호      ex) 010-1234-5678" maxlength="13">
																</div>
																<div class="form-group">
																	<input name="employeeEmail" id="employeeEmail" type="text" class="form-control" placeholder="이메일      ex) goodee@naver.com" maxlength="30">
																</div>

																<div class="form-group">
																	<select name="employeeGender" id="employeeGender" class="form-control">
																		<option value="0" selected>성별</option>
																		<option value="m">남</option>
																		<option value="f">여</option>
																	</select>
																</div>
																<div class="form-group">
																	<select name="branchNo" id="branchNo" class="form-control">
																		<option value="0" selected>지점</option>
																	</select>
																</div>
																<div class="form-group">
																	<select name="employeePosition" id="employeePosition" class="form-control">
																		<option value="0" selected>직책</option>
																		<option value="trainer">트레이너</option>
																		<option value="branch_manager">지점 관리자</option>
																		<option value="head_office_manager">본사 관리자</option>
																	</select>
																</div>

																<div class="form-group">
																	<input name="employeeFile" id="employeeFile" type="file" class="form-control" accept="image/*">
																</div>

															</div>

														</div>
														<div class="row">
															<div class="col-lg-12">
																<div class="payment-adress">
																	<button type="button" class="btn btn-primary waves-effect waves-light" id="insertBtn">추가하기</button>
																</div>
															</div>
														</div>
													</form>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 mt-3"></div>
				</div>
			</div>
		</div>
		<!-- 직원 추가 화면 start-->

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
	$('#employeeId').focus();

	// 지점 목록 DB 테이블 column 가져 오기
	$.ajax({
		url : '${pageContext.request.contextPath}/headoffice/emp/branchList',
		type : 'get',
		success : function(result) {

			$(result).each(
					function(index, item) {
						$('#branchNo').append(
								'<option value="' + Number(index + 1) + '">'
										+ item + '</option>')
					});

		}
	});

	let isIdCheck = false;
	// 중복체크를 하고 난 뒤 아이디 입력란에 사용 가능한 아이디를 지우고 새로운 아이디를 입력했을 경우에 대처
	$('#employeeId').keydown(function() {
		isIdCheck = false;
	});

	// 중복체크 버튼
	$('#idCheck')
			.click(
					function() {

						// 아이디 정규식 패턴 : 영어 소문자로 시작하는 영어 소문자 + 숫자 조합 6~15자리
						let idReg = /^[a-z]+[a-z0-9]{5,14}$/g;

						let employeeId = $('#employeeId').val();
						if (employeeId != '') {
							$
									.ajax({
										url : '${pageContext.request.contextPath}/headoffice/emp/checkExistingId',
										method : 'post',
										data : {
											employeeId : employeeId
										},
										success : function(result) {
											if (result == 1) {
												alert('이미 사용중인 아이디입니다. 아이디를 다시 입력해주세요.');
												$('#employeeId').focus();
											} else if (!idReg.test($(
													'#employeeId').val())) {
												alert('아이디는 영어 소문자로 시작하고 영어 소문자+숫자 조합의 6~15자로 이루어져야 합니다.');
												$('#employeeId').focus();
											} else {
												isIdCheck = true;
												alert('사용 가능한 아이디입니다.');
												$('#employeePw').focus();
											}
										},
										error : function(err) {
											console.log(err);
										}
									});
						} else {
							alert('아이디를 입력하세요.');
							$('#employeeId').focus();
						}
					});

	// 이미지 파일의 형식을 검사하는 함수
	function isImageFile(file) {

		var validImageTypes = [ 'image/jpg', 'image/jpeg', 'image/png',
				'image/gif', 'image/webp' ];
		return validImageTypes.includes(file.type);
	}

	// 회원가입 버튼
	$('#insertBtn')
			.click(
					function() {

						if (isIdCheck == false) {
							alert('ID 중복체크를 하세요.');
							$('#employeeId').focus();
							return;
						}

						// 비밀번호 정규식 패턴 : 영문 숫자 특수기호 조합 6~15자리 
						let passwordReg = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{6,15}$/;

						if ($('#employeePw').val().length == 0) {
							// 비밀번호 창에 아무것도 입력하지 않았을 때
							alert('비밀번호를 입력하세요.');
							$('#memberPw').focus();
							return;
						} else if ($('#employeePw').val() != $('#pwCheck')
								.val()) {
							// 비밀번호 일치 확인
							alert('비밀번호가 일치하지 않습니다.');
							$('#pwCheck').focus();
							return;
						} else if (!passwordReg.test($('#employeePw').val())) {
							// 숫자와 영어를 혼용하지 않았을 때
							alert("비밀번호는 숫자와 영문자, 특수문자를 혼용하여 6자 이상 입력해야 합니다.");
							$('#employeePw').focus();
							return;
						}

						if ($('#employeeName').val().trim() == '') {
							alert('이름을 입력하세요.');
							$('#employeeName').val('');
							$('#employeeName').focus();
							return;
						}

						if ($('#employeeName').val().length < 2) {
							alert('이름은 최소 2자 이상 입력하여야 합니다.');
							$('#employeeName').focus();
							return;
						}

						// 이름 정규식 패턴 : 한글, 영문, 띄어쓰기만 허용 가능
						let nameReg = /^[a-zA-Zㄱ-힣\s]+$/;

						if (!nameReg.test($('#employeeName').val())) {
							alert('이름은 한글과 영문만 입력 가능합니다.');
							$('#employeeName').val('');
							$('#employeeName').focus();
							return;
						}

						if ($('#employeePhone').val().trim() == '') {
							alert('휴대폰 번호를 입력하세요.');
							$('#employeePhone').val('');
							$('#employeePhone').focus();
							return;
						}

						// 휴대폰 번호 형식 정규식 패턴
						let phoneReg = /^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/;

						if (!phoneReg.test($('#employeePhone').val())) {
							alert('올바른 휴대폰 번호의 형식을 입력하세요.');
							$('#employeePhone').val('');
							$('#employeePhone').focus();
							return;
						}

						if ($('#employeeEmail').val().trim() == '') {
							alert('이메일을 입력하세요.');
							$('#employeeEmail').val('');
							$('#employeeEmail').focus();
							return;
						}

						// 이메일 형식 정규식 패턴
						let emailReg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

						if (!emailReg.test($('#employeeEmail').val())) {
							alert('올바른 이메일의 형식을 입력하세요.');
							$('#employeeEmail').val('');
							$('#employeeEmail').focus();
							return;
						}

						if ($('#employeeGender').val() == 0) {
							alert('성별을 선택하세요.');
							$('#employeeGender').focus();
							return;
						}

						if ($('#branchNo').val() == 0) {
							alert('지점을 선택하세요.');
							$('#branchNo').focus();
							return;
						}

						if ($('#employeePosition').val() == 0) {
							alert('직책을 선택하세요.');
							$('#employeePosition').focus();
							return;
						}

						if ($('#employeeFile').val().length == 0) {
							alert('직원 사진을 첨부하세요.');
							$('#employeeFile').focus();
							return;
						}

						var fileInput = $('#employeeFile')[0];
						var file = fileInput.files[0];

						if (!isImageFile(file)) {
							alert('사진은 이미지 파일만 첨부 가능합니다.');
							$('#employeeFile').focus();
							return;
						}

						alert('추가가 완료되었습니다.');
						$('#insertForm').submit();
					});
</script>

</html>