<%@ include file="header.jsp"%>
<div id="content">
<h2>Seleccione la tabla con la que desea trabajar</h2>
<p><c:out value="${message}" /></p>
	<form action="selectColumns" method="POST">
		<fieldset id="marcoLogin">
			<c:if test="${tables == null}">
				<h3>No existe ninguna tabla en la base de datos proporcionada</h3>
			</c:if>
			<c:if test="${tables != null}">
				<form action="selectColumns" method="POST">
						<table>
							<tr>
								<select name="table"><br>
									<c:forEach items="${tables}" var="table">
										<option value="${table}"><c:out value="${table}" />
									</c:forEach>
								</select>
							</tr>
							<br/>
							<tr>
								<td><input type="submit" value="Aceptar" /></td>
							</tr>
						</table>
				</form>
			</c:if>
			
		</fieldset>
	</form>
</div>
<%@ include file="footer.jsp"%>