<%@ page import="domain.Task" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
				integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
</head>
<body>
<div class="container mt-5">
	<%
		String message = (String) request.getAttribute("message");

		if (message != null) {
	%>
		<div class="alert alert-success alert-dismissible fade show" role="alert">
			<strong><%= message %></strong>
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	<% } %>

	<ul class="nav flex-column">
		<li class="nav-item">
			<a class="btn btn-primary" data-toggle="collapse" href="#addTask"
				 role="button" aria-expanded="false" aria-controls="addTask">Add task</a>
		</li>
		<li class="nav-item">
			<a class="btn btn-primary" data-toggle="collapse" href="#removeTask"
				 role="button" aria-expanded="false" aria-controls="removeTask">Remove task</a>
		</li>
		<li class="nav-item">
			<a class="btn btn-primary" data-toggle="collapse" href="#listTasks"
				 role="button" aria-expanded="false" aria-controls="addTask">List tasks</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="#">Search tasks</a>
		</li>
		<li class="nav-item">
			<a class="nav-link" href="save">Save tasks</a>
		</li>
		<li class="nav-item">
			<div class="row-sm-2">
				<a class="nav-link" href="exit">Exit</a>
			</div>
		</li>
	</ul>

	<div class="collapse" id="addTask">
		<form method="POST" action="add">
			<div class="form-group">
				<label for="idTaskAdd">Id task</label>
				<input type="text" class="form-control" id="idTaskAdd" name="idTask">
			</div>
			<div class="form-group">
				<label for="name">Name</label>
				<input type="text" class="form-control" id="name" name="name">
			</div>
			<div class="form-group">
				<label for="description">Description</label>
				<input type="text" class="form-control" id="description" name="description">
			</div>
			<div class="form-group">
				<label for="dateAdd">Date</label>
				<input type="date" id="dateAdd" name="date">
			</div>

			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>

	<div class="collapse" id="listTasks">
		<a class="btn btn-primary" href="list">Refresh</a>

		<form class="form-inline" method="POST" action="list">
			<div class="form-group">
				<label for="date">Date</label>
				<input type="date" id="date" name="date" class="form-control mx-sm-3" />
				<input type="submit" value="Search">
			</div>
		</form>


		<div class="card-columns">
		<%
			List<Task> tasks = (List<Task>) request.getAttribute("tasks");

			if (tasks != null) {
				for (Task task : tasks) {
		%>
			<div class="card">
				<div class="card-body">
					<h5 class="card-title"><%= task.getName() %></h5>
					<p class="card-text"><%= task.getDescription() %></p>
					<p class="card-text"><small class="text-muted"><%= task.getDate().getTime() %>></small></p>
				</div>
			</div>
		<%
				}
			}
		%>
		</div>
	</div>

		<div class="collapse" id="removeTask">
			<form class="form-inline" method="POST" action="remove">
				<div class="form-group">
					<label for="idTask">Id</label>
					<input type="text" id="idTask" name="idTask" class="form-control mx-sm-3" />
					<input type="submit" value="Delete">
				</div>
			</form>
		</div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
				integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
				crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
				integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
				crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"
				integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s"
				crossorigin="anonymous"></script>
</body>
</html>
