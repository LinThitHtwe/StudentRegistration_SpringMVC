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
import student.model.UserBean;


@Controller
public class LoginLogoutController {

	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private StudentTableDAO studentTableDAO;
	
	
	//CSS URL
	@RequestMapping(value = "/css",method = RequestMethod.GET)
	public String Css() {
		return "redirect:/CSS/test.css";
	}
	
	//Show Login Page
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public ModelAndView setuplogin() {
		return new ModelAndView("login","loginBean", new UserBean());
		
	}
	
	
	//Function for logging in
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String login(@ModelAttribute("loginBean")@Validated UserBean bean,BindingResult result,ModelMap model,HttpSession session) {
		if(bean.getEmail().equals("")||bean.getEmail().equals(null)) {
			return "errorPage";
		}
		
		AccountRequestDTO requser = new AccountRequestDTO();
		AccountRequestDTO reqadmin = new AccountRequestDTO();


BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// Admin login
		ArrayList<AccountResponseDTO> adminList = accountDAO.selectAllAdmin();
		Iterator<AccountResponseDTO> adminIter = adminList.iterator();

		while (adminIter.hasNext()) {
		    AccountResponseDTO dto = adminIter.next();

		    if (bean.getEmail().equals(dto.getEmail())) {
		        if (encoder.matches(bean.getPassword(), dto.getPassword())) {
		            reqadmin.setId(dto.getId());
		            reqadmin.setEmail(dto.getEmail());
		            reqadmin.setName(dto.getName());
		            reqadmin.setRole(dto.getRole());
		            session.setAttribute("adminId", reqadmin.getId());
		            session.setAttribute("adminName", reqadmin.getName());
		            session.setAttribute("adminEmail", reqadmin.getEmail());
		            session.setAttribute("adminRole", reqadmin.getRole());
		            session.setAttribute("signUpEmail", reqadmin.getEmail());
		            return "redirect:/homePage";
		        }
		    }
		}

		// User login
		ArrayList<AccountResponseDTO> userList = accountDAO.selectAllUser();
		Iterator<AccountResponseDTO> userIter = userList.iterator();

		while(userIter.hasNext()) {
		    AccountResponseDTO dto=userIter.next();
		    if(bean.getEmail().equals(dto.getEmail())) {
		    	
		        if(encoder.matches(bean.getPassword(), dto.getPassword())) {
		            requser.setId(dto.getId());
		            requser.setEmail(dto.getEmail());
		            requser.setName(dto.getName());
		            requser.setRole(dto.getRole());
		            session.setAttribute("userId",requser.getId());
		            session.setAttribute("userName", requser.getName());
		            session.setAttribute("userEmail",requser.getEmail());
		            session.setAttribute("userRole", requser.getRole());
		            session.setAttribute("signUpEmail", requser.getEmail());
		            return "redirect:/homePage";
		        }
		    }
		}
		
		// Error handling
		model.addAttribute("error", "Invalid email or password");
		return "login";

		
	}
	
	@RequestMapping(value = "/setupsignup",method = RequestMethod.GET)
	public ModelAndView setupsignup() {
		return new ModelAndView("signup","signupBean", new UserBean());
		
	}
	
	@RequestMapping(value = "/signup",method = RequestMethod.POST)
	public String signup(@ModelAttribute("loginBean")@Validated UserBean bean,BindingResult result,ModelMap model,HttpSession session) {

		
		ArrayList<AccountResponseDTO> datalist = accountDAO.selectAllUser();		
		Iterator<AccountResponseDTO> it=datalist.iterator();
		
		while(it.hasNext()) {
		    AccountResponseDTO dto = it.next();
		    System.out.println(bean.getEmail() + "," + dto.getEmail());
		    if(bean.getEmail().equals(dto.getEmail())) {
		        model.addAttribute("error", "Email Already Exists!");
		        return "signup"; 
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

		String dtoEmail = dto.getEmail();
		System.out.print("signUp__"+dtoEmail);
		session.setAttribute("signUpEmail", dtoEmail);
        session.setAttribute("userName", bean.getName());
        session.setAttribute("userEmail",dto.getEmail());
        session.setAttribute("userRole", dto.getRole());
        AccountResponseDTO res = accountDAO.getSignupId(dto);
        session.setAttribute("userId", res.getId());
        
	
		return "redirect:/homePage";
	}
	
	@RequestMapping(value = "/homePage",method = RequestMethod.GET)
	public String homePage(HttpSession session,ModelMap model) {
	    if(session.getAttribute("signUpEmail")==null) {
	        return  "errorPage";
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
	    
	    
		AccountDAO accountDAO = new AccountDAO();
		AccountRequestDTO dto = new AccountRequestDTO();
		System.out.println("HomePage--"+session.getAttribute("signUpEmail"));
		System.out.println("HomePage--"+(String)session.getAttribute("signUpEmail"));
		//dto.setEmail((String) request.getSession().getAttribute("signUpEmail"));
		dto.setEmail((String)session.getAttribute("signUpEmail"));

		AccountResponseDTO res = accountDAO.getSignupId(dto);
		int signUpID = res.getId();
		System.out.println(signUpID);
		
		if(session.getAttribute("adminId")==null) {
            session.setAttribute("userId",signUpID);
            session.setAttribute("userName", res.getName());
            session.setAttribute("userEmail",res.getEmail());
            session.setAttribute("userRole", res.getRole());
            
            return "welcomeBack";
		}
		else {
            session.setAttribute("adminId",signUpID);
            session.setAttribute("adminName", res.getName());
            session.setAttribute("adminEmail",res.getEmail());
            session.setAttribute("adminRole", res.getRole());
            return "welcomeBack";
		}
	}

	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
	    if (session != null) {
	        session.invalidate();
	    }
	    return "redirect:/";
	}
	
	

	

}
