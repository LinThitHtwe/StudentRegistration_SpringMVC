package student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import student.dto.StudentCourseRequestDTO;




@Service("studentCourseDAO")
public class StudentCourseDAO {
	public static Connection con = null;
	static {
		con=MyConncection.getConnection();
	}

	public void insertData(StudentCourseRequestDTO dto) {
		String sql="insert into student_course(student_id,course_id) values(?,?)";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getStudentId());
			ps.setInt(2, dto.getCourseId());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DataBase Error , insert Error"+e); 
		}
		 
	}
	public void insertStudentsCourses(int studentId, int[] courseIds) {
		String deleteSql="DELETE FROM student_course WHERE student_id=?";
		String insertSql = "insert into student_course(student_id,course_id) values(?,?)";
		try {

			PreparedStatement ps = con.prepareStatement(deleteSql);
			ps.setInt(1, studentId);
			ps.execute();
			
			ps = con.prepareStatement(insertSql);
			
			for (int courseId : courseIds) {
				ps.setInt(1, studentId);
				ps.setInt(2, courseId);
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
	
}
