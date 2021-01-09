<!--
index.jsp
09/01/2021
@authors Adrián Lizaga Isaac, Borja Rando Jarque
-->
<%@ page contentType="text/html;charset=UTF-8" %>

<%
	Boolean disableBack = (Boolean) session.getAttribute("disableBack");

	if ((disableBack != null) && (disableBack)) {
%>
<jsp:forward page="add.jsp"/>
<% } %>

<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!-- Bootstrap -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
				integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

	<title>Tasks2</title>
</head>
<body>
	<div class="container mt-2">
		<div class="row-sm-6 d-flex align-items-center justify-content-center mx-auto">
			<div class="col-sm-6 mt-2">
				<h1 class="display-4 text-center">Login</h1>
				<form method="POST" action="login">
					<div class="form-group">
						<label for="user">User</label>
						<input type="text" class="form-control" id="user" name="user" value="prog" required>
					</div>
					<div class="form-group">
						<label for="password">Password</label>
						<input type="password" class="form-control" id="password" name="password" value="prog123" required>
					</div>
					<div class="form-group">
						<label for="IP">IP</label>
						<input type="text" class="form-control" id="IP" name="IP" value="155.210.71.101" required>
					</div>
					<div class="form-group">
						<label for="port">Port</label>
						<input type="text" class="form-control" id="port" name="port" value="123" required>
					</div>

					<div class="text-right">
						<button type="submit" class="btn btn-primary" id="loginButton">Login</button>
					</div>
				</form>

				<div class="mt-2 text-center">
					<div class="spinner-border text-primary" role="status" id="loader" style="display:none">
						<span class="sr-only">Loading...</span>
					</div>
				</div>

				<%
					String errorMessage;
					if ((errorMessage = (String) request.getAttribute("errorMessage")) != null) {
				%>
				<div class="mt-3 alert alert-danger alert-dismissible fade show" role="alert">
					<strong>
						<%= errorMessage %>
					</strong>
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<% } %>
			</div>
		</div>
	</div>

	<!-- Bootstrap -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
					integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
					crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
					integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
					crossorigin="anonymous"></script>

	<script>
    $("#loginButton").click(function () {
      $('#loader').show();
    });
	</script>
</body>
</html>
