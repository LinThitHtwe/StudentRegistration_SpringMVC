package student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import student.dto.StudentRequestDTO;
import student.dto.StudentResponseDTO;




@Service("studentDAO")
public class StudentDAO {
	public static Connection con = null;
	static {
		con=MyConncection.getConnection();
	}

	public void insertData(StudentRequestDTO dto) {
		String sql="insert into student(student_name,student_dob,student_gender,student_phone,student_education,student_photo,status) values(?,?,?,?,?,?,?)";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,dto.getStudentName());
			ps.setString(2, dto.getDateOfBirth());
			ps.setString(3, dto.getGender());
			ps.setString(4,dto.getPhone());
			ps.setNString(5, dto.getEducation());
			ps.setString(6,dto.getPhoto());
			ps.setInt(7, 0);

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DataBase Error , insert Error"+e); 
		}
		 
	}
	
	public void updateData(StudentRequestDTO dto) {
		String sql="update student set student_name=?,student_dob=?,student_gender=?,student_phone=?,student_education=?,student_photo=? where id=?";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1,dto.getStudentName());
			ps.setString(2, dto.getDateOfBirth());
			ps.setString(3, dto.getGender());
			ps.setString(4,dto.getPhone());
			ps.setNString(5, dto.getEducation());
			ps.setString(6, dto.getPhoto());
			ps.setInt(7, dto.getStudentId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DataBase Error . Update error"+e); 
		}
		 
	}
	
	public void updateDataWithoutPhoto(StudentRequestDTO dto) {
		String sql="update student set student_name=?,student_dob=?,student_gender=?,student_phone=?,student_education=? where id=?";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1,dto.getStudentName());
			ps.setString(2, dto.getDateOfBirth());
			ps.setString(3, dto.getGender());
			ps.setString(4,dto.getPhone());
			ps.setNString(5, dto.getEducation());
			ps.setInt(6, dto.getStudentId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DataBase Error . Update error"+e); 
		}
		 
	}
	
	public void deleteData(StudentRequestDTO dto) {
		String sql="update student set status=? where id=?";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, 1);
			ps.setInt(2, dto.getStudentId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DataBase Error . Delete Failed"+e); 
		}
		 
	}
	
	public StudentResponseDTO selectOneStudentByName(StudentRequestDTO dto) {
		StudentResponseDTO res = new StudentResponseDTO();
		String sql="SELECT * FROM student ORDER BY id DESC LIMIT 1;";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, dto.getStudentName());
			ps.setString(2, dto.getPhone());
			ps.setString(3, dto.getEducation());
			ps.setString(4, dto.getDateOfBirth());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				res.setStudentId(rs.getInt("id"));
				res.setStudentName(rs.getString("student_name"));
				res.setGender(rs.getString("student_gender"));
				res.setEducation(rs.getString("student_education"));
				res.setDateOfBirth(rs.getString("student_dob"));
				res.setPhone(rs.getString("student_phone"));
				res.setPhoto(rs.getString("student_photo"));
				res.setStatus(rs.getInt("status"));
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . Select One Student ."+e); 
		}
		return res;
	}
	
	public StudentResponseDTO selectOneStudent(StudentRequestDTO dto) {
		StudentResponseDTO res = new StudentResponseDTO();
		String sql="select * from student where id=? and status != 1 ";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, dto.getStudentId());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				res.setStudentId(rs.getInt("id"));
				res.setStudentName(rs.getString("student_name"));
				res.setGender(rs.getString("student_gender"));
				res.setEducation(rs.getString("student_education"));
				res.setDateOfBirth(rs.getString("student_dob"));
				res.setPhone(rs.getString("student_phone"));
				res.setPhoto(rs.getString("student_photo"));
				res.setStatus(rs.getInt("status"));
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . Select One Student ."+e); 
		}
		return res;
	}
	
	public ArrayList<StudentResponseDTO> selectAllStudent() {
		ArrayList<StudentResponseDTO> list = new ArrayList();
		String sql= "select * from student where status != 1";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				StudentResponseDTO res = new StudentResponseDTO();
				res.setStudentId(rs.getInt("id"));
				res.setStudentName(rs.getNString("student_name"));
				res.setGender(rs.getNString("student_gender"));
				res.setEducation(rs.getNString("account_education"));
				res.setDateOfBirth(rs.getNString("student_dob"));
				res.setPhone(rs.getNString("student_phone"));
				res.setPhoto(rs.getNString("student_photo"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . SelectAll Student Error"+e); 
		}
		return list;
	}
	
//	public ArrayList<AccountResponseDTO> selectAllOneUser(AccountRequestDTO dto) {
//		ArrayList<AccountResponseDTO> list = new ArrayList();
//		String sql= "select * from account where id=? and status != 1 and account_role!='admin'";
//		try {
//			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setInt(1, dto.getId());
//			ResultSet rs= ps.executeQuery();
//			while(rs.next()) {
//				AccountResponseDTO res = new AccountResponseDTO();
//				res.setId(rs.getInt("id"));
//				res.setUserName(rs.getNString("account_name"));
//				res.setEmail(rs.getNString("account_email"));
//				res.setPassword(rs.getNString("account_password"));
//				res.setRole(rs.getNString("account_role"));
//				res.setStatus(rs.getInt("status"));
//				list.add(res);  
//			}
//			
//		} catch (SQLException e) {
//			System.out.println("DataBase Error . selectAllOneUser Error"+e); 
//		}
//		return list;
//	}
//	
//	
//	public ArrayList<StudentResponseDTO> selectAllAdmin() {
//		ArrayList<StudentResponseDTO> list = new ArrayList();
//		String sql= "select * from student where status != 1 ";
//		try {
//			PreparedStatement ps = con.prepareStatement(sql);
//			ResultSet rs= ps.executeQuery();
//			while(rs.next()) {
//				StudentResponseDTO res = new StudentResponseDTO();
//				res.setId(rs.getInt("id"));
//				res.setUserName(rs.getNString("account_name"));
//				res.setEmail(rs.getNString("account_email"));
//				res.setPassword(rs.getNString("account_password"));
//				res.setRole(rs.getNString("account_role"));
//				res.setStatus(rs.getInt("status"));
//				list.add(res);  
//			}
//			
//		} catch (SQLException e) {
//			System.out.println("DataBase Error . SelectAllAdmin Error"+e); 
//		}
//		return list;
//	}
	
	//-----------------------------------------------------------------------------
	
	
//	public ArrayList<AccountResponseDTO> searchByAllUser(AccountRequestDTO dto) {
//		ArrayList<AccountResponseDTO> list = new ArrayList();
//		String sql= "SELECT * FROM account WHERE id LIKE CONCAT('%', ?, '%') AND account_name LIKE CONCAT('%', ?, '%') AND status != 1 AND account_role !='admin'";
//		try {
//			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setInt(1, dto.getId());
//			ps.setString(2, dto.getUserName());
//			ResultSet rs= ps.executeQuery();
//			while(rs.next()) {
//				AccountResponseDTO res = new AccountResponseDTO();
//				res.setId(rs.getInt("id"));
//				res.setUserName(rs.getString("account_name"));
//				res.setStatus(rs.getInt("status"));
//				list.add(res);  
//			}
//		
//		} catch (SQLException e) {
//			System.out.println("DataBase Error . searchByALLUser Error"+e); 
//		}
//		return list;
//	}
//
//	public ArrayList<AccountResponseDTO> searchByUserID(AccountRequestDTO dto) {
//		ArrayList<AccountResponseDTO> list = new ArrayList();
//		String sql= "SELECT * FROM account WHERE id LIKE CONCAT('%', ?, '%') AND status != 1 AND account_role!='admin'";
//		try {
//			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setInt(1, dto.getId());
//			ResultSet rs= ps.executeQuery();
//			while(rs.next()) {
//				AccountResponseDTO res = new AccountResponseDTO();
//				res.setId(rs.getInt("id"));
//				res.setUserName(rs.getString("account_name"));
//				res.setStatus(rs.getInt("status"));
//				list.add(res);  
//			}
//			
//		} catch (SQLException e) {
//			System.out.println("DataBase Error . searchByUserID Error"+e); 
//		}
//		return list;
//	}
//	
//	public ArrayList<AccountResponseDTO> searchByUserName(AccountRequestDTO dto) {
//		ArrayList<AccountResponseDTO> list = new ArrayList();
//		String sql= "SELECT * FROM account WHERE account_name LIKE CONCAT('%', ?, '%') AND status != 1 AND account_role !='admin'";
//		try {
//			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setString(1, dto.getUserName());
//			ResultSet rs= ps.executeQuery();
//			while(rs.next()) {
//				AccountResponseDTO res = new AccountResponseDTO();
//				res.setId(rs.getInt("id"));
//				res.setUserName(rs.getString("account_name"));
//				res.setStatus(rs.getInt("status"));
//				list.add(res);  
//			}
//			
//		} catch (SQLException e) {
//			System.out.println("DataBase Error . searchByUserName Error"+e); 
//		}
//		return list;
//	}
//	
//	public AccountResponseDTO getSignupId(AccountRequestDTO dto) {
//		AccountResponseDTO res = new AccountResponseDTO();
//		String sql="select * from account where account_email=? and status != 1 and account_role!='admin'";
//
//		
//		try {
//			PreparedStatement ps = con.prepareStatement(sql);
//
//			ps.setString(1, dto.getEmail());
//			ResultSet rs= ps.executeQuery();
//			while(rs.next()) {
//				res.setId(rs.getInt("id"));
//				res.setUserName(rs.getNString("account_name"));
//				res.setEmail(rs.getNString("account_email"));
//				res.setPassword(rs.getNString("account_password"));
//				res.setRole(rs.getNString("account_role"));
//				res.setStatus(rs.getInt("status"));
//			}
//			
//		} catch (SQLException e) {
//			System.out.println("DataBase Error . Select One User ."+e); 
//		}
//		return res;
//	}
	
	
}
