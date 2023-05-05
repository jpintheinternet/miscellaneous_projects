<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
	<meta charset="UTF-8">
	<title>New Vaccine</title>
</head>
<body>
	<div class="ml-3 mr-5 mt-3">
		<h2>New Vaccine</h2>
		<form action="NewVaccine" method="post">
			<div class="form-group">
				<table class="table table-striped table-border">
					<tr>
						<th>Name</th>
						<td><input class="form-control" type="text" name="name" placeholder="Enter Name Here"></td>
					</tr>
					<tr>
						<th>Doses Required</th>
						<td><select class="form-control" name="dosesRequired">
							<option>1</option>
							<option>2</option>
						</select></td>
					</tr>
					<tr>
						<th>Days Between Doses</th>
						<td><input class="form-control" type="text" name="daysBetween" placeholder="Enter Number of Days"></td>
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