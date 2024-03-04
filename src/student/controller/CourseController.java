package student.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import student.dao.CourseDAO;
import student.dao.StudentTableDAO;
import student.dto.CourseRequestDTO;
import student.dto.CourseResponseDTO;
import student.dto.StudentAccountCourseResponseDTO;
import student.model.CourseBean;

@Controller
public class CourseController {

	@Autowired
	private CourseDAO courseDAO;
	@Autowired
	private StudentTableDAO studentTableDAO;

	
    private Timer timer;

    @RequestMapping(value = "/startTask", method = RequestMethod.GET)
    public String startTask() {
        timer = new Timer();
        timer.schedule(new UpdateTask(), 30000); // 1 minute delay

        // Return the appropriate view or redirect
        return "redirect:/homePage";
    }
    
    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
 System.out.print("Test");
        }
    }
	
	@RequestMapping(value = "/setupaddCourse",method = RequestMethod.GET)
	public ModelAndView setupaddCourse(HttpSession session) {	
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return new ModelAndView("errorPage","courseBean",new CourseBean());
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
		return new ModelAndView("CourseRegister","courseBean",new CourseBean());
	}
	
	@RequestMapping(value = "/addCourse",method = RequestMethod.POST)
	public String addCourse(@ModelAttribute("courseBean")@Validated CourseBean bean,BindingResult result,ModelMap model,HttpSession session) {
	
	ArrayList<CourseResponseDTO> datalist = courseDAO.selectOneName(bean.getCourseName());		
	Iterator<CourseResponseDTO> it=datalist.iterator();
	while(it.hasNext()) {
		CourseResponseDTO dto = it.next();
	    System.out.println(bean.getCourseName() + "," + bean.getCourseName());
	    if(bean.getCourseName().equals(dto.getCourseName())) {
	        model.addAttribute("error", "Course Already Exist");

	        return "CourseRegister"; // stop the rest of the code from executing
	    }
	}
		
		CourseRequestDTO dto = new CourseRequestDTO();
		dto.setCourseName(bean.getCourseName());
		courseDAO.insertData(dto);
		return "redirect:/homePage";
	
	}
	
	@RequestMapping(value = "/courseTable",method = RequestMethod.GET)
	public String courseTable(ModelMap model,HttpSession session) {
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
	    
		ArrayList<CourseResponseDTO> courseList = courseDAO.selectAll();
		model.addAttribute("courseList", courseList);	
		return "courseTable";
	}

	@RequestMapping(value = "/setupUpdateCourse/{id}",method = RequestMethod.GET)
	public ModelAndView setupUpdateCourse(HttpSession session,@PathVariable String id,ModelMap model) {	
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return new ModelAndView("errorPage","courseBean",new CourseBean());
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
		CourseRequestDTO req = new CourseRequestDTO();
		req.setCourseId(id);
		
	    
		return new ModelAndView("CourseUpdate","courseBean",courseDAO.selectOne(req));
	}
	
	@RequestMapping(value = "/updateCourse",method = RequestMethod.POST)
	public String updateCourse(@ModelAttribute("courseBean")@Validated CourseBean bean,
			BindingResult result,ModelMap model,HttpSession session) {
		CourseRequestDTO req = new CourseRequestDTO();
		CourseDAO dao = new CourseDAO();
		req.setCourseId(bean.getCourseId());
		req.setCourseName(bean.getCourseName());
		dao.updateData(req);
		return "redirect:/courseTable";
	}
	
}
