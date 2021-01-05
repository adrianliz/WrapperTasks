<%@ page contentType="text/html;charset=UTF-8" %>

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
		<%
			Boolean disableBack = (Boolean) session.getAttribute("disableBack");
			if ((disableBack == null) || (!disableBack)) {
		%>
		<div class="text-right">
			<a class="btn btn-dark" href="menu.jsp">Go back</a>
		</div>
		<% } else { %>
		<div class="alert alert-warning alert-dismissible fade show" role="alert">
			<strong>You must add a valid task before go back</strong>
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<% } %>

		<div class="row-sm-6 d-flex align-items-center justify-content-center mx-auto">
			<div class="col-sm-6 mt-2">
				<h1 class="display-4 text-center">Add task</h1>
				<form method="POST" action="add">
					<div class="form-group">
						<label for="idTask-add">ID Task</label>
						<input type="text" class="form-control" id="idTask-add" name="idTask" required>
					</div>
					<div class="form-group">
						<label for="name">Name</label>
						<input type="text" class="form-control" id="name" name="name" required>
					</div>
					<div class="form-group">
						<label for="description">Description</label>
						<input type="text" class="form-control" id="description" name="description" required>
					</div>
					<div class="form-group">
						<label for="dateAdd">Date</label>
						<input type="date" class="form-control" id="dateAdd" name="date" required>
					</div>

					<div class="text-right">
						<button type="submit" class="btn btn-success">Submit</button>
					</div>
				</form>

				<%
					String errorMessage;
					if ((errorMessage = (String) request.getAttribute("errorMessage")) != null) {
				%>
				<div class="mt-2 alert alert-danger alert-dismissible fade show" role="alert">
					<strong>
						<%= errorMessage %>
					</strong>
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<% } %>

				<%
					String successMessage = (String) request.getAttribute("successMessage");
					if (successMessage != null) {
				%>
				<div class="mt-2 alert alert-success alert-dismissible fade show" role="alert">
					<strong><%= successMessage %>
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
</body>
</html>
