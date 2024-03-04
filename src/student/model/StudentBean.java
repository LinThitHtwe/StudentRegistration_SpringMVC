package student.model;

import org.springframework.web.multipart.MultipartFile;

public class StudentBean {
private String studentId;
private String studentName;
private String dateOfBirth;
private String gender;
private String phone;
private String education;
private String[] attend;
private MultipartFile  photo;
private String accountId;
public String getStudentId() {
	return studentId;
}
public void setStudentId(String studentId) {
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
public MultipartFile  getPhoto() {
	return photo;
}
public void setPhoto(MultipartFile  photo) {
	this.photo = photo;
}
public String getAccountId() {
	return accountId;
}
public void setAccountId(String accountId) {
	this.accountId = accountId;
}
}
