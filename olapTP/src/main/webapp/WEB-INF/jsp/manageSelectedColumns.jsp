<%@ include file="header.jsp"%>
<div class="well well-large">
<div class="alert"><p><c:out value="${message}" /></p></div>
<p><div class="alert alert-error"><c:out value="${columnTypeWrong}"/></div></p>
	<table class="table table-striped">
	<th><td>Multidim</td><td>Columna</td></th>
	<c:forEach items="${columnsInTable}" var="columnInTable">
		<tr>
			<td><c:out value="${columnInTable.multidimName}" /></td>
			<td><c:out value="${columnInTable.columnName}" /></td>
		</tr>
	</c:forEach>
	</table>
</div>
<%@ include file="footer.jsp"%>