<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html class="no-js" lang="zxx">

<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>프로그램 추가</title>
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

		<!-- 프로그램 추가 화면 start-->
		<form id="insertForm" method="post" action="${pageContext.request.contextPath}/headoffice/program/addProgram" enctype="multipart/form-data">

			<div class="row" id="font" style="padding: 15px;">

				<div class="col-lg-3 col-md-2 col-sm-1 col-xs-12"></div>
				<div class="col-lg-6 col-md-8 col-sm-10 col-xs-12">
					<h3 style="margin-bottom: 20px;">프로그램 추가하기</h3>
					<div style="margin-bottom: 10px;">
						<label for="programName" class="form-label">프로그램 제목</label> <input type="text" class="form-control" id="programName" name="programName" placeholder="입력하기" maxlength="40">
					</div>
					<div>
						<label for="programDetail" class="form-label">프로그램 내용</label>
						<textarea name="programDetail" id="programDetail" class="form-control" rows="15" maxlength="1000" style="resize: none; margin-bottom: 10px;" placeholder="입력하기"></textarea>
					</div>

					<div class="row" style="margin-bottom: 10px;">
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
							<label for="programMaxCustomer" class="form-label">수용 인원</label> <input type="number" min="1" max="100" maxlength="3" oninput="maxLengthCheck(this)" class="form-control" id="programMaxCustomer" name="programMaxCustomer" placeholder="입력하기">
						</div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
							<label for="programFile" class="form-label">프로그램 이미지</label> <input type="file" class="form-control" id="programFile" name="programFile" accept="image/*">
						</div>
					</div>
					<div class="row" style="margin-bottom: 10px;">
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
							<div id="dateArea">
								<label for="programDate1" class="form-label">개설 날짜</label>
								<div style="display: flex">
									<input type="text" class="form-control" id="programDate1" name="programDates" class="programDates" placeholder="yyyy-mm-dd" style="width: 240px; background-color: white;" readonly>
									<button type="button" style="margin-left: 15px; margin-top: 5px; height: 30px; width: 30px;" id="plusBtn">+</button>
								</div>
							</div>
						</div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12"></div>
					</div>

					<div style="text-align: center;">
						<button type="button" class="btn btn-primary" id="insertBtn" style="margin-top: 15px;">추가하기</button>
					</div>
				</div>

				<div class="col-lg-3 col-md-2 col-sm-1 col-xs-12"></div>
			</div>
		</form>
		<!-- 프로그램 추가 화면 end-->

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
	$('#programName').focus();

	// 숫자 입력시 글자 수 제한
	function maxLengthCheck(object) {
		if (object.value.length > object.maxLength) {
			object.value = object.value.slice(0, object.maxLength);
		}
	}

	let counter = 2;

	// +버튼 클릭시 개설 날짜 input 태그 증가
	$(document)
			.on(
					'click',
					'#plusBtn',
					function() {

						let newDatepickerId = 'programDate' + counter;

						let newDatepickerInput = $(
								'<input type="text" class="form-control" name="programDates" class="programDates" placeholder="yyyy-mm-dd" style="width:240px; background-color:white;" readonly>')
								.attr('id', newDatepickerId);
						let minusBtn = $('<button type="button" style="margin-left:15px; margin-top:5px; height:30px; width:30px;" class="minusBtn">-</button>');

						// 동적으로 생성된 input과 button을 dateHtml에 추가
						let dateHtml = $('<div style="display:flex;"></div>');
						dateHtml.append(newDatepickerInput);
						dateHtml.append(minusBtn);

						$('#dateArea').append(dateHtml);

						// 달력 API 초기화
						$('#' + newDatepickerId).datepicker(
								{
									dateFormat : 'yy-mm-dd',
									dayNamesMin : [ "일", "월", "화", "수", "목",
											"금", "토" ],
									monthNames : [ "1월", "2월", "3월", "4월",
											"5월", "6월", "7월", "8월", "9월",
											"10월", "11월", "12월" ],
									showMonthAfterYear : true,
									changeYear : true,
									changeMonth : true,
									monthNamesShort : [ '1월', '2월', '3월', '4월',
											'5월', '6월', '7월', '8월', '9월',
											'10월', '11월', '12월' ],
									yearRange : 'c-1:c+10',
									showButtonPanel : true,
									currentText : '오늘 날짜',
									closeText : '닫기',
									minDate : 0
								});

						// -버튼 클릭시 해당 개설 날짜 input 태그 삭제
						$(document).on('click', '.minusBtn', function() {
							$(this).prev('input').remove(); // 이전에 추가된 input 태그 삭제
							$(this).remove(); // 클릭한 - 버튼 삭제
						});

						counter++;
					});

	// 달력 API
	$(function() {
		$('#programDate1').datepicker(
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

	// 이미지 파일의 형식을 검사하는 함수
	function isImageFile(file) {

		var validImageTypes = [ 'image/jpg', 'image/jpeg', 'image/png',
				'image/gif', 'image/webp' ];
		return validImageTypes.includes(file.type);
	}

	// 날짜 형식 유효성 검증 함수
	function validateDateFormat(input) {

		// yyyy-mm-dd 형식의 정규식
		let dateFormat = /^\d{4}-\d{2}-\d{2}$/;

		if (!dateFormat.test(input.val())) {
			return false;
		} else {
			return true;
		}

	}

	// 선택한 날짜가 오늘 혹은 그 이후인지 검증하는 함수
	function validateFuture(input) {

		console.log(input);

		let today = new Date();
		let validDate = new Date(input);

		return validDate >= today;
	}

	// 선택한 개설 날짜 중에 중복된 날짜가 있는지 검증하는 함수
	function hasDuplicates(array) {
		return (new Set(array)).size !== array.length;
	}

	$(document)
			.on(
					'click',
					'#insertBtn',
					function() {

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

						if ($('#programMaxCustomer').val() == '') {
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

						if ($('#programFile').val().length == 0) {
							alert('프로그램 사진을 첨부하세요.');
							$('#programFile').focus();
							return;
						}

						var fileInput = $('#programFile')[0];
						var file = fileInput.files[0];

						if (!isImageFile(file)) {
							alert('사진은 이미지 파일만 첨부 가능합니다.');
							$('#programFile').focus();
							return;
						}

						if ($('#programDate1').val().length == 0) {
							alert('개설 날짜를 입력하세요.');
							$('#programDate1').focus();
							return;
						}

						let isFormatValid = true;

						// id가 dateArea인 div태그 내의 모든 input 태그에 대해 날짜 형식 검증
						$('#dateArea input[type="text"]').each(function() {
							if (!validateDateFormat($(this))) {
								isFormatValid = false;
								return;
							}
						});

						if (!isFormatValid) {
							alert('형식이 올바르지 않은 개설 날짜 입력창이 있습니다. 날짜를 선택하거나 "-"를 눌러 입력창을 삭제하세요.');
							return;
						}

						let isFutureDateValid = true;

						// id가 dateArea인 div태그 내의 모든 input 태그에 대해 오늘 날짜 혹은 그 이후인지 검증
						$('#dateArea input[type="text"]').each(function() {
							if (!validateFuture($(this).val())) {
								isFutureDateValid = false;
								return;
							}
						});

						if (!isFutureDateValid) {
							alert('날짜는 오늘 혹은 그 이후로만 설정 가능합니다.');
							return;
						}

						// 추가하는 개설날짜 중에 중복된 개설날짜가 있는지 확인하는 함수
						let values = [];
						$('#dateArea input[type="text"]').each(function() {
							values.push($(this).val());
						});

						console.log('values : ' + values);

						if (hasDuplicates(values)) {
							alert('개설 날짜가 서로 동일하지 않게 입력하세요.');
							return;
						}

						// 선택한 개설 날짜가 DB에 이미 존재하는지 확인
						$
								.ajax({
									url : '${pageContext.request.contextPath}/headoffice/program/datesCheck',
									method : 'post',
									data : JSON.stringify(values),
									dataType : 'json', // 서버에서 받을 데이터의 타입 
									contentType : 'application/json', // 서버로 보내는 데이터의 타입
									success : function(result) {
										if (result == true) {
											alert('선택한 개설 날짜에 이미 다른 프로그램이 등록되어 있습니다. 날짜를 변경해주세요.')
											return;
										} else {
											alert('추가 완료되었습니다.');
											$('#insertForm').submit();
										}

									},
									error : function(err) {
										console.log('datesCheck 오류');
										console.log(err);
									}
								});

					});
</script>

</html>