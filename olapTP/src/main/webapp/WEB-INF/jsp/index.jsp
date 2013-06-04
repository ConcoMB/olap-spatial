<%@ include file="header.jsp"%>
<div class="hero-unit">
	<form:form name="dbcredentialsform" action="index" method="POST" class="bs-docs-example form-horizontal" commandName="dbcredentialsform">
		<div class="control-group">
			<label class="control-label" for="url">Database URL</label>
			<div class="controls">
				<form:input type="text" id="url" path="url" placeholder="insert URL..."/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="user">Username</label>
			<div class="controls">
				<form:input type="text" id="user" path="user"
					placeholder="insert username..."/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="password">Password</label>
			<div class="controls">
				<form:input type="password" id="password" path="password"
					placeholder="insert password..."/>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<button type="submit" class="btn btn-primary">Connect!</button>
			</div>
		</div>
	</form:form>
</div>
<%@ include file="footer.jsp"%>