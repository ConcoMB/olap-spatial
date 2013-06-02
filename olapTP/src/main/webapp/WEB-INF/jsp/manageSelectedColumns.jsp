<%@ include file="header.jsp"%>
<div class="well well-large">
<div class="alert"><p><c:out value="${message}" /></p></div>
<c:if test="columnTypeWrong == null || columnTypeWrong.isEmpty()">
	<p><div class="alert alert-error"><c:out value="${columnTypeWrong}"/></div></p>
</c:if>
	<table class="table table-striped">
	<tr><th>Multidim</th><th>Columna</th></tr>
	<c:forEach items="${columnsInTable}" var="columnInTable">
		<tr>
			<td><c:out value="${columnInTable.multidim}" /></td>
			<td><c:out value="${columnInTable.column}" /></td>
		</tr>
	</c:forEach>
	</table>
</div>
<%@ include file="footer.jsp"%>