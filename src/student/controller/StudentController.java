package student.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import student.dao.CourseDAO;
import student.dto.AccountRequestDTO;
import student.dto.CourseResponseDTO;
import student.model.StudentBean;
import student.dao.StudentTableDAO;
import student.dto.StudentAccountCourseResponseDTO;
import student.dao.StudentAccountDAO;
import student.dao.StudentCourseDAO;
import student.dao.StudentDAO;
import student.dto.StudentAccountRequestDTO;
import student.dto.StudentCourseRequestDTO;
import student.dto.StudentRequestDTO;
import student.dto.StudentResponseDTO;

@Controller
public class StudentController {
	@Autowired
	private CourseDAO courseDAO;
	@Autowired
	private StudentTableDAO studentTableDAO;
	@Autowired
	private StudentDAO studentDAO;
	@Autowired
	private StudentAccountDAO studentAccountDAO;
	
	@RequestMapping(value = "/deleteStudent/{id}",method = RequestMethod.GET)
	public String deleteStudent(@PathVariable String id) {
		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setStudentId(Integer.parseInt(id));
		studentDAO.deleteData(dto);

		return "redirect:/studentTable";
	}
	
	@RequestMapping(value = "/setupaddstudent",method = RequestMethod.GET)
	public ModelAndView setupaddstudent(ModelMap model,HttpSession session) {
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return new ModelAndView("errorPage");
	    }
		if(session.getAttribute("adminId")==null) {
			System.out.print("If");
			int userId = Integer.parseInt((session.getAttribute("userId").toString()));
			ArrayList<StudentAccountCourseResponseDTO> studentList = studentTableDAO.selectAllPendingStudentTableUser(userId);
			session.setAttribute("NotifactionstudentList", studentList);
			
		}
		else {
			System.out.print("Else");
			ArrayList<StudentAccountCourseResponseDTO> allstudentList = studentTableDAO.adminSelectAllPendingStudentTable();
			session.setAttribute("NotifactionstudentList", allstudentList);
		}
		
		ArrayList<CourseResponseDTO> courseList = courseDAO.selectAll();
		model.addAttribute("courseListForCheckbox", courseList);
		return new ModelAndView("AddStudent","studentBean", new StudentBean());
		
	}
	
	@RequestMapping(value = "/addstudent",method = RequestMethod.POST)
	public String addstudent(@ModelAttribute("studentBean")@Validated StudentBean bean,ModelMap model,HttpSession session,@RequestParam("course[]") int[] course) {
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return "errorPage";
	    }	    
	    
	    MultipartFile filePart = bean.getPhoto();
	    byte[] photoBytes = null;

	    if (filePart != null && !filePart.isEmpty()) {
	        try (InputStream photoStream = filePart.getInputStream();
	             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            while ((bytesRead = photoStream.read(buffer)) != -1) {
	                baos.write(buffer, 0, bytesRead);
	            }
	            photoBytes = baos.toByteArray();
	        } catch (Exception e) {
	            // Handle exception if any error occurs during file processing
	            e.printStackTrace();
	        }
	    }

	    if (photoBytes != null) {
	        
	        System.out.print("yes");
	    } else {
	        System.out.print("no");
	    }
	    String base64EncodedPhoto = Base64.getEncoder().encodeToString(photoBytes);

		
		StudentDAO studentDAO = new StudentDAO();
		StudentRequestDTO studreq = new StudentRequestDTO();
		studreq.setStudentName(bean.getStudentName());
		studreq.setDateOfBirth(bean.getDateOfBirth());
		studreq.setGender(bean.getGender());
		studreq.setPhone(bean.getPhone());
		studreq.setEducation(bean.getEducation());
		studreq.setPhoto(base64EncodedPhoto);
		studentDAO.insertData(studreq);
		
		StudentResponseDTO studres = new StudentResponseDTO();
		studres  = studentDAO.selectOneStudentByName(studreq);
		int studentID = studres.getStudentId();
		StudentCourseDAO studentCourseDAO = new StudentCourseDAO();
		for(int i:course) {
			StudentCourseRequestDTO studcou = new StudentCourseRequestDTO();
			studcou.setStudentId(studentID);
			studcou.setCourseId(i);
			studentCourseDAO.insertData(studcou);
		}

		StudentAccountRequestDTO stuaccReq = new StudentAccountRequestDTO();
		System.out.print(session.getAttribute("userId"));
		if(session.getAttribute("adminId")==null || session.getAttribute("adminId").equals("")) {
		stuaccReq.setAccount_id(Integer.parseInt(((session.getAttribute("userId").toString()))));
		stuaccReq.setStatus(0);
		}
		
		if(session.getAttribute("userId")==null || session.getAttribute("userId").equals("")) {
			stuaccReq.setAccount_id(Integer.parseInt(((session.getAttribute("adminId").toString()))));
			stuaccReq.setStatus(1);
		}
		stuaccReq.setStudent_id(studentID);
		studentAccountDAO.insertData(stuaccReq);
	
		return "redirect:/homePage";
		
	}
	
	
	
	@RequestMapping(value = "/studentTable",method = RequestMethod.GET)
	public String studentTable(HttpSession session,ModelMap model) {
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return "errorPage";
	    }
		if(session.getAttribute("adminId")==null) {
			System.out.print("If");
			int userId = Integer.parseInt((session.getAttribute("userId").toString()));
			ArrayList<StudentAccountCourseResponseDTO> studentList = studentTableDAO.selectAllPendingStudentTableUser(userId);
			session.setAttribute("NotifactionstudentList", studentList);
			
		}
		else {
			System.out.print("Else");
			ArrayList<StudentAccountCourseResponseDTO> allstudentList = studentTableDAO.adminSelectAllPendingStudentTable();
			session.setAttribute("NotifactionstudentList", allstudentList);
		}
		if(session.getAttribute("adminId")==null) {
			System.out.print("If");
			int userId = Integer.parseInt((session.getAttribute("userId").toString()));
			ArrayList<StudentAccountCourseResponseDTO> studentList = studentTableDAO.selectAllStudentTable(userId);
			model.addAttribute("studentList", studentList);
			return "studentTable";
			
		}
		else {
			System.out.print("Else");
			ArrayList<StudentAccountCourseResponseDTO> allstudentList = studentTableDAO.adminSelectAllStudentTable();
			model.addAttribute("studentList", allstudentList);
			return "studentTable";
		}
	}
	
	@RequestMapping(value = "/studentPendingTable",method = RequestMethod.GET)
	public String studentPendingTable(HttpSession session,ModelMap model) {
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return "errorPage";
	    }
		if(session.getAttribute("adminId")==null) {
			System.out.print("If");
			int userId = Integer.parseInt((session.getAttribute("userId").toString()));
			ArrayList<StudentAccountCourseResponseDTO> studentList = studentTableDAO.selectAllPendingStudentTableUser(userId);
			session.setAttribute("NotifactionstudentList", studentList);
			
		}
		else {
			System.out.print("Else");
			ArrayList<StudentAccountCourseResponseDTO> allstudentList = studentTableDAO.adminSelectAllPendingStudentTable();
			session.setAttribute("NotifactionstudentList", allstudentList);
		}
		if(session.getAttribute("adminId")==null) {
			System.out.print("If");
			int userId = Integer.parseInt((session.getAttribute("userId").toString()));
			ArrayList<StudentAccountCourseResponseDTO> studentList = studentTableDAO.selectAllPendingStudentTableUser(userId);
			model.addAttribute("studentList", studentList);
			return "pendingStudentTable";
			
		}
		else {
			System.out.print("Else");
			ArrayList<StudentAccountCourseResponseDTO> allstudentList = studentTableDAO.adminSelectAllPendingStudentTable();
			model.addAttribute("studentList", allstudentList);
			return "pendingStudentTable";
		}
	}
	
	@RequestMapping(value = "/rejectedStudentTable",method = RequestMethod.GET)
	public String rejectedStudentTable(HttpSession session,ModelMap model) {
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return "errorPage";
	    }
		if(session.getAttribute("adminId")==null) {
			System.out.print("If");
			int userId = Integer.parseInt((session.getAttribute("userId").toString()));
			ArrayList<StudentAccountCourseResponseDTO> studentList = studentTableDAO.selectAllPendingStudentTableUser(userId);
			session.setAttribute("NotifactionstudentList", studentList);
			
		}
		else {
			System.out.print("Else");
			ArrayList<StudentAccountCourseResponseDTO> allstudentList = studentTableDAO.adminSelectAllPendingStudentTable();
			session.setAttribute("NotifactionstudentList", allstudentList);
		}
		if(session.getAttribute("adminId")==null) {
			System.out.print("If");
			int userId = Integer.parseInt((session.getAttribute("userId").toString()));
			ArrayList<StudentAccountCourseResponseDTO> studentList = studentTableDAO.selectAllDeletedStudentTableUser(userId);
			model.addAttribute("studentList", studentList);
			return "rejectedStudentTable";
			
		}
		else {
			System.out.print("Else");
			ArrayList<StudentAccountCourseResponseDTO> allstudentList = studentTableDAO.adminSelectAllDeletedStudentTable();
			model.addAttribute("studentList", allstudentList);
			return "rejectedStudentTable";
		}
	}
	
	@RequestMapping(value = "/setupApproveStudent/{id}",method = RequestMethod.GET)
	public ModelAndView setupApproveStudent(@PathVariable String id,HttpSession session,ModelMap model) {
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return new ModelAndView("errorPage","studentBean",new StudentBean());
	    }
		if(session.getAttribute("adminId")==null) {
			System.out.print("If");
			int userId = Integer.parseInt((session.getAttribute("userId").toString()));
			ArrayList<StudentAccountCourseResponseDTO> studentList = studentTableDAO.selectAllPendingStudentTableUser(userId);
			session.setAttribute("NotifactionstudentList", studentList);
			
		}
		else {
			System.out.print("Else");
			ArrayList<StudentAccountCourseResponseDTO> allstudentList = studentTableDAO.adminSelectAllPendingStudentTable();
			session.setAttribute("NotifactionstudentList", allstudentList);
		}
		ArrayList<CourseResponseDTO> courseList = courseDAO.selectAll();
		model.addAttribute("courseListForCheckbox", courseList);
	    StudentAccountCourseResponseDTO sacdto = new StudentAccountCourseResponseDTO();
	    sacdto = studentTableDAO.searchByStudentIDForUpdate(Integer.parseInt(id));
		String courseNames = sacdto.getCourseName();
		String[] selectedCourses = courseNames.split(",");
		Set<String> selectedCoursesSet = new HashSet<>(Arrays.asList(selectedCourses));
		model.addAttribute("selectedCourses", selectedCoursesSet);
	    
		StudentRequestDTO req = new StudentRequestDTO();
		req.setStudentId(Integer.parseInt(id));
		
		StudentResponseDTO res = new StudentResponseDTO();
		res = studentDAO.selectOneStudent(req);
		model.addAttribute("studentphoto",res.getPhoto());

	    return new ModelAndView("studentApprove","studentBean", studentDAO.selectOneStudent(req));
		
	}
	
	@RequestMapping(value = "/approveStudent",method = RequestMethod.POST)
	public String approveStudent(HttpSession session,@ModelAttribute("studentBean")StudentBean bean) {
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return "errorPage";
	    }
	    studentAccountDAO.approveUser(Integer.parseInt(bean.getStudentId()));
	    System.out.print("Approve");
	    
	    return "redirect:/studentTable";
	}
	
	
	@RequestMapping(value = "/rejectStudent/{id}", method = RequestMethod.GET)
	public String rejectStudent(
			@PathVariable String id,
	    HttpSession session
	) {
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return "errorPage";
	    }
	    studentAccountDAO.rejectUser(Integer.parseInt(id));
	    System.out.print("Reject");
	    
	    return "redirect:/studentTable";
	}

	
	
	
	@RequestMapping(value = "/setupupdateStudent/{id}",method = RequestMethod.GET)
	public ModelAndView setupupdateStudent(@PathVariable String id,HttpSession session,ModelMap model) {
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return new ModelAndView("errorPage");
	    }
		if(session.getAttribute("adminId")==null) {
			System.out.print("If");
			int userId = Integer.parseInt((session.getAttribute("userId").toString()));
			ArrayList<StudentAccountCourseResponseDTO> studentList = studentTableDAO.selectAllPendingStudentTableUser(userId);
			session.setAttribute("NotifactionstudentList", studentList);
			
		}
		else {
			System.out.print("Else");
			ArrayList<StudentAccountCourseResponseDTO> allstudentList = studentTableDAO.adminSelectAllPendingStudentTable();
			session.setAttribute("NotifactionstudentList", allstudentList);
		}
	    
		ArrayList<CourseResponseDTO> courseList = courseDAO.selectAll();
		model.addAttribute("courseListForCheckbox", courseList);
	    StudentAccountCourseResponseDTO sacdto = new StudentAccountCourseResponseDTO();
	    sacdto = studentTableDAO.searchByStudentIDForUpdate(Integer.parseInt(id));
		String courseNames = sacdto.getCourseName();
		String[] selectedCourses = courseNames.split(",");
		Set<String> selectedCoursesSet = new HashSet<>(Arrays.asList(selectedCourses));
		model.addAttribute("selectedCourses", selectedCoursesSet);
	    
		StudentRequestDTO req = new StudentRequestDTO();
		req.setStudentId(Integer.parseInt(id));
		
		StudentResponseDTO res = new StudentResponseDTO();
		res = studentDAO.selectOneStudent(req);
		model.addAttribute("studentphoto",res.getPhoto());

	    return new ModelAndView("updateStudent","studentBean", studentDAO.selectOneStudent(req));
		
	}
	
	@RequestMapping(value = "/updateStudent",method = RequestMethod.POST)
	public String updateStudent(HttpSession session,@ModelAttribute("studentBean")StudentBean bean,@RequestParam("photo") MultipartFile filePart,@RequestParam("course[]") int[] course) {

		if(filePart!=null && filePart.getSize() > 0) {
			System.out.print("If");
		byte[] photoBytes = null;

	    if (filePart != null && !filePart.isEmpty()) {
	        try (InputStream photoStream = filePart.getInputStream();
	             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            while ((bytesRead = photoStream.read(buffer)) != -1) {
	                baos.write(buffer, 0, bytesRead);
	            }
	            photoBytes = baos.toByteArray();
	        } catch (Exception e) {
	            // Handle exception if any error occurs during file processing
	            e.printStackTrace();
	        }
	    }

	    if (photoBytes != null) {
	        
	        System.out.print("yes");
	    } else {
	        System.out.print("no");
	    }
	    String base64EncodedPhoto = Base64.getEncoder().encodeToString(photoBytes);



		
		StudentDAO studentDAO = new StudentDAO();
		StudentRequestDTO studreq = new StudentRequestDTO();
		studreq.setStudentId(Integer.parseInt(bean.getStudentId()));
		studreq.setStudentName(bean.getStudentName());
		studreq.setDateOfBirth(bean.getDateOfBirth());
		studreq.setGender(bean.getGender());
		studreq.setPhone(bean.getPhone());
		studreq.setEducation(bean.getEducation());
		studreq.setPhoto(base64EncodedPhoto);
		studentDAO.updateData(studreq);
		
		StudentCourseDAO scDAO = new StudentCourseDAO();
		scDAO.insertStudentsCourses(Integer.parseInt(bean.getStudentId()),course);
		
		return"redirect:/studentTable";
		
		
		
		
		}
		
		else {
			System.out.print("else");
			StudentDAO studentDAO = new StudentDAO();
			StudentRequestDTO studreq = new StudentRequestDTO();
			studreq.setStudentId(Integer.parseInt(bean.getStudentId()));
			studreq.setStudentName(bean.getStudentName());
			studreq.setDateOfBirth(bean.getDateOfBirth());
			studreq.setGender(bean.getGender());
			studreq.setPhone(bean.getPhone());
			studreq.setEducation(bean.getEducation());
			studentDAO.updateDataWithoutPhoto(studreq);
			
			StudentCourseDAO scDAO = new StudentCourseDAO();
			scDAO.insertStudentsCourses(Integer.parseInt(bean.getStudentId()), course);
			
			return"redirect:/studentTable";
			
		}

	}
}
