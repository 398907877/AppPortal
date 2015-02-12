<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page2" type="org.springframework.data.domain.Page" required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
int current =  page2.getNumber() + 1;
int begin = Math.max(1, current - paginationSize/2);
int end = Math.min(begin + (paginationSize - 1), page2.getTotalPages());

request.setAttribute("current", current);
request.setAttribute("begin", begin);
request.setAttribute("end", end);
%>

	<ul class="pagination">
		 <% if (page2.hasPreviousPage()){%>

                <li><a href="?page2=${current-1}&sortType=${sortType}&${searchParams}">&lt;</a></li>
         <%}else{%>

                <li class="disabled"><a href="#">&lt;</a></li>
         <%} %>
 
		<c:forEach var="i" begin="${begin}" end="${end}">
            <c:choose>
                <c:when test="${i == current}">
                    <li class="active"><a href="?page2=${i}&sortType=${sortType}&${searchParams}">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="?page2=${i}&sortType=${sortType}&${searchParams}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
	  
	  	 <% if (page2.hasNextPage()){%>
               	<li><a href="?page2=${current+1}&sortType=${sortType}&${searchParams}">&gt;</a></li>
                	
         <%}else{%>
                <li class="disabled"><a href="#">&gt;</a></li>
           
         <%} %>

	</ul>

