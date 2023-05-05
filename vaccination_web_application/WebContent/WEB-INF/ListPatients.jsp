<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
	<meta charset="UTF-8">
	<title>List Patients</title>
</head>
<body>
	<div class="ml-3 mr-5 mt-3">
		<h2>List Patients</h2>
		<div class="form-group">
			<table class="table table-striped table-bordered">
				<thead class="thead-dark">
					<tr>
						<th>Id</th>
						<th>Name</th>
						<th>Vaccine</th>
						<th>1st Dose</th>
						<th>2nd Dose</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${patients}" var="patient">
						<tr>
							<td>${patient.patientId}</td>
							<td>${patient.patientName}</td>
							<td>${patient.vaccineName}</td>
							<td>${patient.firstDoseDate}</td>
							<c:if test="${patient.secondDoseDate == 'Received'}">
								<td>
									<a class="btn btn-outline-dark" href="Received?patientId=${patient.patientId}&vaccineId=${patient.vaccineId}">${patient.secondDoseDate}</a>
								</td>
							</c:if>
							<c:if test="${patient.secondDoseDate !=  'Received'}">
								<td>${patient.secondDoseDate}</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<a class="btn btn-dark" href="NewPatient" role="button">New Patient</a>
		<a class="btn btn-dark" href="FrontPageSelect" role="button">Select Management</a>
	</div>
</body>
</html>