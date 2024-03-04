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
            <style>
        .badge {
  background: red;
  color: white;
  padding: 5px 10px;
  border-radius: 50%;
  margin-left: 5px; /* Added margin to separate the badge from the text */
}</style>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
    
        <title>Course Registration</title>
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
    <!-- <div id="testsidebar">Hello World </div> -->
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
        <form:form action="/OJT_Student_Spring_MVC/approveStudent" method="post" enctype="multipart/form-data" modelAttribute="studentBean" onsubmit="return validateForm()">

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student Registration</h2>
                        <div id="ValidationMsg" style="display:none;color:red;text-align:center"></div>
            <div  style="color:red;text-align:center">${error }</div>
                                    <div class="row mb-4" >
                <div class="col-md-2" ></div>
                <label for="name" class="col-md-2 col-form-label">Photo</label>
                <div class="col-md-4">
                      <img id="preview" src="data:image/png;base64,${studentphoto}" alt="Student Photo" style="width: 300px; height: 300px;">
                </div>
            </div>
          
                        <div class="row mb-4" style="display:none;">
                <div class="col-md-2" ></div>
                <label for="name" class="col-md-2 col-form-label">ID</label>
                <div class="col-md-4">
                    <form:input type="text" readonly="true" class="form-control" id="name" name="id" path="studentId" placeholder="Student's Name"></form:input>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">Name</label>
                <div class="col-md-4">
                    <form:input type="text" readonly="true" class="form-control" id="name" name="studentName" path="studentName" placeholder="Student's Name"></form:input>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="dob" class="col-md-2 col-form-label">DOB</label>
                <div class="col-md-4">
                  <form:input class="form-control" id="dob" name="dob" path="dateOfBirth" readonly="true" />


                </div>
            </div>
<fieldset class="row mb-4" disabled="disabled">
    <div class="col-md-2"></div>
    <legend class="col-form-label col-md-2 pt-0">Gender</legend>
    <div class="col-md-4">
        <div class="form-check-inline">
            <form:radiobutton class="form-check-input" id="gridRadios1" value="Male" path="gender" disabled="disabled"/>
            <label class="form-check-label" for="gridRadios1">
                Male
            </label>
        </div>
        <div class="form-check-inline">
            <form:radiobutton class="form-check-input" id="gridRadios2" value="Female" path="gender" disabled="disabled"/>
            <label class="form-check-label" for="gridRadios2">
                Female
            </label>
        </div>
    </div>
</fieldset>


    
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="phone" class="col-md-2 col-form-label">Phone</label>
                <div class="col-md-4">
<form:input type="text" class="form-control" readonly="true" id="phone" name="phone" path="phone" placeholder="Phone Number" oninput="this.value = this.value.replace(/[^0-9]/g, '')"></form:input>


                </div>
            </div>
<div class="row mb-4">
    <div class="col-md-2"></div>
    <label for="education" class="col-md-2 col-form-label">Education</label>
    <div class="col-md-4">
    <form:input type="text" readonly="true" class="form-control" id="education" name="education" path="education" placeholder="Student's Name"></form:input>

    </div>
</div>




<fieldset class="row mb-4">
    <div class="col-md-2"></div>
    <legend class="col-form-label col-md-2 pt-0">Attend</legend>
    <div class="col-md-4">
        <c:forEach items="${requestScope.courseListForCheckbox}" var="courseListForCheckbox">
            <div class="form-check-inline col-md-2">
                <input class="form-check-input" type="checkbox" name="course[]" id="gridRadios1" value="${courseListForCheckbox.courseId}"
                    ${selectedCourses.contains(courseListForCheckbox.courseName) ? 'checked' : ''} disabled>
                <label class="form-check-label" for="gridRadios1">
                    ${courseListForCheckbox.courseName}
                </label>
            </div>
        </c:forEach>
    </div>
</fieldset>



            <div class="row mb-4">
                <div class="col-md-4"></div>
    
                <div class="col-md-4">
<button type="button" class="btn btn-secondary col-md-2" onclick="window.location.href='/OJT_Student_Spring_MVC/studentPendingTable'">
                        Back
                    </button>
               <c:if test="${adminRole == null }">
<button type="button" class="btn btn-secondary col-md-2" onclick="redirectToSetupUpdate()">
    Edit Details
</button>
<script>
function redirectToSetupUpdate() {
    var currentURL = window.location.href;
    var userId = currentURL.substring(currentURL.lastIndexOf('/') + 1);
    var url = '/OJT_Student_Spring_MVC/setupupdateStudent/' + userId;
    window.location.href = url;
}
</script>
                    </c:if>
                    
            <c:if test="${userRole == null }">
                 
<button type="button" class="btn btn-secondary col-md-2"  data-bs-toggle="modal" data-bs-target="#exampleModal">
    Reject
</button>

<button type="submit" class="btn btn-secondary col-md-2" style="padding-right: 10px; white-space: normal;">
    Approve
</button>
</c:if>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Student Deletion</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <h5 style="color: rgb(127, 209, 131);">Are you sure you want to reject this student? </h5>
      </div>
      <div class="modal-footer">
<button id="rejectBtn" class="btn btn-success col-md-2" onclick="return redirectToReject()" style="padding: 10px 40px; font-size: 18px; text-align: center;">Ok</button>


<script>
function redirectToReject() {
	  var currentURL = window.location.href;
	  var userId = currentURL.substring(currentURL.lastIndexOf('/') + 1);
	  var url = '/OJT_Student_Spring_MVC/rejectStudent/' + userId;
	  window.location.href = url;
	  return false; // Prevent the form submission
	}

</script>

      </div>
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
        
    	function readFile(input) {
    		let file = input.files[0];
    		console.log()
    		let photoDataElement = document.getElementById("photo-data");

    		let reader = new FileReader();

    		reader.readAsDataURL(file);

    		reader.onload = function() {
    			console.log(reader.result);
    			photoDataElement.value = reader.result;
    		};

    		reader.onerror = function() {
    			console.log(reader.error);
    		};

    	}
        
        function disableCheckbox() {
        	  return false; // Returns false to prevent the checkbox from being checked
        	}
function validateForm() {
  var nameinput = document.getElementById("name").value;
  var phone = document.getElementById("phone").value;
  var ValidationMsg = document.getElementById('ValidationMsg');

  if (nameinput.trim() === '' || phone.trim() === '') {
    // input field is empty
    ValidationMsg.textContent = 'Please fill out all fields.';
    ValidationMsg.style.display = 'block';

    // hide the validation message after 3 seconds
    setTimeout(() => {
      ValidationMsg.style.display = 'none';
    }, 3000);

    return false;
  }

  var radioInputs = document.getElementsByName('gender');
  var radioSelected = false;
  for (var i = 0; i < radioInputs.length; i++) {
    if (radioInputs[i].checked) {
      radioSelected = true;
      break;
    }
  }
  if (!radioSelected) {
    ValidationMsg.textContent = 'RadioButton Should Be Fill fill';
    ValidationMsg.style.display = 'block';

    // hide the validation message after 3 seconds
    setTimeout(() => {
      ValidationMsg.style.display = 'none';
    }, 3000);

    return false;
  }

  var checkboxInputs = document.getElementsByName('course[]');
  var checkboxChecked = false;
  for (var i = 0; i < checkboxInputs.length; i++) {
    if (checkboxInputs[i].checked) {
      checkboxChecked = true;
      break;
    }
  }





  let dateInput = document.getElementById("dob").value;

  // Date Input Validation
  if (dateInput.trim() === '') {
    ValidationMsg.textContent = 'Date of Birth Required';
    ValidationMsg.style.display = 'block';

    // hide the validation message after 3 seconds
    setTimeout(() => {
      ValidationMsg.style.display = 'none';
    }, 3000);

    return false;
  }

  return true;
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

