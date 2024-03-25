<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>

<!-- Start Left menu area -->
<div class="left-sidebar-pro">
	<nav id="sidebar" class="">
		<div class="sidebar-header">
			<h1 style="color: #2E64FE; margin-top: 15px;">본사 페이지</h1>
		</div>
		<div class="left-custom-menu-adp-wrap">
			<nav class="sidebar-nav left-sidebar-menu-pro">
				<ul class="metismenu" id="menu1">
					<li>
						<a class="has-arrow" href="#" aria-expanded="false"> <span class="educate-icon educate-professor icon-wrap"></span> <span class="mini-click-non">직원</span></a>
						<ul class="submenu-angle form-mini-nb-dp" aria-expanded="false">
							<li>
								<a href="${pageContext.request.contextPath}/headoffice/emp"><span class="mini-sub-pro">직원 목록</span></a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/headoffice/emp/add"><span class="mini-sub-pro">직원 추가</span></a>
							</li>
						</ul>
					</li>
					<li>
						<a class="has-arrow" href="#" aria-expanded="false"> <span class="educate-icon educate-professor icon-wrap"></span> <span class="mini-click-non">회원</span>
						</a>
						<ul class="submenu-angle form-mini-nb-dp" aria-expanded="false">
							<li>
								<a href="${pageContext.request.contextPath}/headoffice/customer"><span class="mini-sub-pro">회원 목록</span></a>
							</li>
						</ul>
					</li>
					<li>
						<a class="has-arrow" href="#" aria-expanded="false"> <span class="educate-icon educate-course icon-wrap"></span> <span class="mini-click-non">프로그램</span>
						</a>
						<ul class="submenu-angle form-mini-nb-dp" aria-expanded="false">
							<li>
								<a href="${pageContext.request.contextPath}/headoffice/program"><span class="mini-sub-pro">프로그램 목록</span></a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/headoffice/program/add"><span class="mini-sub-pro">프로그램 추가</span></a>
							</li>
						</ul>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/notice/noticeList" aria-expanded="false"> <span class="educate-icon educate-course icon-wrap"></span> <span class="mini-click-non">공지</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/question/questionList" aria-expanded="false"> <span class="educate-icon educate-course icon-wrap"></span> <span class="mini-click-non">문의사항</span>
						</a>
					</li>
					<li>
						<a class="has-arrow" href="mailbox.html" aria-expanded="false"> <span class="educate-icon educate-data-table icon-wrap"></span> <span class="mini-click-non">발주</span>
						</a>
						<ul class="submenu-angle" aria-expanded="false">
							<li>
								<a href="/headoffice/sportsEquipmentOrder/list"><span class="mini-sub-pro">발주관리</span></a>
							</li>
						</ul>
					</li>
					<li>
						<a class="has-arrow" href="mailbox.html" aria-expanded="false"> <span class="educate-icon educate-data-table icon-wrap"></span> <span class="mini-click-non">회원권</span>
						</a>
						<ul class="submenu-angle" aria-expanded="false">
							<li>
								<a href="${pageContext.request.contextPath}/headoffice/membershipList"><span class="mini-sub-pro">회원권 목록</span></a>
							</li>
						</ul>
						<ul class="submenu-angle" aria-expanded="false">
							<li>
								<a href="${pageContext.request.contextPath}/headoffice/addMembership"><span class="mini-sub-pro">회원권 추가</span></a>
							</li>
						</ul>
					</li>
					<li>
						<a class="has-arrow" href="mailbox.html" aria-expanded="false"> <span class="educate-icon educate-form icon-wrap"></span> <span class="mini-click-non">물품</span>
						</a>
						<ul class="submenu-angle form-mini-nb-dp" aria-expanded="false">
							<li>
								<a href="${pageContext.request.contextPath}/headoffice/equipment"><span class="mini-sub-pro">물품 목록</span></a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/headoffice/equipment/add"><span class="mini-sub-pro">물품 추가</span></a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#" aria-expanded="false" id="chat"> <span class="educate-icon educate-interface icon-wrap"></span> <span class="mini-click-non">채팅</span>
						</a>
					</li>

				</ul>
			</nav>
		</div>
	</nav>
</div>
<!-- End Left menu area -->
<script>
	$(document)
			.ready(
					function() {

						$('#chat')
								.click(
										function(event) {
											event.preventDefault();
											url = '${pageContext.request.contextPath}/chat/roomList';
											const options = 'top=10, left=10, width=600, height=700, status=no, menubar=no, toolbar=no';
											window.open(url, '_blank', options);
										})

					})
</script>