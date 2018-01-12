import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Book")
public class Book {
	@Id
	@Column(name = "book_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int bookId;

	@Column(name = "no_of_copies")
	private int noOfCopies;

	@Column(name = "book_name")
	private String bookName;
	
	@Column(name= "book_type")
	private String bookType;

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String book_Name) {
		this.bookName = book_Name;
	}

}
