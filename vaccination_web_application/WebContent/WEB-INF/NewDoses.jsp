<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
	<meta charset="UTF-8">
	<title>New Doses</title>
</head>
<body>
	<div class="ml-3 mr-5 mt-3">
		<h2>New Doses</h2>
		<form action="NewDoses" method="post">
			<div class="form-group">
				<table class="table table-striped table-bordered">
					<tr>
						<th>Vaccine</th>
						<td>
							<select class="form-control" name="id">
								<c:forEach items="${vaccines}" var="vaccine">
									<option value="${vaccine.vaccineId}">${vaccine.vaccineName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>New Doses Received</th>
						<td><input class="form-control" type="text" name="dosesReceived" placeholder="Enter Number of Doses"></td>
					</tr>
					<tr>
						<td colspan="2"><button type="submit" class="btn btn-dark">Add</button></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>