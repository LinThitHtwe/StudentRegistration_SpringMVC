package student.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import student.dao.DashBoardDAO;
import student.dao.StudentTableDAO;
import student.dto.CourseCountResponseDTO;
import student.dto.EducationCount;
import student.dto.GenderCountResponseDTO;
import student.dto.StudentAccountCourseResponseDTO;
import student.model.CourseBean;

@Controller
public class DashboardController {
	@Autowired
	private DashBoardDAO dashBoardDAO;
	@Autowired
	private StudentTableDAO studentTableDAO;
	
	@RequestMapping(value = "/dashboard",method = RequestMethod.GET)
	public String dashboard(ModelMap model,HttpSession session) {
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
	    
		ArrayList<GenderCountResponseDTO> genderCount = dashBoardDAO.genderCount();
		ArrayList<EducationCount> educationCount = dashBoardDAO.educationCount();
		model.addAttribute("AverageAge", dashBoardDAO.AverageAge());
		model.addAttribute("Male",genderCount.get(0).getGender());
		model.addAttribute("MaleCount",genderCount.get(0).getCount());
		model.addAttribute("Female",genderCount.get(1).getGender());
		model.addAttribute("FemaleCount",genderCount.get(1).getCount());
		
		model.addAttribute("BCS",educationCount.get(0).getEducation());
		model.addAttribute("BCSCount",educationCount.get(0).getCount());
		model.addAttribute("DIT",educationCount.get(1).getEducation());
		model.addAttribute("DITCount",educationCount.get(1).getCount());
		model.addAttribute("BIT",educationCount.get(2).getEducation());
		model.addAttribute("BITCount",educationCount.get(2).getCount());
//		for(EducationCount ec: educationCount){
//			System.out.print(ec.getEducation()+"--"+ec.getCount());
//		}
		//System.out.print("BCS--"+educationCount.get(0).getEducation()+"--BCS--"+educationCount.get(0).getCount());
		ArrayList<CourseCountResponseDTO> courseCount = dashBoardDAO.courseCount();
		for (CourseCountResponseDTO cc : courseCount) {
		    String attributeName = cc.getCourse();
		    int attributeCount = cc.getCount();
		    model.addAttribute(attributeName, attributeName);
		    model.addAttribute(attributeName + "Count", attributeCount);
		}

		for (int i = 0; i < courseCount.size(); i++) {
		    String attributeName = "course_" + i;
		    String attributeCount = "courseCount_" + i;
		    model.addAttribute(attributeName, courseCount.get(i).getCourse());
		    model.addAttribute(attributeCount, courseCount.get(i).getCount());
		}

		
		return"dashboard";
		
	}
	
	
	
}
