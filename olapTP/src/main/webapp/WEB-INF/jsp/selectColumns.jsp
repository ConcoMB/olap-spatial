<%@ include file="header.jsp"%>
<div class="well-large well">
	<h2>
		Select matching columns from table
		<c:out value="${uniqueTable}" />
	</h2>
	<p>
		<c:out value="${message}" />
	</p>
	<form action="manageSelectedColumns" method="POST">
		<table class="table table-hover">
			<tr>
				<th>Multidim</th>
				<th>Column</th>
			</tr>
			<c:forEach items="${multidimColumns}" var="multidimColumn">
				<tr>
					<td><c:out value="${multidimColumn.name}" />:</td>
					<td><select name="${multidimColumn.name}">
							<c:forEach items="${columns}" var="column">
								<c:set var="selected" value="" />
								<c:if
									test="${fn:toLowerCase(column.name) == fn:toLowerCase(multidimColumn.name)}">
									<c:set var="selected" value="selected" />
								</c:if>
								<option value="${column.name}" <c:out value="${selected}"/>>
									<c:out value="${column.name}" />
								</option>
							</c:forEach>
					</select></td>
				</tr>
			</c:forEach>
			<tr>
			<td><input class="btn btn-primary" type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form>
</div>
<%@ include file="footer.jsp"%>