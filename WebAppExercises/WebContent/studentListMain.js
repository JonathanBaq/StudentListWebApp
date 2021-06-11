function main() {
	getDataFromServer("http://localhost:8080/WebAppExercises/students", printStudents);
}

function printStudents(studentList) {
	let output = "";
	for (let student of studentList) {
		output += "<tr><td>" + student.id + "</td><td>" + 
		student.lastname + "</td><td>" + 
		student.firstname + "</td><td>" +
		student.streetaddress + "</td><td>" + 
		student.postcode + "</td><td>" + 
		student.postoffice + "</td><td>" + 
		createUpdateDeleteLinks(student.id) + "</td></tr>";
	}
	document.getElementById("studentInfo").innerHTML = output;

	function createUpdateDeleteLinks(studentId) {
		return "<span class='link' onclick='goToUpdate(" + studentId + ");'>Update</span>" +
			"&nbsp;&nbsp;" +
			"<span class='link' onclick='deleteStudent(" + studentId + ");'>Delete</span>";
	}
}

function goToAdd(){
	location.replace("studentAddMain.html");
}

function goToUpdate(studentId){
	location.replace("studentUpdate.html?id=" + studentId);
}

main();