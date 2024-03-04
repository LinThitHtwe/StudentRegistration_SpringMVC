<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
                 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">

<head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/OJT_Student_Spring_MVC/css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
    
        <title>Course Registration</title>
        
                <style>
        .badge {
  background: red;
  color: white;
  padding: 5px 10px;
  border-radius: 50%;
  margin-left: 5px; /* Added margin to separate the badge from the text */
}</style>
</head>

<body>
    <div id="testheader">
        <div class="container">
            <div class=row>        
                <div class="col-md-5 ">
            <a href="/OJT_Student_Spring_MVC/homePage"><h3>Student Registration</h3></a>
        </div>  
        <%
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yy-MM-dd");
        String formattedDate = sdf.format(date);
%>
        <div class="col-md-6">
        <c:if test="${adminRole == null }">
            <p>User: ${userName}</p></c:if>
                    <c:if test="${userRole == null }">
            <p>User: ${adminName}</p></c:if>
            <p>Current Date : <%= formattedDate  %></p>
        </div> 
        <div class="col-md-1" >
            <input type="button" class="btn-basic" id="lgnout-button" value="Log Out" onclick="window.location.href='/OJT_Student_Spring_MVC/logout'">
        </div>        
    </div>
</div>

</div>
    
    <div class="container">
    <div class="sidenav">
        
        <c:if test="${adminRole == null }">
        
        <button class="dropdown-btn" > Class Management <i class="fa fa-caret-down"></i></button>

        
            <div class="dropdown-container">

          <a href="/OJT_Student_Spring_MVC/setupaddstudent">Student Registration </a>
          <a href="/OJT_Student_Spring_MVC/studentTable">Student Search </a>
            <a href="/OJT_Student_Spring_MVC/studentPendingTable"> Pending Student</a>
  <a href="/OJT_Student_Spring_MVC/rejectedStudentTable"> Rejected Student</a>
        </div>
        <a href="/OJT_Student_Spring_MVC/setupeditprofile/${userId }">Edit Profile</a>
        </c:if>
        
              <c:if test="${userRole == null }">
<button class="dropdown-btn position-relative">
  Class Management
  <c:if test="${NotifactionstudentList != null && !NotifactionstudentList.isEmpty()}">
    <span class="badge">${NotifactionstudentList.size()}</span>
  </c:if>
  <i class="fa fa-caret-down"></i>
</button>
<div class="dropdown-container">
  <a href="/OJT_Student_Spring_MVC/setupaddCourse">Course Registration</a>
  <a href="/OJT_Student_Spring_MVC/courseTable">Course Search</a>
  <a href="/OJT_Student_Spring_MVC/setupaddstudent">Student Registration</a>
  <a href="/OJT_Student_Spring_MVC/studentTable">Student Table</a>
  <a href="/OJT_Student_Spring_MVC/studentPendingTable">
    Pending Student
    <c:if test="${NotifactionstudentList != null && !NotifactionstudentList.isEmpty()}">
      <span class="badge">${NotifactionstudentList.size()}</span>
    </c:if>
  </a>
  <a href="/OJT_Student_Spring_MVC/rejectedStudentTable">Rejected Student</a>
</div>

<button class="dropdown-btn">
  Users Management
  <i class="fa fa-caret-down"></i>
</button>
<div class="dropdown-container">
  <a href="/OJT_Student_Spring_MVC/userTable">User Table</a>
  <a href="/OJT_Student_Spring_MVC/banneduserTable">Banned User Table</a>
</div>

<a href="/OJT_Student_Spring_MVC/adminTable">Admin Management</a>
<a href="/OJT_Student_Spring_MVC/dashboard">Dashboard</a>
</c:if>
      </div>
      <div class="main_contents">
    <div id="sub_content">
        <form:form action="/OJT_Student_Spring_MVC/addAdmin" method="post" onsubmit="return validateForm(event)" modelAttribute="adminBean">

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Admin Registration</h2>
                        <div id="ValidationMsg" style="display:none;color:red;text-align:center;"></div>
                                    <div id="message" style="color:red;text-align:center;">${error}</div>

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
                        <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="email" class="col-md-2 col-form-label">Name</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="name" name="name" placeholder="Admin Name" path="name"></form:input>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="email" class="col-md-2 col-form-label">Email</label>
                <div class="col-md-4">
                    <form:input type="email" class="form-control" id="email" name="email" placeholder="Email" path="email"></form:input>
                </div>
            </div>
            
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="Passowrd" class="col-md-2 col-form-label">Passowrd</label>
                <div class="col-md-4"> 
                    <form:input type="password" class="form-control" id="password" name="password" placeholder="Password" path="password"></form:input>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="confirmPassword" class="col-md-2 col-form-label">Confirm Passowrd</label>
                <div class="col-md-4">
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password">
                </div>
            </div>
            <div class="row mb-4" style="display:none;">
                <div class="col-md-2"></div>
                <label for="userRole" class="col-md-2 col-form-label">User Role</label>
                <div class="col-md-4">
					<form:input type="password" class="form-control" id="role" value="admin" name="role" path="role"></form:input>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-4"></div>
    
                <div class="col-md-6">
                   
    
                    <button type="submit" class="btn btn-secondary col-md-2" >Add</button>
                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                        aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Student Registration</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                   
                                   <h5 style="color: rgb(127, 209, 131);">Registered Succesfully !</h5>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok</button>
                                   
                                </div>
                            </div>
                        </div>
                </div>
    
            </div>
            </form:form>
    </div>
</div>
</div>
        <div id="testfooter">
            <span>Copyright &#169; ACE Inspiration 2022</span>
        </div>
            <script>
    function validateForm(event) {
    	  var nameinput = document.getElementById("name").value;
    	  var emailinput = document.getElementById("email").value;
    	  var password = document.getElementById("password").value;
    	  var confirmpassword = document.getElementById("confirmPassword").value;
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
    
    
    function hasSpecialChar(str) {
    	  // define a regular expression to match special characters
    var regex = /[\s!@#$%^&*()_+='\[\]{};:\\|,.<>\/?]/;


    	  // test the input string against the regex
    	  return regex.test(str);
    	}
    </script>
        <script>
            /* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
            var dropdown = document.getElementsByClassName("dropdown-btn");
            var i;
            
            for (i = 0; i < dropdown.length; i++) {
              dropdown[i].addEventListener("click", function() {
              this.classList.toggle("active");
              var dropdownContent = this.nextElementSibling;
              if (dropdownContent.style.display === "block") {
              dropdownContent.style.display = "none";
              } else {
              dropdownContent.style.display = "block";
              }
              });
            }
            </script>
</body>

</html>

    