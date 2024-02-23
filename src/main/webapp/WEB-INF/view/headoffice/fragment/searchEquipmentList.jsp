<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

		<div class="container-fluid">   
       		<h3>물품 목록</h3>
           	<!--------------------- 회원 list start-------------------------->   		  	  				
			    <table class="table table-bordered">
			    	<colgroup>
					    <col width="15%" />
					    <col width="8%" />
					    <col width="10%" />
					    <col width="28%" />
					    <col width="15%" />
					    <col width="10%" />
						<col width="9%" />
					    <col width="5%" />
			    	</colgroup>
			    	<thead>
			    		<tr>
			    			<th>물품 이름</th>
			    			<th>이미지</th>
			    			<th>물품 가격(원)</th>
			    			<th>비고</th>
			    			<th>작성자</th>
			    			<th>작성 날짜</th>
			    			<th>활성화 여부</th>	
			    			<th>수정</th>
			    		</tr>
			    	</thead>
			    	<tbody>
						<c:forEach var="m" items="${equipmentList}">
			    			<tr>	    				
			    				<td>${m.itemName}</td>
			    				<td>
			    					<img src="${pageContext.request.contextPath}/upload/equipment/${m.filename}" style="width:70px; height:70px;">
			    				</td>
			    				<td>${m.itemPrice}</td>   				
			    				<td>${m.note}</td>
			    				<td>${m.employeeName}</td>
			    				<td>${m.createdate}</td>
			    				<c:if test="${m.active == 'Y'}">
			    					<td><button type="button" class="btn btn-primary deactivateBtn" 
			    									data-equipmentno="${m.equipmentNo}">활성화</button></td>
			    				</c:if>
			    				<c:if test="${m.active == 'N'}">
			    					<td><button type="button" class="btn btn-primary activateBtn" 
			    									data-equipmentno="${m.equipmentNo}">비활성화</button></td>
			    				</c:if>
		    					<td>
		    						<a href="${pageContext.request.contextPath}/headoffice/equipment/update/${m.equipmentNo}" class="btn btn-primary">수정</a>
		    					</td>
			    			</tr>
			    		</c:forEach>   
			    	</tbody>
			    </table>
       		        		
            <!--------------------- 회원 list end-------------------------->      
        </div>         
           		<!--------------------- 페이징 start ----------------------------------->     
            <div style="text-align:center;">       	
	             <ul class="pagination">
	             
             	  <c:if test="${currentPage == 1}">
           			<li class="page-item disabled">
            	  		<a class="page-link">처음</a>  	
            	 	</li>	
          		  </c:if>
          		  <c:if test="${currentPage != 1}">
           			<li class="page-item">           	  	
            	  		<a class="page-link searchPageBtn" data-page="1" data-type="${type}" 
       	  									data-keyword="${keyword}" href="#">처음</a>
            	 	</li>
           		  </c:if>  
					  
					  <c:if test="${prev}">
					  	<li class="page-item">
					  		<a class="page-link searchPageBtn" data-page="${startPageNum - 1}" data-type="${type}" 
	            	  						data-keyword="${keyword}" href="#">이전</a>
	            	  	</li>
					  </c:if>
					  <c:forEach begin="${startPageNum}" end="${endPageNum}" var="pageNum">
					  	<c:if test="${pageNum == currentPage}"> <!-- 페이징 버튼 색 변경o --> 
					  		<li class="page-item active">
						  		<a class="page-link">${pageNum}</a>
						  	</li>
					  	</c:if>
					  	<c:if test="${pageNum != currentPage}"> <!-- 페이징 버튼 색 변경x --> 
					  		<li class="page-item">
						  		<a class="page-link searchPageBtn" data-page="${pageNum}" 
						  					data-type="${type}" data-keyword="${keyword}" href="#">${pageNum}</a>
						  	</li>
					  	</c:if>
					  </c:forEach>
					  <c:if test="${next}">
					  	<li class="page-item"><a class="page-link searchPageBtn" data-page="${endPageNum + 1}" 
					  						data-type="${type}" data-keyword="${keyword}" href="#">다음</a></li>
					  </c:if>	  
					  <c:if test="${currentPage == lastPage}">
					  	<li class="page-item disabled">
					  		<a class="page-link">끝</a>
					  	</li>
					  </c:if>
					  <c:if test="${currentPage != lastPage}">
					  	<li class="page-item">
					  		<a class="page-link searchPageBtn" data-page="${lastPage}" 
					  						data-type="${type}" data-keyword="${keyword}" href="#">끝</a>
					  	</li>
					  </c:if>
				</ul>	  
            </div>
               <!----------------------- 페이징 end ----------------------------> 