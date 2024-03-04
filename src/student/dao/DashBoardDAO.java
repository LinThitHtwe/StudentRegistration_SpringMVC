package student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import student.dto.CourseCountResponseDTO;
import student.dto.EducationCount;
import student.dto.GenderCountResponseDTO;

@Service("dashBoardDAO")
public class DashBoardDAO {
	
	public static Connection con = null;
	static {
		con=MyConncection.getConnection();
	}
	
	public ArrayList<EducationCount> educationCount() {
	    ArrayList<EducationCount> list = new ArrayList<>();
	    String sql = "SELECT s.student_education, COUNT(*) AS count\r\n" + 
	    		"FROM student_account sa \r\n" + 
	    		"JOIN student s ON sa.student_id = s.id \r\n" + 
	    		"WHERE sa.status = 1\r\n" + 
	    		"GROUP BY s.student_education;";
	    if (con == null) {
	    	System.out.print("con null");
	    }
	    try (PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	        	EducationCount res = new EducationCount();
	            res.setCount(rs.getInt("count"));
	            res.setEducation(rs.getNString("student_education"));
	            list.add(res);
	        }

	    } catch (SQLException e) {
	        System.out.println("Database Error: " + e);
	    }
	    
	    System.out.print(list.size());

	    return list;
	}

	public ArrayList<GenderCountResponseDTO> genderCount() {
	    ArrayList<GenderCountResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT s.student_gender, COUNT(*) AS count\r\n" + 
	    		"FROM student_account sa\r\n" + 
	    		"JOIN student s ON sa.student_id = s.id\r\n" + 
	    		"WHERE sa.status = 1\r\n" + 
	    		"GROUP BY s.student_gender;";
	    if (con == null) {
	    	System.out.print("con null");
	    }
	    try (PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            GenderCountResponseDTO res = new GenderCountResponseDTO();
	            res.setCount(rs.getInt("count"));
	            res.setGender(rs.getNString("student_gender"));
	            list.add(res);
	        }

	    } catch (SQLException e) {
	        System.out.println("Database Error: " + e);
	    }
	    
	    System.out.print(list.size());

	    return list;
	}
	public ArrayList<CourseCountResponseDTO> courseCount() {
	    ArrayList<CourseCountResponseDTO> list = new ArrayList<>();
	    String sql = "SELECT c.course_name, COUNT(*) AS count\r\n" + 
	    		"FROM student_course sc\r\n" + 
	    		"JOIN student_account sa ON sa.student_id = sc.student_id\r\n" + 
	    		"JOIN course c ON c.id = sc.course_id\r\n" + 
	    		"WHERE sa.status = 1\r\n" + 
	    		"GROUP BY c.course_name;";
	    if (con == null) {
	    	System.out.print("con null");
	    }
	    try (PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	        	CourseCountResponseDTO res = new CourseCountResponseDTO();
	            res.setCount(rs.getInt("count"));
	            res.setCourse(rs.getNString("course_name"));
	            list.add(res);
	        }

	    } catch (SQLException e) {
	        System.out.println("Database Error: " + e);
	    }
	    
	    System.out.print(list.size());

	    return list;
	}
	public double AverageAge() {
		double averageAge = 0;
		String sql = "\r\n" + 
				"SELECT AVG(DATEDIFF(CURDATE(), student_dob) / 365.25) AS average_age\r\n" + 
				"FROM student;";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				averageAge = (rs.getDouble("average_age"));
			}
				
		
		
		}catch (SQLException e) {
			System.out.println("DataBase Error . Select One User ."+e); 
		}
		return averageAge;
	}
	
	
}
