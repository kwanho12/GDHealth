<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

			<div class="container-fluid">
               <div class="row">
               
               	<c:forEach var="m" items="${programList}">
               	
               		<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12" style="margin-bottom:15px;">
                        <div class="courses-inner res-mg-b-30">
                            <div style="text-align:center;">	     
                            	<c:if test="${not empty m.filename}">
                            		<img src="${pageContext.request.contextPath}/upload/program/${m.filename}" style="height:270px; width:300px;">
                            	</c:if>
                        	    <c:if test="${empty m.filename}">
                            		<img src="${pageContext.request.contextPath}/noImg.jpg" style="height:270px; width:300px;">
                            	</c:if>                             
                            </div>
                          
                            <div class="course-des">
                            	<h3>${m.programName}</h3>
                                <p><span><i class="fa fa-clock"></i></span> <b>수용 인원 :</b> ${m.maxCustomer}</p>
                                <p><span><i class="fa fa-clock"></i></span> <b>개설 날짜 :</b> ${m.programDate}</p>
                            </div>
                            <div class="product-buttons">
                                <button type="button" class="button-default cart-btn" onclick="location.href='${pageContext.request.contextPath}/headoffice/program/${m.programNo}/${m.programDate}'">자세히 보기</button>
                            </div>
                        </div>
                  	    </div>
               	
               	</c:forEach>
  
               </div>
           </div>
           <!--------------------- 프로그램 list end-------------------------->
	           	         
           <!--------------------- start pagination -----------------------------------> 
	       	<div style="text-align:center;">       	
	             <ul class="pagination">
	             	  <c:if test="${currentPage == 1}">
	           			<li class="page-item disabled">
	            	  		<a class="page-link">처음</a>  	
	            	 	</li>	
	          		  </c:if>
	          		  <c:if test="${currentPage != 1}">
	           			<li class="page-item">           	  	
	            	  		<a class="page-link pageBtn" data-page="1" href="#">처음</a>
	            	 	</li>
	           		  </c:if>            	 				  
					  <c:if test="${prev}">
					  	<li class="page-item"><a class="page-link pageBtn" data-page="${startPageNum - 1}" href="#">이전</a></li>
					  </c:if>
					  <c:forEach begin="${startPageNum}" end="${endPageNum}" var="pageNum">
					  	<c:if test="${pageNum == currentPage}"> <!-- 페이지네이션 버튼 색 변경o --> 
					  		<li class="page-item active">
						  		<a class="page-link">${pageNum}</a>
						  	</li>
					    </c:if>
					    <c:if test="${pageNum != currentPage}"> <!-- 페이지네이션 버튼 색 변경x --> 
					  		<li class="page-item">
						  		<a class="page-link pageBtn" data-page="${pageNum}" href="#">${pageNum}</a>
						  	</li>
					  	</c:if>
					  </c:forEach>
					  <c:if test="${next}">
					  	<li class="page-item">
					  		<a class="page-link pageBtn" data-page="${endPageNum + 1}" href="#">다음</a>
					  	</li>
					  </c:if>
					  <c:if test="${currentPage == lastPage}">
					  	<li class="page-item disabled">
					  		<a class="page-link">끝</a>
					  	</li>
					  </c:if>
					  <c:if test="${currentPage != lastPage}">
					  	<li class="page-item">
					  		<a class="page-link pageBtn" data-page="${lastPage}" href="#">끝</a>
					  	</li>
					  </c:if>				  
				</ul>	  
	        </div>
	       <!----------------------- end pagination ---------------------------->        