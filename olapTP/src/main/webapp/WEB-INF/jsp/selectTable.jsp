<%@ include file="header.jsp"%>
<div class="well well-large">
<h2>Select the table</h2>
<p><c:out value="${message}" /></p>
	<form action="selectColumns" method="POST">
		<fieldset id="marcoLogin">
			<c:if test="${tables == null}">
				<h3>No databases</h3>
			</c:if>
			<c:if test="${tables != null}">
				<form:form action="selectColumns" method="POST">
						<table>
							<tr>
								<form:select name="table" path="table"><br>
									<c:forEach items="${tables}" var="table">
										<option value="${table}"><c:out value="${table}" />
									</c:forEach>
								</form:select>
							</tr>
							<tr>
								<td><input class="btn btn-primary" type="submit" value="Accept" /></td>
							</tr>
						</table>
				</form:form>
			</c:if>
			
		</fieldset>
	</form>
</div>
<%@ include file="footer.jsp"%>