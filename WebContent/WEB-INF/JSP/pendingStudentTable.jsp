<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
         <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="java.util.Base64" %>

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
            <style>
        .badge {
  background: red;
  color: white;
  padding: 5px 10px;
  border-radius: 50%;
  margin-left: 5px; /* Added margin to separate the badge from the text */
}</style>
        <title>Course Registration</title>
        
        <style>
  .notification-dot {
    width: 8px;
    height: 8px;
    background-color: red;
    border-radius: 50%;
    display: inline-block;
    margin-left: 5px;



        
        </style>
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
<form class="row g-3 mt-3 ms-2">
  <div class="col-auto">
    <label for="staticEmail2" class="visually-hidden">Student Name</label>
    <input type="text" class="form-control" id="staticEmail2" placeholder="Student Name">
  </div>
  <div class="col-auto">
    <label for="inputPassword2" class="visually-hidden">Course Name</label>
    <input type="text" class="form-control" id="inputPassword2" placeholder="Course Name">
  </div>

  <div class="col-auto">
    <button type="button" class="btn btn-primary mb-3" onclick="filterTable()">Search</button>
  </div>
  <div class="col-auto">
    <button type="button" class="btn btn-secondary " onclick="window.location.href = '/OJT_Student_Spring_MVC/setupaddstudent';">
      Add
    </button>
  </div>
  <div class="col-auto">
    <button type="button" class="btn btn-danger mb-3" onclick="window.location.reload()">Reset</button>
  </div>
</form>
      
      


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
  // Get the table element
  var table = $('#studentTable');

  // Check if the table has any rows excluding the header row
  if (table.find('tbody tr').length > 0) {
    // Display the notification for Class Management
    $('#classNotification').html('<span class="notification-dot"></span>');

    // Display the notification for Student Search
    $('#studentNotification').html('<span class="notification-dot"></span>');
  }
});
</script>


<div>
      <table class="table table-striped" id="stduentTable">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Name</th>
            <th scope="col">Course Name</th>
            <th scope="col">Photo</th>
            <th scope="col">Details</th>
          </tr>
        </thead>
<tbody>
  <c:forEach items="${requestScope.studentList}" var="studentList">
    <tr>
      <th scope="row">${studentList.student_id}</th>
      <td>${studentList.studentName}</td> 
      <td>${studentList.courseName}</td> 
      <td>
        <img src="data:image/png;base64,${studentList.student_photo}" alt="Student Photo" style="width: 40px; height: 40px;">
      </td>
      <td>

          <button type="button" class="btn btn-secondary mb-2" onclick="window.location.href='/OJT_Student_Spring_MVC/setupApproveStudent/${studentList.student_id}'">See More</button>
 
      </td>
    </tr>
    

  </c:forEach>
  
                  		<c:set var="studentList" value="${fn:length(studentList)}"/>
		<c:if test="${studentList < 1}">
		<tr><td colspan="5" style="font-size: 25px;text-align:center;">Empty</td></tr>
		</c:if>
		
		
</tbody>


      </table>
      
              <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Student Deletion</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        
                         <h5 style="color: rgb(127, 209, 131);">Are you sure want to delete !</h5>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok</button>
    
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</div>
</div>

        <div id="testfooter">
            <span>Copyright &#169; ACE Inspiration 2022</span>
        </div>




        

<script>
  document.addEventListener("DOMContentLoaded", function() {
    var userIdInput = document.getElementById("staticEmail2");
    var userNameInput = document.getElementById("inputPassword2");
    var tableRows = document.querySelectorAll("#stduentTable tbody tr");
    var searchMessage = document.getElementById("searchMessage");

    userIdInput.addEventListener("input", filterTable);
    userNameInput.addEventListener("input", filterTable);

    function filterTable() {
      var filterUserId = userIdInput.value.trim().toLowerCase();
      var filterUserName = userNameInput.value.trim().toLowerCase();

      var foundMatch = false;

      tableRows.forEach(function(row) {
        var userId = row.querySelector("td:nth-child(2)").innerText.toLowerCase();
        var userName = row.querySelector("td:nth-child(3)").innerText.toLowerCase();

        if (
          userId.includes(filterUserId) &&
          userName.includes(filterUserName)
        ) {
          row.style.display = "";
          foundMatch = true;
        } else {
          row.style.display = "none";
        }
      });

      if (foundMatch) {
        searchMessage.style.display = "none";
      } else {
        searchMessage.style.display = "";
      }
    }
  });
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



