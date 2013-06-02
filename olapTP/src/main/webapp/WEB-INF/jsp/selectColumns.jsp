<%@ include file="header.jsp"%>
<div class="well-large well">
<h2>Select a columns from the table to match multiDim columns <c:out value="${uniqueTable}" /></h2>
<p><c:out value="${message}" /></p>
<table class="table table-hover">
	<tr><th>C</th><th></th></tr>
	<form action="manageSelectedColumns" method="POST">
		<fieldset id="marcoLogin">
			<c:forEach items="${multidimColumns}" var="multidimColumn">
				<tr>
					<td>
					<c:out value="${multidimColumn.name}" />:
					</td>
					<td>
						<select name="${multidimColumn.name}">
							<c:forEach items="${columns}" var="column">
								<c:if test="column.name.equalsIgnoreCase(multidimColumn.name)">
								<%-- <% selected="selected"%> --%>
									<script>
										console.log("true");
									</script>
								</c:if>
								<option value="${column.name}"><c:out value="${column.name}" />
							</c:forEach>
						</select>
					</td>
				</tr>
			</c:forEach>
			
				<input class="btn btn-primary" type="submit" value="Submit" />
		</fieldset>
	</form>
</table>
</div>
<%@ include file="footer.jsp"%>