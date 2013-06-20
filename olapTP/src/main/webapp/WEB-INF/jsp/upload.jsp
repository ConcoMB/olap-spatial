<%@ include file="header.jsp"%>
	<div class="hero-unit">
	<form:form method="POST" action="upload" commandName="uploadxmlform" enctype="multipart/form-data">
		<br>
		<h3>Insert the XML:</h3>
		<div class="fileupload fileupload-new" data-provides="fileupload">
			<div class="input-append">
			<div class="uneditable-input span3"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div><span class="btn btn-file"><span class="fileupload-new">Select file</span><span class="fileupload-exists">Change</span><form:input type="file" path="file"/></span><a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Remove</a>
			</div>
			<c:if test="${not empty file_error}">
			<div class="control-group error"><label class="control-label">${file_error}</label></div>
			</c:if>
		</div>
		<h2>Create the MDX XML automatically </h2>
		<td><input formaction="upload" type="submit" name="upload" value="Automatic" class="btn btn-primary btn-large" /></td>
		<h2>Or manually select the tables</h2>
		<td><input formaction="upload" type="submit" name="upload" value="Manual" class="btn btn-primary btn-large"/></td>
	</form:form>
	</div>
<%@ include file="footer.jsp"%>