<%@ include file="header.jsp"%>
<div class="well-large well">
<h2>Seleccione las columnas correspondientes a la tabla <c:out value="${uniqueTable}" /></h2>
<p><c:out value="${message}" /></p>
	<form action="manageSelectedColumns" method="POST">
		<fieldset id="marcoLogin">
			<c:forEach items="${multidimColumns}" var="multidimColumn">
				<div>
					<c:out value="${multidimColumn.name}" />:
					</br>
					<select name="${multidimColumn.name}">
						<c:forEach items="${columns}" var="column">
							<option value="${column.name}"><c:out value="${column.name}" />
						</c:forEach>
					</select>
				</div>
				</br>
			</c:forEach>
			
				<input class="btn btn-primary" type="submit" value="Aceptar" />
		</fieldset>
	</form>
</div>
<%@ include file="footer.jsp"%>