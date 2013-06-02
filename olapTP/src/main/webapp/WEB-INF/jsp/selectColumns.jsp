<%@ include file="header.jsp"%>
<div class="well-large well">
<h2>Select the columns from the table <c:out value="${uniqueTable}" /></h2>
<p><c:out value="${message}" /></p>
	<form class="form-horizontal" action="manageSelectedColumns" method="POST">
		<fieldset id="marcoLogin">
			<c:forEach items="${multidimColumns}" var="multidimColumn">
				<div class="control-group">
					<label class="control-label"><c:out value="${multidimColumn.name}" />:</label>
					</br>
					<div class="controls">
					<select name="${multidimColumn.name}">
						<c:forEach items="${columns}" var="column">
							<option value="${column.name}"><c:out value="${column.name}" />
						</c:forEach>
					</select>
				</div>
				</div>
				</br>
			</c:forEach>
			
				<input class="btn btn-primary" type="submit" value="Aceptar" />
		</fieldset>
	</form>
</div>
<%@ include file="footer.jsp"%>