package student.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import org.springframework.stereotype.Service;

import student.dto.StudentAccountCourseResponseDTO;




@Service("studentTableDAO")
public class StudentTableDAO {
	public static Connection con = null;
	static {
		con=MyConncection.getConnection();
	}
	public ArrayList<StudentAccountCourseResponseDTO> selectAllStudentTable(int accountId) {
	    ArrayList<StudentAccountCourseResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT sa.*, s.*,s.id AS student_id_id, GROUP_CONCAT(DISTINCT sc.course_id) AS course_ids, GROUP_CONCAT(DISTINCT c.course_name) AS course_names\r\n"
	            + "FROM student_account sa\r\n"
	            + "JOIN student s ON sa.student_id = s.id\r\n"
	            + "JOIN account a ON sa.account_id = a.id\r\n"
	            + "JOIN student_course sc ON s.id = sc.student_id\r\n"
	            + "JOIN course c ON sc.course_id = c.id\r\n"
	            + "WHERE sa.account_id = ? "
	            + "  AND s.status != 1\r\n"
	            + "  AND a.status != 1\r\n"
	            
	            + "  AND c.status != 1\r\n"
	            +"AND sa.status = 1\r\n"
	            +"AND sa.delete_status != 1\r\n"
	            + "GROUP BY sa.account_id, sa.id, s.id;";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, accountId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            StudentAccountCourseResponseDTO res = new StudentAccountCourseResponseDTO();
	            res.setStudent_id(rs.getInt("student_id_id"));
	            res.setGender(rs.getString("student_gender"));
	            res.setEducation(rs.getNString("student_education"));
	            res.setPhone(rs.getNString("student_phone"));
	            res.setDob(rs.getNString("student_dob"));
	            res.setCourseName(rs.getString("course_names"));
	            res.setCourseIds(rs.getString("course_ids"));
	            res.setStudentName(rs.getString("student_name"));
	            res.setStudent_photo(rs.getString("student_photo"));

	            list.add(res);
	        }
	    } catch (SQLException e) {
	        System.out.println("DataBase Error . selectAllStudentTable Error" + e);
	    }
	    return list;
	}

	public ArrayList<StudentAccountCourseResponseDTO> adminSelectAllStudentTable() {
	    ArrayList<StudentAccountCourseResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT\r\n" + 
	    		"s.*,\r\n" + 
	    		"s.id AS student_id_id,\r\n" + 
	    		"GROUP_CONCAT(DISTINCT sc.course_id) AS course_ids,\r\n" + 
	    		"GROUP_CONCAT(DISTINCT c.course_name) AS course_names\r\n" + 
	    		"FROM student_account sa\r\n" + 
	    		"\r\n" + 
	    		"JOIN student s ON sa.student_id = s.id\r\n" + 
	    		"JOIN account a ON sa.account_id = a.id\r\n" + 
	    		"JOIN student_course sc ON s.id = sc.student_id\r\n" + 
	    		"JOIN course c ON sc.course_id = c.id\r\n" + 
	    		"WHERE\r\n" + 
	    		"s.status != 1\r\n" + 
	    		"AND c.status != 1\r\n" + 
	    		"AND sa.status = 1\r\n" + 
	    		"AND sa.delete_status != 1\r\n" + 
	    		"GROUP BY\r\n" + 
	    		" s.id;";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            StudentAccountCourseResponseDTO res = new StudentAccountCourseResponseDTO();
	            res.setStudent_id(rs.getInt("student_id_id"));
	            res.setGender(rs.getString("student_gender"));
	            res.setEducation(rs.getNString("student_education"));
	            res.setPhone(rs.getNString("student_phone"));
	            res.setDob(rs.getNString("student_dob"));
	            res.setCourseName(rs.getString("course_names"));
	            res.setCourseIds(rs.getString("course_ids"));
	            res.setStudentName(rs.getString("student_name"));
	            res.setStudent_photo(rs.getString("student_photo"));

	            list.add(res);
	        }
	    } catch (SQLException e) {
	        System.out.println("DataBase Error . adminSelectAllStudentTable Error" + e);
	    }
	    return list;
	}
	
	//--------------------------------------------------------------
	
	public ArrayList<StudentAccountCourseResponseDTO> selectAllPendingStudentTableUser(int accountId) {
	    ArrayList<StudentAccountCourseResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT sa.*, s.*,s.id AS student_id_id, GROUP_CONCAT(DISTINCT sc.course_id) AS course_ids, GROUP_CONCAT(DISTINCT c.course_name) AS course_names\r\n"
	            + "FROM student_account sa\r\n"
	            + "JOIN student s ON sa.student_id = s.id\r\n"
	            + "JOIN account a ON sa.account_id = a.id\r\n"
	            + "JOIN student_course sc ON s.id = sc.student_id\r\n"
	            + "JOIN course c ON sc.course_id = c.id\r\n"
	            + "WHERE sa.account_id = ? "
	            + "  AND s.status != 1\r\n"
	            + "  AND a.status != 1\r\n"
	            + "  AND c.status != 1\r\n"
	            +"AND sa.status = 0\r\n"
	            +"AND sa.delete_status != 1\r\n"
	            + "GROUP BY sa.account_id, sa.id, s.id;";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, accountId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            StudentAccountCourseResponseDTO res = new StudentAccountCourseResponseDTO();
	            res.setStudent_id(rs.getInt("student_id_id"));
	            res.setGender(rs.getString("student_gender"));
	            res.setEducation(rs.getNString("student_education"));
	            res.setPhone(rs.getNString("student_phone"));
	            res.setDob(rs.getNString("student_dob"));
	            res.setCourseName(rs.getString("course_names"));
	            res.setCourseIds(rs.getString("course_ids"));
	            res.setStudentName(rs.getString("student_name"));
	            res.setStudent_photo(rs.getString("student_photo"));

	            list.add(res);
	        }
	    } catch (SQLException e) {
	        System.out.println("DataBase Error . selectAllPendingStudentTableUser Error" + e);
	    }
	    return list;
	}
	
	public ArrayList<StudentAccountCourseResponseDTO> adminSelectAllPendingStudentTable() {
	    ArrayList<StudentAccountCourseResponseDTO> list = new ArrayList<>();
	    String sql = "    \r\n" + 
	    		"    SELECT\r\n" + 
	    		"    s.*,\r\n" + 
	    		"    s.id AS student_id_id,\r\n" + 
	    		"    GROUP_CONCAT(DISTINCT sc.course_id) AS course_ids,\r\n" + 
	    		"    GROUP_CONCAT(DISTINCT c.course_name) AS course_names\r\n" + 
	    		"FROM\r\n" + 
	    		"    student s\r\n" + 
	    		"    JOIN student_course sc ON s.id = sc.student_id\r\n" + 
	    		"    JOIN course c ON sc.course_id = c.id\r\n" + 
	    		"    JOIN student_account sa ON sa.student_id = s.id\r\n" + 
	    		"    JOIN account a ON sa.account_id = a.id\r\n" + 
	    		"WHERE\r\n" + 
	    		"    s.status != 1\r\n" + 
	    		"    AND c.status != 1\r\n" + 
	    		"    AND sa.status = 0\r\n" 
	            +"AND sa.delete_status != 1\r\n"+
	    		"GROUP BY\r\n" + 
	    		"    s.id;";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            StudentAccountCourseResponseDTO res = new StudentAccountCourseResponseDTO();
	            res.setStudent_id(rs.getInt("student_id_id"));
	            res.setGender(rs.getString("student_gender"));
	            res.setEducation(rs.getNString("student_education"));
	            res.setPhone(rs.getNString("student_phone"));
	            res.setDob(rs.getNString("student_dob"));
	            res.setCourseName(rs.getString("course_names"));
	            res.setCourseIds(rs.getString("course_ids"));
	            res.setStudentName(rs.getString("student_name"));
	            res.setStudent_photo(rs.getString("student_photo"));

	            list.add(res);
	        }
	    } catch (SQLException e) {
	        System.out.println("DataBase Error . selectAllPendingStudentTableUser Error" + e);
	    }
	    return list;
	}
	//-------------------------------------------------------------------------
	
	public ArrayList<StudentAccountCourseResponseDTO> selectAllDeletedStudentTableUser(int accountId) {
	    ArrayList<StudentAccountCourseResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT sa.*, s.*,s.id AS student_id_id, GROUP_CONCAT(DISTINCT sc.course_id) AS course_ids, GROUP_CONCAT(DISTINCT c.course_name) AS course_names\r\n"
	            + "FROM student_account sa\r\n"
	            + "JOIN student s ON sa.student_id = s.id\r\n"
	            + "JOIN account a ON sa.account_id = a.id\r\n"
	            + "JOIN student_course sc ON s.id = sc.student_id\r\n"
	            + "JOIN course c ON sc.course_id = c.id\r\n"
	            + "WHERE sa.account_id = ? "
	            + "  AND s.status != 1\r\n"
	            + "  AND a.status != 1\r\n"
	            + "  AND c.status != 1\r\n"
	            +"AND sa.status = 2\r\n"
	            +"AND sa.delete_status != 1\r\n"
	            + "GROUP BY sa.account_id, sa.id, s.id;";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, accountId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            StudentAccountCourseResponseDTO res = new StudentAccountCourseResponseDTO();
	            res.setStudent_id(rs.getInt("student_id_id"));
	            res.setGender(rs.getString("student_gender"));
	            res.setEducation(rs.getNString("student_education"));
	            res.setPhone(rs.getNString("student_phone"));
	            res.setDob(rs.getNString("student_dob"));
	            res.setCourseName(rs.getString("course_names"));
	            res.setCourseIds(rs.getString("course_ids"));
	            res.setStudentName(rs.getString("student_name"));
	            res.setStudent_photo(rs.getString("student_photo"));

	            list.add(res);
	        }
	    } catch (SQLException e) {
	        System.out.println("DataBase Error . selectAllDeletedStudentTableUser Error" + e);
	    }
	    return list;
	}
	
	
	public ArrayList<StudentAccountCourseResponseDTO> adminSelectAllDeletedStudentTable() {
	    ArrayList<StudentAccountCourseResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT\r\n" + 
	    		"s.*,\r\n" + 
	    		"s.id AS student_id_id,\r\n" + 
	    		"GROUP_CONCAT(DISTINCT sc.course_id) AS course_ids,\r\n" + 
	    		"GROUP_CONCAT(DISTINCT c.course_name) AS course_names\r\n" + 
	    		"FROM student_account sa\r\n" + 
	    		"\r\n" + 
	    		"JOIN student s ON sa.student_id = s.id\r\n" + 
	    		"JOIN account a ON sa.account_id = a.id\r\n" + 
	    		"JOIN student_course sc ON s.id = sc.student_id\r\n" + 
	    		"JOIN course c ON sc.course_id = c.id\r\n" + 
	    		"WHERE\r\n" + 
	    		"s.status != 1\r\n" + 
	    		"AND c.status != 1\r\n" + 
	    		"AND sa.status = 2\r\n" + 
	    		"AND sa.delete_status != 1\r\n" + 
	    		"GROUP BY\r\n" + 
	    		" s.id;";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            StudentAccountCourseResponseDTO res = new StudentAccountCourseResponseDTO();
	            res.setStudent_id(rs.getInt("student_id_id"));
	            res.setGender(rs.getString("student_gender"));
	            res.setEducation(rs.getNString("student_education"));
	            res.setPhone(rs.getNString("student_phone"));
	            res.setDob(rs.getNString("student_dob"));
	            res.setCourseName(rs.getString("course_names"));
	            res.setCourseIds(rs.getString("course_ids"));
	            res.setStudentName(rs.getString("student_name"));
	            res.setStudent_photo(rs.getString("student_photo"));

	            list.add(res);
	        }
	    } catch (SQLException e) {
	        System.out.println("DataBase Error . adminSelectAllDeletedStudentTable Error" + e);
	    }
	    return list;
	}
	
	
	
	//------------------------------------------------------------
	
	
	public ArrayList<StudentAccountCourseResponseDTO> searchByNameStudentFromUser(int accountId,String studentName) {
	    ArrayList<StudentAccountCourseResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT sa.*, s.*,s.id AS student_id_id, GROUP_CONCAT(DISTINCT sc.course_id) AS course_ids, GROUP_CONCAT(DISTINCT c.course_name) AS course_names\r\n" + 
	    		"FROM student_account sa\r\n" + 
	    		"JOIN student s ON sa.student_id = s.id\r\n" + 
	    		"JOIN account a ON sa.account_id = a.id\r\n" + 
	    		"JOIN student_course sc ON s.id = sc.student_id\r\n" + 
	    		"JOIN course c ON sc.course_id = c.id\r\n" + 
	    		"WHERE sa.account_id = ?\r\n" + 
	    		"  AND s.student_name LIKE CONCAT('%', ?, '%')\r\n" + 
	    		"  AND s.status != 1\r\n" + 
	    		"  AND a.status != 1\r\n" + 
	    		"  AND c.status != 1\r\n" 
	            +"AND sa.delete_status != 1\r\n"+
	    		"GROUP BY sa.account_id, sa.id, s.id;\r\n" + 
	    		"";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, accountId);
	        ps.setNString(2, studentName);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            StudentAccountCourseResponseDTO res = new StudentAccountCourseResponseDTO();
	            res.setStudent_id(rs.getInt("student_id_id"));
	            res.setGender(rs.getString("student_gender"));
	            res.setEducation(rs.getNString("student_education"));
	            res.setPhone(rs.getNString("student_phone"));
	            res.setDob(rs.getNString("student_dob"));
	            res.setCourseName(rs.getString("course_names"));
	            res.setCourseIds(rs.getString("course_ids"));
	            res.setStudentName(rs.getString("student_name"));
	            res.setStudent_photo(rs.getString("student_photo"));

	            list.add(res);
	        }
	    } catch (SQLException e) {
	        System.out.println("DataBase Error . SelectAll Error" + e);
	    }
	    return list;
	}
	
	
	public ArrayList<StudentAccountCourseResponseDTO> searchByCourseNameFromUser(int accountId,String courseName) {
	    ArrayList<StudentAccountCourseResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT sa.*, s.*,s.id AS student_id_id, GROUP_CONCAT(DISTINCT sc.course_id) AS course_ids, GROUP_CONCAT(DISTINCT c.course_name) AS course_names\r\n" + 
	    		"FROM student_account sa\r\n" + 
	    		"JOIN student s ON sa.student_id = s.id\r\n" + 
	    		"JOIN account a ON sa.account_id = a.id\r\n" + 
	    		"JOIN student_course sc ON s.id = sc.student_id\r\n" + 
	    		"JOIN course c ON sc.course_id = c.id\r\n" + 
	    		"WHERE sa.account_id = ?\r\n" + 
	    		"  AND s.status != 1\r\n" + 
	    		"  AND a.status != 1\r\n" + 
	    		"  AND c.status != 1\r\n" + 
	    		"GROUP BY sa.account_id, sa.id, s.id\r\n" + 
	    		"HAVING course_names LIKE CONCAT('%', ?, '%');\r\n" + 
	    		"";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, accountId);
	        ps.setNString(2, courseName);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            StudentAccountCourseResponseDTO res = new StudentAccountCourseResponseDTO();
	            res.setStudent_id(rs.getInt("student_id_id"));
	            res.setGender(rs.getString("student_gender"));
	            res.setEducation(rs.getNString("student_education"));
	            res.setPhone(rs.getNString("student_phone"));
	            res.setDob(rs.getNString("student_dob"));
	            res.setCourseName(rs.getString("course_names"));
	            res.setCourseIds(rs.getString("course_ids"));
	            res.setStudentName(rs.getString("student_name"));
	            res.setStudent_photo(rs.getString("student_photo"));

	            list.add(res);
	        }
	    } catch (SQLException e) {
	        System.out.println("DataBase Error . SelectAll Error" + e);
	    }
	    return list;
	}
	
	public StudentAccountCourseResponseDTO searchByStudentIDForUpdate(int code) {
	    StudentAccountCourseResponseDTO res = new StudentAccountCourseResponseDTO();
	    String sql = "SELECT sa.*, s.*, s.id AS student_id_id, GROUP_CONCAT(DISTINCT sc.course_id) AS course_ids, GROUP_CONCAT(DISTINCT c.course_name) AS course_names\r\n" + 
	    		"FROM student_account sa\r\n" + 
	    		"JOIN student s ON sa.student_id = s.id\r\n" + 
	    		"JOIN account a ON sa.account_id = a.id\r\n" + 
	    		"JOIN student_course sc ON s.id = sc.student_id\r\n" + 
	    		"JOIN course c ON sc.course_id = c.id\r\n" + 
	    		"  WHERE s.id = ?\r\n" + 
	    		"  AND s.status != 1\r\n" + 
	    		"  AND a.status != 1\r\n" + 
	    		"  AND c.status != 1\r\n" + 
	    		"GROUP BY sa.account_id, sa.id, s.id\r\n"  
	    		;
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, code);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            res.setStudent_id(rs.getInt("student_id_id"));
	            res.setStudentName(rs.getString("student_name"));
	            res.setGender(rs.getString("student_gender"));
	            res.setEducation(rs.getNString("student_education"));
	            res.setPhone(rs.getNString("student_phone"));
	            res.setDob(rs.getNString("student_dob"));
	            res.setCourseName(rs.getString("course_names"));
	            res.setCourseIds(rs.getString("course_ids"));
	            res.setStudentName(rs.getString("student_name"));
//	            byte[] imageBytes = rs.getBytes("student_photo");
//	            Blob imageBlob = rs.getBlob("student_photo");
	            res.setStudent_photo(rs.getString("student_photo"));

	  
	        }
	    } catch (SQLException e) {
	        System.out.println("DataBase Error . SelectAll Error" + e);
	    }
	    return res;
	}
	
	public ArrayList<StudentAccountCourseResponseDTO> searchByAllUser(int accountId,String courseName,String studentName) {
	    ArrayList<StudentAccountCourseResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT sa.*, s.*,s.id AS student_id_id, GROUP_CONCAT(DISTINCT sc.course_id) AS course_ids, GROUP_CONCAT(DISTINCT c.course_name) AS course_names\r\n" + 
	    		"FROM student_account sa\r\n" + 
	    		"JOIN student s ON sa.student_id = s.id\r\n" + 
	    		"JOIN account a ON sa.account_id = a.id\r\n" + 
	    		"JOIN student_course sc ON s.id = sc.student_id\r\n" + 
	    		"JOIN course c ON sc.course_id = c.id\r\n" + 
	    		"WHERE sa.account_id = ?\r\n" + 
	    		"  AND s.student_name LIKE CONCAT('%', ?, '%')\r\n" + 
	    		"  AND s.status != 1\r\n" + 
	    		"  AND a.status != 1\r\n" + 
	    		"  AND c.status != 1\r\n" + 
	    		"GROUP BY sa.account_id, sa.id, s.id\r\n" + 
	    		"HAVING course_names LIKE CONCAT('%', ?, '%');\r\n" + 
	    		"";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, accountId);
	        ps.setNString(2, studentName);
	        ps.setNString(3, courseName);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            StudentAccountCourseResponseDTO res = new StudentAccountCourseResponseDTO();
	            res.setStudent_id(rs.getInt("student_id_id"));
	            res.setGender(rs.getString("student_gender"));
	            res.setEducation(rs.getNString("student_education"));
	            res.setPhone(rs.getNString("student_phone"));
	            res.setDob(rs.getNString("student_dob"));
	            res.setCourseName(rs.getString("course_names"));
	            res.setCourseIds(rs.getString("course_ids"));
	            res.setStudentName(rs.getString("student_name"));
	            res.setStudent_photo(rs.getString("student_photo"));

	            list.add(res);
	        }
	    } catch (SQLException e) {
	        System.out.println("DataBase Error . SelectAll Error" + e);
	    }
	    return list;
	}
	
	//----------------------------
	
	
	public ArrayList<StudentAccountCourseResponseDTO> searchByNameStudentFromAdmin(String studentName) {
	    ArrayList<StudentAccountCourseResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT sa.*, s.*,s.id AS student_id_id, GROUP_CONCAT(DISTINCT sc.course_id) AS course_ids, GROUP_CONCAT(DISTINCT c.course_name) AS course_names\r\n" + 
	    		"FROM student_account sa\r\n" + 
	    		"JOIN student s ON sa.student_id = s.id\r\n" + 
	    		"JOIN account a ON sa.account_id = a.id\r\n" + 
	    		"JOIN student_course sc ON s.id = sc.student_id\r\n" + 
	    		"JOIN course c ON sc.course_id = c.id\r\n" + 
	    		"  WHERE s.student_name LIKE CONCAT('%', ?, '%')\r\n" + 
	    		"  AND s.status != 1\r\n" + 
	    		"  AND a.status != 1\r\n" + 
	    		"  AND c.status != 1\r\n" + 
	    		"GROUP BY sa.account_id, sa.id, s.id;\r\n" + 
	    		"";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setNString(1, studentName);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            StudentAccountCourseResponseDTO res = new StudentAccountCourseResponseDTO();
	            res.setStudent_id(rs.getInt("student_id_id"));
	            res.setGender(rs.getString("student_gender"));
	            res.setEducation(rs.getNString("student_education"));
	            res.setPhone(rs.getNString("student_phone"));
	            res.setDob(rs.getNString("student_dob"));
	            res.setCourseName(rs.getString("course_names"));
	            res.setCourseIds(rs.getString("course_ids"));
	            res.setStudentName(rs.getString("student_name"));
	            res.setStudent_photo(rs.getString("student_photo"));

	            list.add(res);
	        }
	    } catch (SQLException e) {
	        System.out.println("DataBase Error . SelectAll Error" + e);
	    }
	    return list;
	}
	
	
	public ArrayList<StudentAccountCourseResponseDTO> searchByCourseNameFromAdmin(String courseName) {
	    ArrayList<StudentAccountCourseResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT sa.*, s.*,s.id AS student_id_id, GROUP_CONCAT(DISTINCT sc.course_id) AS course_ids, GROUP_CONCAT(DISTINCT c.course_name) AS course_names\r\n" + 
	    		"FROM student_account sa\r\n" + 
	    		"JOIN student s ON sa.student_id = s.id\r\n" + 
	    		"JOIN account a ON sa.account_id = a.id\r\n" + 
	    		"JOIN student_course sc ON s.id = sc.student_id\r\n" + 
	    		"JOIN course c ON sc.course_id = c.id\r\n" +  
	    		"  WHERE s.status != 1\r\n" + 
	    		"  AND a.status != 1\r\n" + 
	    		"  AND c.status != 1\r\n" + 
	    		"GROUP BY sa.account_id, sa.id, s.id\r\n" + 
	    		"HAVING course_names LIKE CONCAT('%', ?, '%');\r\n" + 
	    		"";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setNString(1, courseName);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            StudentAccountCourseResponseDTO res = new StudentAccountCourseResponseDTO();
	            res.setStudent_id(rs.getInt("student_id_id"));
	            res.setGender(rs.getString("student_gender"));
	            res.setEducation(rs.getNString("student_education"));
	            res.setPhone(rs.getNString("student_phone"));
	            res.setDob(rs.getNString("student_dob"));
	            res.setCourseName(rs.getString("course_names"));
	            res.setCourseIds(rs.getString("course_ids"));
	            res.setStudentName(rs.getString("student_name"));
	            res.setStudent_photo(rs.getString("student_photo"));

	            list.add(res);
	        }
	    } catch (SQLException e) {
	        System.out.println("DataBase Error . SelectAll Error" + e);
	    }
	    return list;
	}
	
	public ArrayList<StudentAccountCourseResponseDTO> searchByAllAdmin(String courseName,String studentName) {
	    ArrayList<StudentAccountCourseResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT sa.*, s.*,s.id AS student_id_id, GROUP_CONCAT(DISTINCT sc.course_id) AS course_ids, GROUP_CONCAT(DISTINCT c.course_name) AS course_names\r\n" + 
	    		"FROM student_account sa\r\n" + 
	    		"JOIN student s ON sa.student_id = s.id\r\n" + 
	    		"JOIN account a ON sa.account_id = a.id\r\n" + 
	    		"JOIN student_course sc ON s.id = sc.student_id\r\n" + 
	    		"JOIN course c ON sc.course_id = c.id\r\n" + 
	    		"  WHERE s.student_name LIKE CONCAT('%', ?, '%')\r\n" + 
	    		"  AND s.status != 1\r\n" + 
	    		"  AND a.status != 1\r\n" + 
	    		"  AND c.status != 1\r\n" + 
	    		"GROUP BY sa.account_id, sa.id, s.id\r\n" + 
	    		"HAVING course_names LIKE CONCAT('%', ?, '%');\r\n" + 
	    		"";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setNString(1, studentName);
	        ps.setNString(2, courseName);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            StudentAccountCourseResponseDTO res = new StudentAccountCourseResponseDTO();
	            res.setStudent_id(rs.getInt("student_id_id"));
	            res.setGender(rs.getString("student_gender"));
	            res.setEducation(rs.getNString("student_education"));
	            res.setPhone(rs.getNString("student_phone"));
	            res.setDob(rs.getNString("student_dob"));
	            res.setCourseName(rs.getString("course_names"));
	            res.setCourseIds(rs.getString("course_ids"));
	            res.setStudentName(rs.getString("student_name"));
	            res.setStudent_photo(rs.getString("student_photo"));

	            list.add(res);
	        }
	    } catch (SQLException e) {
	        System.out.println("DataBase Error . SelectAll Error" + e);
	    }
	    return list;
	}


}
