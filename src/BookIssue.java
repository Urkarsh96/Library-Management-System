import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "book_issue")
public class BookIssue {
	@Id
	@Column(name = "issue_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long IssueID;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "no_of_days")
	private short noOfDays;
	
	@OneToOne
	private Customer cust;
	
	@OneToOne
	private Book book;
	
	public Customer getCust() {
		return cust;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}


	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public short getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(short noOfDays) {
		this.noOfDays = noOfDays;
	}

	public long getIssueID() {
		return IssueID;
	}

	public void setIssueID(long issueID) {
		IssueID = issueID;
	}

}
