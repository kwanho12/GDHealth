<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset='utf-8'>
<meta name='viewport' content='width=device-width, initial-scale=1'>
<title>GD HEALTH</title>
<link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css' rel='stylesheet'>
<link href='https://use.fontawesome.com/releases/v5.7.2/css/all.css' rel='stylesheet'>
<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins&display=swap');
</style>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script type='text/javascript' src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
<script type='text/javascript' src='https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js'></script>
<script type='text/javascript' src='https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js'></script>
<link rel="stylesheet" href="/css/login.css">
</head>
<script>
	$(document).ready(function() {
		var msg = "${msg}";
		if (msg.trim() !== "") {
			alert(msg);
		}
	});
</script>
<body oncontextmenu='return false' class='snippet-body'>
	<div class="container">
		<div class="row">
			<div class="offset-md-2 col-lg-5 col-md-7 offset-lg-4 offset-md-3">
				<div class="panel border bg-white">
					<div class="panel-heading">
						<h3 class="pt-3 font-weight-bold">직원 로그인</h3>
					</div>
					<div class="panel-body p-3">
						<form action="/employee/login" method="post">
							<div class="form-group py-2">
								<div class="input-field">
									<span class="far fa-user p-2"></span> <input type="text"
										placeholder="Username" id="employeeId" name="employeeId"
										required>
								</div>
							</div>
							<div class="form-group py-1 pb-2">
								<div class="input-field">
									<span class="fas fa-lock px-2"></span> <input type="password"
										placeholder="Password" id="employeePw" name="employeePw"
										required>
									<button type="button" class="btn bg-white text-muted toggle-password">
										<span class="far fa-eye-slash"></span>
									</button>
								</div>
							</div>
							<button type="submit" class="btn btn-primary btn-block mt-3">Login</button>
							<a type="button" href="/customer/home" class="btn btn-secondary btn-sm btn-block mt-3">고객 홈으로</a>
							<div class="text-center pt-4 text-muted">
								<a href="#" id="lookAroundBranch">(둘러보기) 지점관리자 계정</a>
								<br>
								<a href="#"  id="lookAroundHeadoffice">(둘러보기) 본사관리자 계정</a>
							</div>
						</form>
					</div>
					
				</div>
			</div>
		</div>
	</div>
<script>
	$('#lookAroundHeadoffice').click(function (){
		$('#employeeId').val('headoffice');
		$('#employeePw').val('jinkwanho12#');
	});
	$('#lookAroundBranch').click(function (){
		$('#employeeId').val('gdhealth01');
		$('#employeePw').val('jinkwanho12#');
	})

</script>
</body>
</html>