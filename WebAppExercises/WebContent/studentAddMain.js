function addStudent() {
	const url = "http://localhost:8080/WebAppExercises/addStudent";
	const form = document.forms["formAddStudent"];
	const parameterData =
		"id=" + form["txtId"].value +
		"&firstname=" + form["txtFirstname"].value +
		"&lastname=" + form["txtLastname"].value +
		"&streetaddress=" + form["txtStreetAddress"].value +
		"&postcode=" + form["txtPostCode"].value +
		"&postoffice=" + form["txtPostOffice"].value;
	postDataToServer (url, parameterData, processAddResponse);
	
}
function processAddResponse(status) {
	if (status.errorCode === 0) {
		alert("Student data added!");
		location.assign("/WebAppExercises/studentListMain.html");
	} else if (status.errorCode === 1) {
		alert("Cannot add the student. The id is already in use!");
	} else {
		alert("The database is temporarily unavailable. Please try again later.");
	}
}