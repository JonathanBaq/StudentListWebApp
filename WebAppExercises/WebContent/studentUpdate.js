function main(){
	let updateId = getParamValue('id');
	updateStudent(updateId);
}

function getParamValue(key){
	let p = window.location.search;
	p = p.match(new RegExp(key + '=([^&=]+)'));
	return p ? p[1] : false;
}

//Use own ShowStudentByIdServlet to show current student
function updateStudent(studentId) {
	const url = "http://localhost:8080/WebAppExercises/showStudent";
	let parameterData = "id=" + studentId;
	postDataToServer(url, parameterData, processShowResponse);
}

function processShowResponse(studentList) {

	for (const student of studentList) {
		
		const form = document.forms["formUpdateStudent"];
		form["txtId"].disabled = true;
		form["txtId"].value = student.id;
		form["txtFirstname"].value = student.firstname;
		form["txtLastname"].value = student.lastname;
		form["txtStreetAddress"].value = student.streetaddress;
		form["txtPostCode"].value = student.postcode;
		form["txtPostOffice"].value = student.postoffice;
	}
	
}

function confirmUpdate() {
	const url = "http://localhost:8080/WebAppExercises/updateStudent";
	const form = document.forms["formUpdateStudent"];
	let parameterData =
		"id=" + form["txtId"].value +
		"&firstname=" + form["txtFirstname"].value +
		"&lastname=" + form["txtLastname"].value +
		"&streetaddress=" + form["txtStreetAddress"].value +
		"&postcode=" + form["txtPostCode"].value +
		"&postoffice=" + form["txtPostOffice"].value;
	postDataToServer(url, parameterData, processUpdateResponse);

}

function processUpdateResponse(status) {
	if (status.errorCode === 0) {
		alert("Student data updated!");
		location.assign("/WebAppExercises/studentListMain.html");
	} else if (status.errorCode === 1) {
		alert("Cannot update, student not found!");
	} else {
		alert("The database is temporarily unavailable. Please try again later.");
	}
}

main();
