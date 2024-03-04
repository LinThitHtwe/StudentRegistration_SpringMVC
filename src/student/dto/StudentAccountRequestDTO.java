package student.dto;

public class StudentAccountRequestDTO {
private int id;
private int account_id;
private int student_id;
private int status;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getAccount_id() {
	return account_id;
}
public void setAccount_id(int account_id) {
	this.account_id = account_id;
}
public int getStudent_id() {
	return student_id;
}
public void setStudent_id(int student_id) {
	this.student_id = student_id;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
}
