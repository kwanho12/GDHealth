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
						<h3 class="pt-3 font-weight-bold">Login</h3>
					</div>
					<div class="panel-body p-3">
						<form action="/customer/login" method="post">
							<div class="form-group py-2">
								<div class="input-field">
									<span class="far fa-user p-2"></span> <input type="text"
										placeholder="Username" id="customerId" name="customerId"
										required>
								</div>
							</div>
							<div class="form-group py-1 pb-2">
								<div class="input-field">
									<span class="fas fa-lock px-2"></span> <input type="password"
										placeholder="Password" value="jinkwanho12#" id="customerPw" name="customerPw"
										required>
									<button type="button" class="btn bg-white text-muted toggle-password">
										<span class="far fa-eye-slash"></span>
									</button>
								</div>
							</div>
							<div class="form-inline">
								<input type="checkbox" id="checkId" name="checkId"> <label
									for="remember" class="text-muted">Remember me</label> <a
									href="/customer/findPw" id="forgot" class="font-weight-bold">비밀번호 찾기</a>
							</div>
							<button type="submit" class="btn btn-primary btn-block mt-3">Login</button>
							<div class="text-center pt-4 text-muted">
								계정이 없으신가요? <a href="/customer/signup">회원가입</a>
							</div>
						</form>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	<script>
    $(document).ready(function(){
        // 저장된 쿠키값을 가져와서 ID와 비밀번호 칸에 넣어준다. 없으면 공백으로 들어감.
        var key = getCookie("key");
        var password = getCookie("password");
        $("#customerId").val(key);
    	$('#customerId').val("test1");
        $("#customerPw").val(password);
    	$('#customerPw').val("jinkwanho12#");


        // 그 전에 ID와 비밀번호를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID와 비밀번호가 표시된 상태라면,
        if($("#customerId").val() != ""){ 
            $("#checkId").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
        }

        $("#checkId").change(function(){ // 체크박스에 변화가 있다면,
            if($("#checkId").is(":checked")){ // ID 저장하기 체크했을 때,
                setCookie("key", $("#customerId").val(), 7); // 7일 동안 쿠키 보관
                setCookie("password", $("#customerPw").val(), 7); // 7일 동안 쿠키 보관
            } else { // ID 저장하기 체크 해제 시,
                deleteCookie("key");
                deleteCookie("password");
            }
        });

        // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
        $("#customerId").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
            if($("#checkId").is(":checked")){ // ID 저장하기를 체크한 상태라면,
                setCookie("key", $("#customerId").val(), 7); // 7일 동안 쿠키 보관
            }
        });

        // 비밀번호 저장하기를 체크한 상태에서 비밀번호를 입력하는 경우, 이럴 때도 쿠키 저장.
        $("#customerPw").keyup(function(){ // 비밀번호 입력 칸에 비밀번호를 입력할 때,
            if($("#checkId").is(":checked")){ // ID 저장하기를 체크한 상태라면,
                setCookie("password", $("#customerPw").val(), 7); // 7일 동안 쿠키 보관
            }
        });

        // 비밀번호 숨기기/보이기 토글 기능
        $(".toggle-password").click(function() {
            var passwordField = $("#customerPw");
            var passwordFieldType = passwordField.attr('type');
            if (passwordFieldType === 'password') {
                passwordField.attr('type', 'text');
            } else {
                passwordField.attr('type', 'password');
            }
        });
        // 쿠키 저장하기 
        // setCookie => saveid함수에서 넘겨준 시간이 현재시간과 비교해서 쿠키를 생성하고 지워주는 역할
        function setCookie(cookieName, value, exdays) {
            var exdate = new Date();
            exdate.setDate(exdate.getDate() + exdays);
            var cookieValue = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toGMTString());
            document.cookie = cookieName + "=" + cookieValue;
        }

        // 쿠키 삭제
        function deleteCookie(cookieName) {
            var expireDate = new Date();
            expireDate.setDate(expireDate.getDate() - 1);
            document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
        }

        // 쿠키 가져오기
        function getCookie(cookieName) {
            cookieName = cookieName + '=';
            var cookieData = document.cookie;
            var start = cookieData.indexOf(cookieName);
            var cookieValue = '';
            if (start != -1) { // 쿠키가 존재하면
                start += cookieName.length;
                var end = cookieData.indexOf(';', start);
                if (end == -1) // 쿠키 값의 마지막 위치 인덱스 번호 설정 
                    end = cookieData.length;
                console.log("end 위치  : " + end);
                cookieValue = cookieData.substring(start, end);
            }
            return unescape(cookieValue);
        }
    });
</script>
</body>
</html>