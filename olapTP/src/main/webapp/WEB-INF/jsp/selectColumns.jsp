<%@ include file="header.jsp"%>
<div id="content">
<h2>Seleccione las columnas correspondientes de la tabla <c:out value="${uniqueTable}" /></h2>
<p>Los datos se muestran de la forma: "NombreDimension_NombreNivel_NombrePropiedad" correspondientes a los nombres el multidim proporcionado, a excepción de las métricas que se muestran diréctamente con el nombre original en el multidim.</p>
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
			
				<input type="submit" value="Aceptar" />
		</fieldset>
	</form>
</div>
<%@ include file="footer.jsp"%>