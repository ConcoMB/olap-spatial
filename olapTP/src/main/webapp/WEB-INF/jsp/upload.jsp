<%@ include file="header.jsp"%>
	<div class="hero-unit">
	<form method="POST">
		<br>
		<h2>Create the MDX XML automatically </h2>
		<h3>From the Lettuce's XML:</h3>
		<div class="fileupload fileupload-new" data-provides="fileupload">
			<div class="input-append">
			<div class="uneditable-input span3"><i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span></div><span class="btn btn-file"><span class="fileupload-new">Select file</span><span class="fileupload-exists">Change</span><input type="file" /></span><a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Remove</a>
			</div>
		</div>
		<td><input formaction="automatic/createAutomaticOutput" type="submit" value="Automatic" class="btn btn-primary btn-large" /></td>
		<h2>Or manually select the tables</h2>
		<td><input formaction="manual/selectTable" type="submit" value="Manual" class="btn btn-primary btn-large"/></td>
	</form>
	</div>
<%@ include file="footer.jsp"%>