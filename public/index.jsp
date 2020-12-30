<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
</head>
<body>
<form method="POST" action="login">
	<div class="form-group">
		<label for="user">User</label>
		<input type="text" class="form-control" id="user" name="user" value="prog">
	</div>
	<div class="form-group">
		<label for="password">Password</label>
		<input type="password" class="form-control" id="password" name="password" value="prog123">
	</div>
	<div class="form-group">
		<label for="IP">IP</label>
		<input type="text" class="form-control" id="IP" name="IP" value="155.210.71.101">
	</div>
	<div class="form-group">
		<label for="port">Port</label>
		<input type="text" class="form-control" id="port" name="port" value="623">
	</div>

	<button type="submit" class="btn btn-primary">Submit</button>
</form>
</body>
</html>
