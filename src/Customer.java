import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
/*
 *  customer_id integer NOT NULL,
    customer_name character varying COLLATE pg_catalog."default",
    customer_age smallint
    */
	
	@Id
	@Column(name = "customer_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerId ;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_age")
	private short customerAge;
	
	@Column(name = "customer_username")
	private String userName;
	
	@Column(name = "customer_password")
	private String password;
	
	@Column(name="customer_type")
	private int customerType;
	
	public int getCustomerType() {
		return customerType;
	}
	public void setCustomerType(int customerType) {
		this.customerType = customerType;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public short getCustomerAge() {
		return customerAge;
	}
	public void setCustomerAge(short o) {
		this.customerAge = o;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
