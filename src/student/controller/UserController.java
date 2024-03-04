package student.controller;

import java.util.ArrayList;

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
import student.dao.StudentAccountDAO;
import student.dao.StudentTableDAO;
import student.dto.AccountRequestDTO;
import student.dto.AccountResponseDTO;
import student.dto.StudentAccountCourseResponseDTO;
import student.model.UserBean;

@Controller
public class UserController {
	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private StudentAccountDAO studentAccountDAO;
	
	@Autowired
	private StudentTableDAO studentTableDAO;
	
	@RequestMapping(value = "/userTable",method = RequestMethod.GET)
	public String userTable(ModelMap model,HttpSession session) {
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
	    
		ArrayList<AccountResponseDTO> userList = accountDAO.selectAllUser();
		model.addAttribute("userList", userList);
		return "userTable";
	}
	
	@RequestMapping(value = "/banneduserTable",method = RequestMethod.GET)
	public String banneduserTable(ModelMap model,HttpSession session) {
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
	    
		ArrayList<AccountResponseDTO> userList = accountDAO.selectAllBannedUser();
		model.addAttribute("userList", userList);
		return "banneduserTable";
	}
	
	@RequestMapping(value = "/setupeditprofile/{id}", method = RequestMethod.GET)
	public ModelAndView setupeditprofile(@PathVariable String id,HttpSession session) {
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return new ModelAndView("errorPage","accountBean",new UserBean());
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
		return new ModelAndView("userUpdate","accountBean",accountDAO.selectOneUser(req));
	}
	
	
	@RequestMapping(value = "/updateprofile", method = RequestMethod.POST)
	public String updateprofile(@ModelAttribute("accountBean")@Validated UserBean bean,BindingResult result,ModelMap model,
			HttpSession session,@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword) {
		System.out.print("old Password--"+oldPassword+"--");
		System.out.print("new Password--"+newPassword+"--");
		System.out.print("confirmPassword--"+confirmPassword+"--");
		
		int id = Integer.parseInt(session.getAttribute("userId").toString());
		AccountRequestDTO dto = new AccountRequestDTO();
		AccountResponseDTO res = new AccountResponseDTO();
		dto.setId(id);
		res = accountDAO.selectOneUser(dto);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(oldPassword, res.getPassword())) {
		    model.addAttribute("error", "Wrong old password");
		    return "userUpdate";
		} else {
		    AccountRequestDTO req = new AccountRequestDTO();
		    req.setId(Integer.parseInt(bean.getId()));
		    req.setEmail(bean.getEmail());
		    req.setName(bean.getName());
		    req.setPassword(encoder.encode(newPassword)); // Encrypt the new password
		    accountDAO.updateData(req);
		    String email = req.getEmail();
		    session.setAttribute("signUpEmail", email);

		    return "redirect:/homePage";
		}
	}
	
	@RequestMapping(value = "/banUser/{id}", method = RequestMethod.GET)
	public String banUser(HttpSession session,@PathVariable String id) {
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return "errorPage";
	    }
		AccountRequestDTO req = new AccountRequestDTO();
		req.setId(Integer.parseInt(id));
		accountDAO.deleteData(req);
		studentAccountDAO.banUser(Integer.parseInt(id));
		return"redirect:/userTable";
	}
	
	@RequestMapping(value = "/unbanUser/{id}", method = RequestMethod.GET)
	public String unbanUser(HttpSession session,@PathVariable String id) {
	    if (session == null || session.getAttribute("signUpEmail") == null) {
	        return "errorPage";
	    }
		AccountRequestDTO req = new AccountRequestDTO();
		req.setId(Integer.parseInt(id));
		accountDAO.unBanUser(req);
		studentAccountDAO.unbanUser(Integer.parseInt(id));
		return"redirect:/userTable";
	}
}
