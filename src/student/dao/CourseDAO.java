package student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import student.dto.CourseRequestDTO;
import student.dto.CourseResponseDTO;





@Service("courseDAO")
public class CourseDAO {
	public static Connection con = null;
	static {
		con=MyConncection.getConnection();
	}

	public void insertData(CourseRequestDTO dto) {
		String sql="insert into course(course_name,status) values(?,?)";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getCourseName());
			ps.setInt(2, 0);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DataBase Error , insert Error"+e); 
		}
		 
	}
	
	public void updateData(CourseRequestDTO dto) {
		String sql="update course set course_name=?where id=?";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setNString(1, dto.getCourseName());
			ps.setNString(2, dto.getCourseId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DataBase Error . Update error"+e); 
		}
		 
	}
	
	public void deleteData(CourseRequestDTO dto) {
		String sql="update course set status where book_code=?";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, 1);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DataBase Error "+e); 
		}
		 
	}
//	
	public CourseResponseDTO selectOne(CourseRequestDTO dto) {
		CourseResponseDTO res = new CourseResponseDTO();
		String sql="select * from course where id=?";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setNString(1, dto.getCourseId());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				res.setCourseId(rs.getInt("id"));
				res.setCourseName(rs.getString("course_name"));
				res.setStatus(rs.getInt("status"));
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . Select One"+e); 
		}
		return res;
	}
	
	
	public ArrayList<CourseResponseDTO> selectOneName(String Name) {
		CourseResponseDTO res = new CourseResponseDTO();
		String sql="select * from course where course_name=?";
		ArrayList<CourseResponseDTO> list = new ArrayList();
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setNString(1, Name);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				res.setCourseId(rs.getInt("id"));
				res.setCourseName(rs.getString("course_name"));
				res.setStatus(rs.getInt("status"));
				list.add(res);
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . Select One"+e); 
		}
		return list;
	}
	
	public ArrayList<CourseResponseDTO> selectAll() {
		ArrayList<CourseResponseDTO> list = new ArrayList();
		String sql= "select * from course where status != 1";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				CourseResponseDTO res = new CourseResponseDTO();
				res.setCourseId(rs.getInt("id"));
				res.setCourseName(rs.getString("course_name"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . SelectAll Error"+e); 
		}
		return list;
	}
	
	//-----------------------------------------------------------------------------
	
	
	public ArrayList<CourseResponseDTO> searchByAll(CourseRequestDTO dto) {
		ArrayList<CourseResponseDTO> list = new ArrayList();
		String sql= "SELECT * FROM course WHERE id LIKE CONCAT('%', ?, '%') AND course_name LIKE CONCAT('%', ?, '%') AND status != 1";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getCourseId());
			ps.setString(2, dto.getCourseName());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				CourseResponseDTO res = new CourseResponseDTO();
				res.setCourseId(rs.getInt("id"));
				res.setCourseName(rs.getString("course_name"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . searchByID Error"+e); 
		}
		return list;
	}
	
	public ArrayList<CourseResponseDTO> searchByID(CourseRequestDTO dto) {
		ArrayList<CourseResponseDTO> list = new ArrayList();
		String sql= "SELECT * FROM course WHERE id LIKE CONCAT('%', ?, '%') AND status != 1";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getCourseId());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				CourseResponseDTO res = new CourseResponseDTO();
				res.setCourseId(rs.getInt("id"));
				res.setCourseName(rs.getString("course_name"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . searchByID Error"+e); 
		}
		return list;
	}
	
	public ArrayList<CourseResponseDTO> searchByName(CourseRequestDTO dto) {
		ArrayList<CourseResponseDTO> list = new ArrayList();
		String sql= "SELECT * FROM course WHERE course_name LIKE CONCAT('%', ?, '%') AND status != 1";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getCourseName());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				CourseResponseDTO res = new CourseResponseDTO();
				res.setCourseId(rs.getInt("id"));
				res.setCourseName(rs.getString("course_name"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . searchByID Error"+e); 
		}
		return list;
	}
	
	
}
