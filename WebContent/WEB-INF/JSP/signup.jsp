<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/OJT_Student_Spring_MVC/css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
<title> Student Registration LGN001 </title>
</head>
<body class="login-page-body"> 
  
    <div class="login-page">
      <div class="form">
        <div class="login">
          <div class="login-header">
            <h1>Create Account</h1>
            <div id="ValidationMsg" style="display:none;color:red;"></div>
                        <div id="message" style="color:red;">${error}</div>

<script>
  // Wait for the page to load
  window.addEventListener('load', function() {
    // Get the message element
    var message = document.getElementById('message');
    
    // Set a timeout to hide the message after 3 seconds
    setTimeout(function() {
      message.style.display = 'none';
    }, 3000); // 3000 milliseconds = 3 seconds
  });
</script>
          </div>
        </div>
<form:form class="login-form" action="/OJT_Student_Spring_MVC/signup" method="post" modelAttribute="signupBean" onsubmit="return validateForm()">
  <form:input id="name" type="text" placeholder="User Name" name="name" path="name"></form:input>
  <form:input id="email" type="email" placeholder="Email" name="email" path="email"></form:input>
   <form:input id="rolel" type="text" placeholder="Role" name="role" value="user" style="display:none;" path="role"></form:input>
  <form:input id="password" type="password" placeholder="Password" name="password" path="password"></form:input>
  <input id="confirmpassword" type="password" placeholder="Confirm Password" />

  <button type="submit">Sign Up</button>
  <p class="message">Already Have An Account? <a href="/OJT_Student_Spring_MVC/">Login</a></p>
</form:form>
      </div>
    </div>
    <script>
    function validateForm(event) {
    	  var nameinput = document.getElementById("name").value;
    	  var emailinput = document.getElementById("email").value;
    	  var password = document.getElementById("password").value;
    	  var confirmpassword = document.getElementById("confirmpassword").value;
    	  var ValidationMsg = document.getElementById('ValidationMsg');
    	  
    	 



    	  if (nameinput.trim() === '' || emailinput.trim() === '' || password.trim() === '' || confirmpassword.trim() === '') {
    	    // input field is empty
    	    ValidationMsg.textContent = 'Please fill out all fields.';
    	    ValidationMsg.style.display = 'block';

    	    // hide the validation message after 3 seconds
    	    setTimeout(() => {
    	      ValidationMsg.style.display = 'none';
    	    }, 3000);

    	    return false;
    	  } 

    	  if (password !== confirmpassword) {
    	    ValidationMsg.textContent = 'Passwords do not match.';
    	    ValidationMsg.style.display = 'block';

    	    // hide the validation message after 3 seconds
    	    setTimeout(() => {
    	      ValidationMsg.style.display = 'none';
    	    }, 3000);

    	    return false;
    	  }

    	  if (password.length < 8 || password.length > 20) {
    	    ValidationMsg.textContent = 'Password must be between 8 and 20 characters long.';
    	    ValidationMsg.style.display = 'block';

    	    // hide the validation message after 3 seconds
    	    setTimeout(() => {
    	      ValidationMsg.style.display = 'none';
    	    }, 3000);

    	    return false;
    	  }

    	  if (!emailinput.endsWith("@gmail.com") || emailinput.indexOf('@') === -1 || emailinput.indexOf('@gmail.com') !== emailinput.length - 10) {
    	    ValidationMsg.textContent = 'Invalid email address.';
    	    ValidationMsg.style.display = 'block';

    	    setTimeout(() => {
    	      ValidationMsg.style.display = 'none';
    	    }, 3000);

    	    return false;
    	  }
    	  
    	  return true;
    	}

    

    </script>
</body>

</html>