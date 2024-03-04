package student.dto;



public class StudentRequestDTO {

	private int studentId;
	private String studentName;
	private String dateOfBirth;
	private String gender;
	private String phone;
	private String education;
	private String[] attend;
	private String  photo;
	private String accountId;
	private int status;
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String[] getAttend() {
		return attend;
	}
	public void setAttend(String[] attend) {
		this.attend = attend;
	}
	public String  getPhoto() {
		return photo;
	}
	public void setPhoto(String  photo) {
		this.photo = photo;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
