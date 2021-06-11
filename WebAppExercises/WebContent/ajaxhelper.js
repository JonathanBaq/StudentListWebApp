function getDataFromServer(url, printData) {
	fetch(url)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw "HTTP status code is " + response.status;
			}
		})
		.then(responseData => printData(responseData))
		.catch(errorText => alert("getDataFromServer failed: " + errorText));
}

function postDataToServer (url, parameterData, processResponse) {
	const requestOptions = {
		method: "POST",
		headers: { "Content-Type": "application/x-www-form-urlencoded" },
		body: parameterData
	};
	fetch(url, requestOptions)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw "HTTP status code is " + response.status;
			}
		})
		.then(status => processResponse(status))
		.catch(errorText => alert("postDataToServer failed: " + errorText));
}

