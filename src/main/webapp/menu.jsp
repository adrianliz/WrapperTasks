<!--
menu.jsp
09/01/2021
@authors Adrián Lizaga Isaac, Borja Rando Jarque
-->
<%@ page import="domain.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
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
	<div class="container mx-auto">
		<h1 class="text-center">Dashboard Tasks2</h1>

		<div class="row">
			<%
				String errorMessage = (String) request.getAttribute("errorMessage");
				if (errorMessage != null) {
			%>
			<div class="alert alert-danger alert-dismissible fade show" role="alert">
				<strong><%= errorMessage %>
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
			<div class="alert alert-success alert-dismissible fade show" role="alert">
				<strong><%= successMessage %>
				</strong>
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<% } %>
		</div>

		<div class="mt-2 text-right">
			<a class="btn btn-dark" href="exit">Exit</a>
		</div>

		<div class="row mt-2">
			<div class="col-lg-2 m-2">
				<a class="btn btn-primary" href="new">New task file</a>
			</div>

			<div class="col-lg-2 m-2">
				<a class="btn btn-primary" href="add.jsp">Add task</a>
			</div>

			<div class="col-lg-2 m-2">
				<a class="btn btn-primary" href="list">List tasks</a>
			</div>

			<div class="col-lg-2 m-2">
				<form class="form-inline" method="POST" action="list">
					<div class="form-group mb-2">
						<label class="sr-only" for="date">Date</label>
						<input type="date" class="form-control mb-2 mr-sm-2" id="date" name="date">
						<button type="submit" class="btn btn-success mb-2">Search</button>
					</div>
				</form>
			</div>
		</div>


		<div class="card-columns m-2">
			<%
				List<Task> tasks = (List<Task>) session.getAttribute("tasks");
				if (tasks != null) {
					for (Task task : tasks) {
			%>
			<div class="card shadow p-3 mb-5 bg-white rounded">
				<div class="card-body">
					<h5 class="card-title"><%= task.getName() %>
					</h5>
					<p class="card-text"><%= task.getDescription() %>
					</p>
					<p class="card-text">
						<small class="text-muted">
							<%
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								String formattedDate = sdf.format(task.getDate().getTime());
							%>
							<%= formattedDate %>
						</small>
					</p>
					<form method="POST" action="remove">
						<label>
							<input name="idTask" value=<%= task.getId()%> hidden>
						</label>
						<div class="text-right">
							<button type="submit" class="btn btn-danger mb-2">Remove</button>
						</div>
					</form>
				</div>
			</div>
			<%
					}
				}
			%>
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
