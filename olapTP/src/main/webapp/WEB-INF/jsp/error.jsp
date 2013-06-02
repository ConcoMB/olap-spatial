<%@ include file="header.jsp"%>
		<div class="alert alert-error"><c:out value="${error_message}"/></div>
		</br>
		<div class="well-large alert"><c:out value="${errorStackTrace}"/></div>
		
<%@ include file="footer.jsp"%>