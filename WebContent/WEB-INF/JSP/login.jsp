<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
          <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/OJT_Student_Spring_MVC/css">
<title> Student Registration LGN001 </title>
</head>
<body class="login-page-body"> 
  
    <div class="login-page">
      <div class="form">
        <div class="login">
          <div class="login-header">
            <h1>Welcome!</h1>
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
        <form:form class="login-form" action="/OJT_Student_Spring_MVC/login" method="post" name="confirm" modelAttribute="loginBean" onsubmit="return validateForm()">
          <form:input type="email" id="email" name="email" placeholder="email" path="email"/>
          <form:input type="password" id="password" name="password" placeholder="Password"  path="password"/>
          <button>login</button>
          <p class="message">Not registered? <a href="/OJT_Student_Spring_MVC/setupsignup">Create an account</a></p>
        </form:form>
      </div>
    </div>
    
    
    <script>
    function validateForm(event) {
    	  var emailinput = document.getElementById("email").value;
    	  var password = document.getElementById("password").value;
    	  var ValidationMsg = document.getElementById('ValidationMsg');
    	  
    	 



    	  if (emailinput.trim() === '' || password.trim() === '') {
    	    // input field is empty
    	    ValidationMsg.textContent = 'Please fill out fields.';
    	    ValidationMsg.style.display = 'block';

    	    // hide the validation message after 3 seconds
    	    setTimeout(() => {
    	      ValidationMsg.style.display = 'none';
    	    }, 3000);

    	    return false;
    	  } 






    	  
    	  return true;
    	}
    
    
    function hasSpecialChar(str) {
    	  // define a regular expression to match special characters
    var regex = /[\s!@#$%^&*()_+='\[\]{};:\\|,.<>\/?]/;


    	  // test the input string against the regex
    	  return regex.test(str);
    	}
    </script>
    
</body>

</html>