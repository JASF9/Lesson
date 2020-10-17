var registerForm = document.getElementById("register-form")
var registerButton = document.getElementById("register-button")


registerButton.onclick = function(){	
	fetch("http://localhost:8080/WebLesson/Register").then(function(response){
    	alert("hello")
    }).catch(function(err){
    	alert("bye");
    })
}