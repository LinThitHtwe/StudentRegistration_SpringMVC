<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
                 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
        <form class="row g-3 mt-3 ms-2" action="SearchUserServlet" method="post">
            <div class="col-auto">
                <label for="staticEmail2" class="visually-hidden">User Id</label>
                <input type="text" oninput="this.value = this.value.replace(/[^0-9]/g, '')" class="form-control" name="searchUserId" id="searchUserId" placeholder="User ID">
            </div>
            <div class="col-auto">
                <label for="inputPassword2" class="visually-hidden">User Name</label>
                <input type="text" class="form-control" name="searchUserName" id="searchUserName" placeholder="User Name">
            </div>
    
  <div class="col-auto">
    <button type="submit" class="btn btn-primary mb-3" id="searchBtn" disabled>Search</button>
  </div>
            <div class="col-auto">
                <button type="button" class="btn btn-secondary " onclick="location.href = 'userRegistration.jsp';">
                    Add
                </button>
    
            </div>
            <div class="col-auto">
                <button type="button" class="btn btn-danger mb-3" onclick="location.reload()">Reset</button>

            </div>
        </form>
        
                <script>
  const searchUserId = document.getElementById('searchUserId');
  const searchUserName = document.getElementById('searchUserName');
  const searchBtn = document.getElementById('searchBtn');

  function toggleSearchBtn() {
    if (searchUserId.value.trim() !== '' || searchUserName.value.trim() !== '') {
      searchBtn.disabled = false;
    } else {
      searchBtn.disabled = true;
    }
  }

  searchUserId.addEventListener('input', toggleSearchBtn);
  searchUserName.addEventListener('input', toggleSearchBtn);
</script>
    
        <table class="table table-striped" id="stduentTable">
            <thead>
                <tr>
                    
                    <th scope="col">User ID</th>
                    <th scope="col">User Name</th>
                    <th scope="col">Actions</th>
                    
                </tr>
            </thead>
            
             <c:forEach items="${requestScope.userList}" var="userList">
            <tbody>
                <tr>
    
                    
                    <td>${userList.id }</td>
                    <td>${userList.name}</td>
                    
                <td>
<div class="d-flex justify-content-start">
<c:if test="${adminRole == null }">
  <button type="button" class="btn btn-success me-2" onclick="location.href = '';">Update</button></c:if>
   <c:if test="${userRole == null }">
 <a href="/OJT_Student_Spring_MVC/unbanUser/${userList.id}" class="btn btn-success me-2" id="deleteLink" data-bs-toggle="modal" data-bs-target="#exampleModal${userList.id}">UnBan</a>
<div class="modal fade" id="exampleModal${userList.id}" tabindex="-1" aria-labelledby="exampleModalLabel${userList.id}" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Student Deletion</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <h5 style="color: rgb(127, 209, 131);">Are you sure you want to unban <label style="color:red;">${userList.name }</label>? </h5>
      </div>
      <div class="modal-footer">
        <form action="/OJT_Student_Spring_MVC/unbanUser/${userList.id}" method="GET">
          <input type="hidden" name="code" value="${userList.id}">
          <button type="submit" class="btn btn-success col-md-2" style="padding: 10px 40px; font-size: 18px; text-align: center;">Ok</button>


        </form>
      </div>
    </div>
  </div>
</div>
  </c:if>
</div>
                </td>


    
            </tbody>
            </c:forEach>
                              		<c:set var="userList" value="${fn:length(userList)}"/>
		<c:if test="${userList < 1}">
		<tr><td colspan="3" style="font-size: 25px;text-align:center;">Empty</td></tr>
		</c:if>
        </table>
    
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Student Deletion</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <h5 style="color: rgb(127, 209, 131);">Are you sure you want to delete?</h5>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal" id="deleteBtn">Ok</button>
      </div>
    </div>
  </div>
</div>




<script>
  // Get the delete link and the delete button from the modal
  const deleteLink = document.getElementById('deleteLink');
  const deleteBtn = document.getElementById('deleteBtn');

  // Add a click event listener to the delete button
  deleteBtn.addEventListener('click', function() {
    // Simulate clicking the delete link when the button is clicked
    deleteLink.click();
  });
</script>




    </div>
</div>
</div>
        <div id="testfooter">
            <span>Copyright &#169; ACE Inspiration 2022</span>
        </div>
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

    


    


<body>
    
</body>

