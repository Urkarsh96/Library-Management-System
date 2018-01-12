import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Employee")
public class Employee {
 @Id
 @Column(name = "employee_id")
 @GeneratedValue(strategy = GenerationType.AUTO)
 private int employeeId;

 @Column(name = "employee_name")
 private String employeeName;

 @Column(name = "employee_password")
 private String employeePassword;

 @Column(name = "employee_type")
 private boolean employeeType;

 @Column(name = "employee_username")
 private String employeeUserName;

 public int getEmployeeId() {
  return employeeId;
 }

 public void setEmployeeId(int employeeId) {
  this.employeeId = employeeId;
 }

 public String getEmployeeName() {
  return employeeName;
 }

 public void setEmployeeName(String employeeName) {
  this.employeeName = employeeName;
 }

 public String getEmployeePassword() {
  return employeePassword;
 }

 public void setEmployeePassword(String employeePassword) {
  this.employeePassword = employeePassword;
 }

 public boolean isEmployeeType() {
  return employeeType;
 }

 public void setEmployeeType(boolean employeeType) {
  this.employeeType = employeeType;
 }

 public String getEmployeeUserName() {
  return employeeUserName;
 }

 public void setEmployeeUserName(String employeeusername) {
  this.employeeUserName = employeeusername;
 }

}