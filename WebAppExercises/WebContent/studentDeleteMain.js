function deleteStudent(studentId) {
	const confirmed = confirm("Delete Student?");
	if (confirmed == true) {
		const url = "http://localhost:8080/WebAppExercises/deleteStudent";
		const parameterData = "id=" + studentId;
		
		postDataToServer (url, parameterData, processAddResponse);
		
	}
}
function processAddResponse(status) {
	if (status.errorCode === 0) {
		alert("Student data deleted!");
		location.reload();
	} else if (status.errorCode === 1) {
		alert("Student data not deleted. Unknown student id!");
	} else {
		alert("The database is temporarily unavailable. Please try again later.");
	}
}