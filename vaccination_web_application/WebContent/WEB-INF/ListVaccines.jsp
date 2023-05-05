<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
	<meta charset="UTF-8">
	<title>List Vaccines</title>
</head>
<body>
	<div class="ml-3 mr-5 mt-3">
		<h2>List of Vaccines</h2>
		<div class="form-group">
			<table class="table table-striped table-bordered">
				<thead class="thead-dark">
					<tr>
						<th>Vaccine Name</th>
						<th>Doses Required</th>
						<th>Days Between Doses</th>
						<th>Total Doses Received</th>
						<th>Total Doses Left</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${vaccines}" var="vaccine">
						<tr>
							<th>${vaccine.vaccineName}</th>
							<td>${vaccine.dosesRequired}</td>
							<td>${vaccine.daysBetween}</td>
							<td>${vaccine.totalDosesReceived}</td>
							<td>${vaccine.totalDosesLeft}</td>
							<td><a class="btn btn-outline-dark" href="EditVaccines?id=${vaccine.vaccineId}" role="button">Edit</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<a class="btn btn-dark" href='NewVaccine' role='button'>New Vaccine</a>   
		<a class="btn btn-dark" href='NewDoses' role='button'>New Doses</a>
		<a class="btn btn-dark" href="FrontPageSelect" role="button">Select Management</a>
	</div>
</body>
</html>