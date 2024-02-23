<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

			<div class="container-fluid">   
            
               	<!--------------------- 회원 list start-------------------------->
               	<c:set var="cnt" value="0"></c:set>          		  			
  				<c:forEach var="m" items="${empList}">
	   			    <c:set var="cnt" value="${cnt + 1}"></c:set>
	   			    <c:if test="${(cnt%4) == 1}">
	   			  	 	<div class="row"> 
	   			    </c:if>	 			    
			           	    <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12" style="margin-bottom:15px;">
			                	<div class="student-inner-std res-mg-b-30">
			                     <div class="student-img">
			                        <img src="${pageContext.request.contextPath}/upload/emp/${m.filename}" style="height:300px; width:300px;"/>     	  
			                     </div>
			                     <div class="student-dtl">
			                         <h2>
			                         <a href="${pageContext.request.contextPath}/headoffice/emp/empOne/${m.empId}">
			                         	${m.empName}
			                         </a>		                         
			                         </h2>
			                         <c:if test="${m.empGender == 'm'}">
			                         	<p class="dp">남자</p>
			                         </c:if>
			                         <c:if test="${m.empGender == 'f'}">
			                         	<p class="dp">여자</p>
			                         </c:if>  
			                         <p class="dp-ag"><b>입사 날짜 :</b> ${m.createdate}</p>
			                  		</div>
			              	    </div>
			           	    </div>	           	    
	           	    <c:if test="${(cnt%4 == 0) || (cnt == employeeCnt)}"> <!-- cnt가 4의 배수이거나 마지막 순서일 때 -->
	   			  	 	</div>
	   			    </c:if>
           		</c:forEach>           		
                <!--------------------- 회원 list end-------------------------->      
            </div>    
            
       		<!--------------------- 페이징 start ----------------------------------->     
            <div style="text-align:center;">       	
	             <ul class="pagination">   	
	             	
	             	<!------- '처음' 버튼 start ------->
             		<c:if test="${currentPage == 1}">
             			<li class="page-item disabled">
	             	  		<a class="page-link">처음</a>  	
	             	 	</li>	
             		</c:if>
             		<c:if test="${currentPage != 1}">
             			<li class="page-item">           	  	
	             	  		<a class="page-link pageBtn" href="#" data-page="1">처음</a>
	             	 	</li>
             		</c:if>
             		<!------- '처음' 버튼 end ------->

					<!------- '이전' 버튼 start ------->
		  			<c:if test="${prev}">
					  	<li class="page-item"><a class="page-link pageBtn" href="#" data-page="${startPageNum - 1}">이전</a></li>
				 	</c:if>
				 	<!------- '이전' 버튼 end ------->
				 	
				 	<!------- 페이지 번호 버튼 start ------->
				    <c:forEach begin="${startPageNum}" end="${endPageNum}" var="pageNum">
					  	<c:if test="${pageNum == currentPage}"> <!-- 페이징 버튼 색 변경o --> 
					  		<li class="page-item active">
						  		<a class="page-link">${pageNum}</a>
						  	</li>
					  	</c:if>
					  	<c:if test="${pageNum != currentPage}"> <!-- 페이징 버튼 색 변경x --> 
					  		<li class="page-item">
						  		<a class="page-link pageBtn" href="#" data-page="${pageNum}">${pageNum}</a>
						  	</li>
					  	</c:if>
				    </c:forEach>
				    <!------- 페이지 번호 버튼 end ------->
				    
				    <!------- '다음' 버튼 start ------->
			  		<c:if test="${next}">
					  	<li class="page-item pageBtn"><a class="page-link" href="#" data-page="${endPageNum + 1}">다음</a></li>
				 	</c:if>
				 	<!------- '다음' 버튼 end ------->
				 	
				 	<!------- '끝' 버튼 start ------->
				  	<c:if test="${currentPage == lastPage}">
					  	<li class="page-item disabled">
					  		<a class="page-link">끝</a>
					  	</li>
				    </c:if>
		  	    	<c:if test="${currentPage != lastPage}">
					  	<li class="page-item">
					  		<a class="page-link pageBtn" href="#" data-page="${lastPage}">끝</a>
					  	</li>
					</c:if>	
					<!------- '끝' 버튼 end ------->
							  
				</ul>	 
				<!----------------------- 페이징 end ---------------------------->     
            </div>