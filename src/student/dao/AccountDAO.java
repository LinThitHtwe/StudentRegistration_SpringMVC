package student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import student.dto.AccountRequestDTO;
import student.dto.AccountResponseDTO;




@Service("accountDAO")
public class AccountDAO {
	public static Connection con = null;
	static {
		con=MyConncection.getConnection();
	}

	public void insertData(AccountRequestDTO dto) {
		String sql="insert into account(account_name,account_email,account_password,account_role,status) values(?,?,?,?,?)";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,dto.getName());
			ps.setString(2, dto.getEmail());
			ps.setString(3, dto.getPassword());
			ps.setString(4,dto.getRole());
			ps.setInt(5, 0);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DataBase Error , insert Error"+e); 
		}
		 
	}
	
	public void updateData(AccountRequestDTO dto) {
		String sql="update account set account_name=?,account_email=?,account_password=? where id=?";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setNString(1, dto.getName());
			ps.setNString(2, dto.getEmail());
			ps.setNString(3, dto.getPassword());
			ps.setInt(4, dto.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DataBase Error . Update error"+e); 
		}
		 
	}
	
	public void deleteData(AccountRequestDTO dto) {
		String sql="update account set status=? where id=?";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, 1);
			ps.setInt(2, dto.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DataBase Error . Delete Failed"+e); 
		}
		 
	}
	
	public void unBanUser(AccountRequestDTO dto) {
		String sql="update account set status=? where id=?";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, 0);
			ps.setInt(2, dto.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("DataBase Error . Delete Failed"+e); 
		}
		 
	}
	
//	
	public AccountResponseDTO selectOneUser(AccountRequestDTO dto) {
		AccountResponseDTO res = new AccountResponseDTO();
		String sql="select * from account where id=? and status != 1 and account_role!='admin'";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, dto.getId());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				res.setId(rs.getInt("id"));
				res.setName(rs.getNString("account_name"));
				res.setEmail(rs.getNString("account_email"));
				res.setPassword(rs.getNString("account_password"));
				res.setRole(rs.getNString("account_role"));
				res.setStatus(rs.getInt("status"));
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . Select One User ."+e); 
		}
		return res;
	}
	
	public ArrayList<AccountResponseDTO> selectAllUser() {
		ArrayList<AccountResponseDTO> list = new ArrayList();
		String sql= "select * from account where status != 1 and account_role!='admin'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				AccountResponseDTO res = new AccountResponseDTO();
				res.setId(rs.getInt("id"));
				res.setName(rs.getNString("account_name"));
				res.setEmail(rs.getNString("account_email"));
				res.setPassword(rs.getNString("account_password"));
				res.setRole(rs.getNString("account_role"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . SelectAll User Error"+e); 
		}
		return list;
	}
	
	public ArrayList<AccountResponseDTO> selectAllBannedUser() {
		ArrayList<AccountResponseDTO> list = new ArrayList();
		String sql= "select * from account where status = 1 and account_role!='admin'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				AccountResponseDTO res = new AccountResponseDTO();
				res.setId(rs.getInt("id"));
				res.setName(rs.getNString("account_name"));
				res.setEmail(rs.getNString("account_email"));
				res.setPassword(rs.getNString("account_password"));
				res.setRole(rs.getNString("account_role"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . SelectAll User Error"+e); 
		}
		return list;
	}
	
	public ArrayList<AccountResponseDTO> selectAllOneUser(AccountRequestDTO dto) {
		ArrayList<AccountResponseDTO> list = new ArrayList();
		String sql= "select * from account where id=? and status != 1 and account_role!='admin'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getId());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				AccountResponseDTO res = new AccountResponseDTO();
				res.setId(rs.getInt("id"));
				res.setName(rs.getNString("account_name"));
				res.setEmail(rs.getNString("account_email"));
				res.setPassword(rs.getNString("account_password"));
				res.setRole(rs.getNString("account_role"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . selectAllOneUser Error"+e); 
		}
		return list;
	}
	
	
	public AccountResponseDTO selectOneAdmin(AccountRequestDTO dto) {
		AccountResponseDTO res = new AccountResponseDTO();
		String sql="select * from account where id=? and status != 1 and account_role!='user'";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, dto.getId());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				res.setId(rs.getInt("id"));
				res.setName(rs.getNString("account_name"));
				res.setEmail(rs.getNString("account_email"));
				res.setPassword(rs.getNString("account_password"));
				res.setRole(rs.getNString("account_role"));
				res.setStatus(rs.getInt("status"));
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . Select One User ."+e); 
		}
		return res;
	}
	
	
	public ArrayList<AccountResponseDTO> selectAllAdmin() {
		ArrayList<AccountResponseDTO> list = new ArrayList();
		String sql= "select * from account where status != 1 and account_role!='user'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				AccountResponseDTO res = new AccountResponseDTO();
				res.setId(rs.getInt("id"));
				res.setName(rs.getNString("account_name"));
				res.setEmail(rs.getNString("account_email"));
				res.setPassword(rs.getNString("account_password"));
				res.setRole(rs.getNString("account_role"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . SelectAllAdmin Error"+e); 
		}
		return list;
	}
	
	//-----------------------------------------------------------------------------
	
	
	public ArrayList<AccountResponseDTO> searchByAllUser(AccountRequestDTO dto) {
		ArrayList<AccountResponseDTO> list = new ArrayList();
		String sql= "SELECT * FROM account WHERE id LIKE CONCAT('%', ?, '%') AND account_name LIKE CONCAT('%', ?, '%') AND status != 1 AND account_role !='admin'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getId());
			ps.setString(2, dto.getName());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				AccountResponseDTO res = new AccountResponseDTO();
				res.setId(rs.getInt("id"));
				res.setName(rs.getString("account_name"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
		
		} catch (SQLException e) {
			System.out.println("DataBase Error . searchByALLUser Error"+e); 
		}
		return list;
	}

	public ArrayList<AccountResponseDTO> searchByUserID(AccountRequestDTO dto) {
		ArrayList<AccountResponseDTO> list = new ArrayList();
		String sql= "SELECT * FROM account WHERE id LIKE CONCAT('%', ?, '%') AND status != 1 AND account_role!='admin'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getId());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				AccountResponseDTO res = new AccountResponseDTO();
				res.setId(rs.getInt("id"));
				res.setName(rs.getString("account_name"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . searchByUserID Error"+e); 
		}
		return list;
	}
	
	public ArrayList<AccountResponseDTO> searchByUserName(AccountRequestDTO dto) {
		ArrayList<AccountResponseDTO> list = new ArrayList();
		String sql= "SELECT * FROM account WHERE account_name LIKE CONCAT('%', ?, '%') AND status != 1 AND account_role !='admin'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				AccountResponseDTO res = new AccountResponseDTO();
				res.setId(rs.getInt("id"));
				res.setName(rs.getString("account_name"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . searchByUserName Error"+e); 
		}
		return list;
	}
	
	public AccountResponseDTO getSignupId(AccountRequestDTO dto) {
		AccountResponseDTO res = new AccountResponseDTO();
		String sql="select * from account where account_email=? and status != 1";

		
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, dto.getEmail());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				res.setId(rs.getInt("id"));
				res.setName(rs.getNString("account_name"));
				res.setEmail(rs.getNString("account_email"));
				res.setPassword(rs.getNString("account_password"));
				res.setRole(rs.getNString("account_role"));
				res.setStatus(rs.getInt("status"));
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . Select One User ."+e); 
		}
		return res;
	}
	
	//------------------------------------------------
	
	
	
	
	public ArrayList<AccountResponseDTO> searchByAllAdmin(AccountRequestDTO dto) {
		ArrayList<AccountResponseDTO> list = new ArrayList();
		String sql= "SELECT * FROM account WHERE id LIKE CONCAT('%', ?, '%') AND account_name LIKE CONCAT('%', ?, '%') AND status != 1 AND account_role !='user'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getId());
			ps.setString(2, dto.getName());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				AccountResponseDTO res = new AccountResponseDTO();
				res.setId(rs.getInt("id"));
				res.setName(rs.getString("account_name"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
		
		} catch (SQLException e) {
			System.out.println("DataBase Error . searchByALLUser Error"+e); 
		}
		return list;
	}

	public ArrayList<AccountResponseDTO> searchByAdminID(AccountRequestDTO dto) {
		ArrayList<AccountResponseDTO> list = new ArrayList();
		String sql= "SELECT * FROM account WHERE id LIKE CONCAT('%', ?, '%') AND status != 1 AND account_role!='user'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getId());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				AccountResponseDTO res = new AccountResponseDTO();
				res.setId(rs.getInt("id"));
				res.setName(rs.getString("account_name"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . searchByUserID Error"+e); 
		}
		return list;
	}
	
	public ArrayList<AccountResponseDTO> searchByAdminName(AccountRequestDTO dto) {
		ArrayList<AccountResponseDTO> list = new ArrayList();
		String sql= "SELECT * FROM account WHERE account_name LIKE CONCAT('%', ?, '%') AND status != 1 AND account_role !='user'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				AccountResponseDTO res = new AccountResponseDTO();
				res.setId(rs.getInt("id"));
				res.setName(rs.getString("account_name"));
				res.setStatus(rs.getInt("status"));
				list.add(res);  
			}
			
		} catch (SQLException e) {
			System.out.println("DataBase Error . searchByUserName Error"+e); 
		}
		return list;
	}
	

	
	
}
