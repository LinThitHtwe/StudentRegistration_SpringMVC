package student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import student.dto.StudentAccountRequestDTO;


@Service("studentAccountDAO")
public class StudentAccountDAO {
	public static Connection con = null;
	static {
		con=MyConncection.getConnection();
	}

	public void insertData(StudentAccountRequestDTO dto) {
		String sql="insert into student_account(student_id,account_id,status) values(?,?,?)";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getStudent_id());
			ps.setInt(2, dto.getAccount_id());
			ps.setInt(3,dto.getStatus());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DataBase Error , insert Error"+e); 
		}
		 
	}
	
	public void banUser(int id) {
		String sql="UPDATE account\r\n" + 
				"JOIN student_account ON account.id = student_account.account_id\r\n" + 
				"SET account.status = 1,\r\n" + 
				"    student_account.delete_status = 1\r\n" + 
				"WHERE student_account.account_id = 1;";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("banUser Error , insert Error"+e); 
		}
		
	}
	
	
	public void unbanUser(int id) {
		String sql="UPDATE account\r\n" + 
				"JOIN student_account ON account.id = student_account.account_id\r\n" + 
				"SET account.status = 0,\r\n" + 
				"    student_account.delete_status = 0\r\n" + 
				"WHERE student_account.account_id = 1;";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("unbanUser Error , insert Error"+e); 
		}
		
	}
	
	public void approveUser(int id) {
		String sql="UPDATE student_account\r\n" + 
				"SET status = 1\r\n" + 
				"WHERE student_id = ?;";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("unbanUser Error , insert Error"+e); 
		}
		
	}
	
	public void rejectUser(int id) {
		String sql="UPDATE student_account\r\n" + 
				"SET status = 2\r\n" + 
				"WHERE student_id = ?;";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("unbanUser Error , insert Error"+e); 
		}
		
	}
}
