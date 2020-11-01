
function registerUser(){
	fetch("http://localhost:8080/WebLesson/Register",{
		method: 'POST',
		headers: {
			"Content-Type": "application/json"
		},
		body:document.getElementById("register-form")
	}).then(response => {
		if(response.status !==200){
			document.getElementById("response").innerHTML = response.message +". Status:" + response.message;
    	}
		else{
			document.getElementById("response").innerHTML = response.message;
		}
		alert("Testing");
    	return response;
	})
	.catch(err => {
		console.log(err);
	});
}

window.onload = function(){
	let registerButton = document.getElementById("register-button");
	registerButton.addEventListener('click',registerUser);
}

