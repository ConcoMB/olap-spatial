<%@ include file="header.jsp"%>
<div id="content">
<p><c:out value="${message}" /></p>
<p><div style="color:#FF0000;"><c:out value="${columnTypeWrong}"/></div></p>
	<c:forEach items="${columnsInTable}" var="columnInTable">
		<div>
			<c:out value="${columnInTable.multidimName}" />
			<c:out value="->" />
			<c:out value="${columnInTable.columnName}" />
		</div>
	</c:forEach>
</div>
<%@ include file="footer.jsp"%>