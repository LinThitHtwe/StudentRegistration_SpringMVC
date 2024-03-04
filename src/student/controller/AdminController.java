package student.controller;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import student.dao.AccountDAO;
import student.dao.StudentTableDAO;
import student.dto.AccountRequestDTO;
import student.dto.AccountResponseDTO;
import student.dto.StudentAccountCourseResponseDTO;
import student.model.CourseBean;
import student.model.UserBean;

@Controller
public class AdminController {

	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private StudentTableDAO studentTableDAO;
	
	@RequestMapping(value = "/deleteAdmin/{id}",method = RequestMethod.GET)
	public String deleteAdmin(@PathVariable("id") String id) {
		AccountRequestDTO dto = new AccountRequestDTO();
		dto.setId(Integer.parseInt(id));
		accountDAO.deleteData(dto);

		
		return "redirect:/adminTable";
	}
	
	@RequestMapping(value = "/adminTable",method = RequestMethod.GET)
	public String adminTable(ModelMap model,HttpSession session) {
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
	    
	    ArrayList<AccountResponseDTO> adminList = accountDAO.selectAllAdmin();
		model.addAttribute("adminList", adminList);	
		return "adminTable";
	    
	}
	
	@RequestMapping(value = "/setupAddAdmin",method = RequestMethod.GET)
	public ModelAndView setupAddAdmin(HttpSession session) {
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

		return new ModelAndView("addAdmin","adminBean",new UserBean());
	}
	
	@RequestMapping(value = "/addAdmin",method = RequestMethod.POST)
	public String addAdmin(@ModelAttribute("adminBean")@Validated UserBean bean,BindingResult result,ModelMap model,HttpSession session) {

		
		ArrayList<AccountResponseDTO> datalist = accountDAO.selectAllAdmin();		
		Iterator<AccountResponseDTO> it=datalist.iterator();
		
		while(it.hasNext()) {
		    AccountResponseDTO dto = it.next();
		    System.out.println(bean.getEmail() + "," + dto.getEmail());
		    if(bean.getEmail().equals(dto.getEmail())) {
		        model.addAttribute("error", "Email Already Exists!");
		        return "addAdmin"; 
		    }
		}

		AccountRequestDTO dto = new AccountRequestDTO();
		dto.setName(bean.getName());
		dto.setEmail(bean.getEmail());
		dto.setStatus(0);
		dto.setRole(bean.getRole());

BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedPassword = encoder.encode(bean.getPassword());
dto.setPassword(hashedPassword);
		accountDAO.insertData(dto);

		return "redirect:/adminTable";
	}
	
	
	
	
	@RequestMapping(value = "/setupUpdateAdmin/{id}",method = RequestMethod.GET)
	public ModelAndView setupUpdateAdmin(HttpSession session,@PathVariable String id) {
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
	    AccountRequestDTO req = new AccountRequestDTO();
	    req.setId(Integer.parseInt(id));

		return new ModelAndView("updateAdmin","adminBean",accountDAO.selectOneAdmin(req));
	}
	
	

	

	@RequestMapping(value = "/updateAdmin",method = RequestMethod.POST)
	public String updateAdmin(@ModelAttribute("adminBean")@Validated UserBean bean,
			BindingResult result,ModelMap model,HttpSession session,@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword) {
		System.out.print("old Password--"+oldPassword+"--");
		System.out.print("new Password--"+newPassword+"--");
		System.out.print("confirmPassword--"+confirmPassword+"--");
		
		AccountRequestDTO dto = new AccountRequestDTO();
		AccountResponseDTO res = new AccountResponseDTO();
		dto.setId(Integer.parseInt(bean.getId()));
		res = accountDAO.selectOneAdmin(dto);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(oldPassword, res.getPassword())) {
		    model.addAttribute("error", "Wrong old password");
		    return "updateAdmin";
		} else {
		    AccountRequestDTO req = new AccountRequestDTO();
		    req.setId(Integer.parseInt(bean.getId()));
		    req.setEmail(bean.getEmail());
		    req.setName(bean.getName());
		    req.setPassword(encoder.encode(newPassword)); // Encrypt the new password
		    accountDAO.updateData(req);
		    String email = req.getEmail();
		    if(bean.getEmail().equals(session.getAttribute("signUpEmail"))){
		    	System.out.print(bean.getEmail()+"---"+session.getAttribute("signUpEmail"));
		    session.setAttribute("signUpEmail", email);
		    }

		    return "redirect:/homePage";
		}
	
	}
	
}
