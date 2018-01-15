import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

public class LibraryMain {

	public static void deleteAll(Session session, String tablename) {
		SQLQuery delquery = session.createSQLQuery("delete from " + tablename);
		delquery.executeUpdate();
	}

	public static void print1(Session session, String query) {
		SQLQuery qry = session.createSQLQuery(query);
		List l = qry.list();
		System.out.println("Total Number Of Records : " + l.size());
		Iterator it = l.iterator();

		while (it.hasNext()) {
			Object o[] = (Object[]) it.next();

			for (Object item : o) {
				System.out.print(item + "   |   ");
			}
			System.out.println();
			// System.out.println("Supplierid : "+o[0]+ "\t Supplierphoneno : "+o[1]);

		}
	}

	public static int display(Session session, String select, String tablename) {
		SQLQuery query = session.createSQLQuery("Select " + select + " from " + tablename);

		System.out.println("Query executed ");
		List l = query.list();
		System.out.println("Total Number Of Records : " + l.size());
		Iterator it = l.iterator();
		while (it.hasNext()) {
			Object o[] = (Object[]) it.next();
			for (Object item : o) {
				System.out.print(item + " ");
			}
			System.out.println();
			System.out.println("----------------");
		}
		return l.size();
	}

	public static void insert(Session session, ArrayList<Object> obj) {
		for (Object o : obj) {
			session.save(o);
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		try {

			System.out.println("WELCOME TO LIBRARY MANAGEMENT SYSTEM");
			
			System.out.println("");
			System.out.println("--------------------------------------------------------");
			System.out.println("");
			SessionFactory factory = new AnnotationConfiguration().addAnnotatedClass(Book.class)
					.addAnnotatedClass(Customer.class).addAnnotatedClass(BookIssue.class)
					.addAnnotatedClass(Employee.class).configure()

					.buildSessionFactory();
			System.out.println("Factory created");
			ArrayList<Object> objects = new ArrayList<>();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			Session session = factory.openSession();
			Transaction trans = session.beginTransaction();

			/*
			 * Employee emp= addEmployee(br); session.save(emp);
			 * 
			 * session.flush(); trans.commit();
			 */
			// objects.add(emp);
			System.out.println("Select your option \n" + "1. Login \n" + "2. Register\n");
			char ch = br.readLine().charAt(0);

			if (ch == '1') { // LOGIN
				while (true) {
					System.out.println("Enter your Username");
					String username = br.readLine();
					if (username.startsWith("C_")) {
						System.out.println("Customer Login");
						System.out.println("Enter your Password");
						String password = br.readLine();
						if (display(session, "*", "customer where customer_username = '" + username
								+ "' and customer_password = '" + password + "'") == 1) {
							System.out.println("Successfully Logged In");
							System.out.println("Search Books (y/n");
							if (br.readLine().charAt(0) == 'y') {
								System.out.println("1. Search By Name");
								System.out.println("2. Search By Genre");
								System.out.println("3. Search Issued Books");
								char ch1 = br.readLine().charAt(0);
								if (ch1 == '1') {// Searchbyname

									String bookName = br.readLine();
									searchByName(session, bookName);
								} else if (ch1 == '2') {
									System.out.println("Enter Genre");
									String genre = br.readLine();
									searchByGenre(session, genre);
								} else if (ch1 == '3') {
									book_Issued_Search(session, 3);

								}

							}

							break;
						} else
							System.out.println("Invalid User Login");
					}

					//
					else if (username.startsWith("EMP_")) {
						System.out.println("Employee Login");
						System.out.println("Enter your Password");
						String password = br.readLine();
						SQLQuery query = session.createSQLQuery("select * from employee where employee_username = "
								+ "'" + username + "' and employee_password = '" + password + "'");
						List emp = query.list();
						/* Employee e=(Employee)emp.get(0); */
						Employee e = new Employee();
						// e.setEmployeeType(true);
						Iterator it = emp.iterator();
						if (it.hasNext()) {
							Object o[] = (Object[]) it.next();
							e.setEmployeeId(Integer.parseInt(o[0].toString()));
							e.setEmployeeName(o[1].toString());
							e.setEmployeePassword(o[2].toString());
							String bool = o[3].toString();
							bool = "true";
							System.out.println("type is =" + bool);
							e.setEmployeeType(Boolean.valueOf((bool)));
							System.out.println("Employee type is " + e.isEmployeeType());
							e.setEmployeeUserName(o[4].toString());
							System.out.println(Arrays.toString(o));
						}
						if (display(session, "*", "employee where employee_username = '" + username
								+ "' and employee_password = '" + password + "'") == 1) {
							System.out.println("Successfully Logged In" + e.getEmployeeId() + e.getEmployeeName()
									+ e.getEmployeeUserName() + " " + e.isEmployeeType());
							while (true) {
								System.out.println("Select your option\n"
										+ "1.Add Book\n2.Remove Book\n3.Issue Book\n4.Return Book");

								if (e.isEmployeeType()) {
									System.out.println("5. Add Employees\n6.Remove Employees");
								}
								System.out.println("7.Exit");
								char in1 = br.readLine().charAt(0);
								System.out.println("pressed " + in1);

								if (in1 == '1') {
									objects.add(addbook(br));
								}
								if (in1 == '2') {
									System.out.println("Enter a book id to be removed");
									int bookId = Integer.parseInt(br.readLine());
									deleteBook(br, session, bookId);
								}
								if (in1 == '3') {
									String bName = br.readLine();
									String cName = br.readLine();

									Book b = getBook(session, bName);
									Customer c = getCustomer(session, cName);
									System.out.println("No of Copies = " + b.getNoOfCopies());
									if (b.getNoOfCopies() > 0) {
										b.setNoOfCopies(b.getNoOfCopies() - 1);
										session.update(b);
										objects.add(issue(b, c));
									} else {
										System.out.println("Book Out Of Stock");
									}

								}
								if (in1 == '4') {
									System.out.println("Return Book");
									String bName = br.readLine();
									String cName = br.readLine();

									Book b = getBook(session, bName);
									b.setNoOfCopies(b.getNoOfCopies() + 1);
									session.update(b);
									Customer c = getCustomer(session, cName);
									return_Book(session, c, b, br);
								}
								if (in1 == '5') {
									System.out.println("Enter Employees ? (y/n)");
									if (br.readLine().charAt(0) == 'y')
										objects.add(addEmployee(br));

								}
								if (in1 == '6') {
									System.out.println("Remove Employees ? (y/n)");
									if (br.readLine().charAt(0) == 'y') {
										System.out.println("Enter Employee ID");
										int eId = Integer.parseInt(br.readLine());
										del_Library_Staff(session, eId);
									}
								}
								if (in1 == '7') {
									System.out.println("Exiting");
									break;

								}
							} // session.flush(); trans.commit();
							break;
						} else
							System.out.println("Invalid Employee Login");
					} else
						System.out.println("Incorrect Username");
				}

			}
			if (ch == '2')
				objects.add(register(br));

			// deleteAll(session, "book_issue");
			// deleteAll(session, "book");
			// deleteAll(session, "customer");

			/*
			 * Book b1 = addbook(br); BookIssue bi1 = issue(b1, c1); objects.add(b1);
			 * objects.add(c1); objects.add(bi1);
			 */
			insert(session, objects);
			session.flush();
			trans.commit();
			// display(session,"*","book");
			// display(session, "*", "customer");
			/*
			 * display(session,"*","book_issue");
			 * display(session,"customer.customer_name,book.book_name " ,
			 * "book_issue,book,customer where" + " book_issue.book_book_id=book.book_id " +
			 * "and book_issue.cust_customer_id=customer.customer_id");
			 */
			// display(session,"book_issue,book,customer");
			/*
			 * display(session,"book_issue,book,customer" +
			 * " where book_issue.book_id=book.book_id " +
			 * "and book_issue.customer_id=customer.customer_id");
			 */
			/*
			 * "select customer.customer_name,book.book_name" +
			 * " from book_issue,book,customer where book_issue.book_id=book.book_id" +
			 * " and book_issue.customer_id=customer.customer_id"
			 */
			session.close();
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
	}

	private static Customer getCustomer(Session session, String cName) {
		// TODO Auto-generated method stub
		SQLQuery query = session.createSQLQuery("select * from Customer where customer_name = " + "'" + cName + "'");
		List cust = query.list();
		/* Employee e=(Employee)emp.get(0); */

		Customer c = new Customer();

		Iterator it = cust.iterator();
		if (it.hasNext()) {
			Object o[] = (Object[]) it.next();

			System.out.println(Arrays.toString(o));

			c.setCustomerId(Integer.parseInt(o[0].toString()));
			c.setCustomerName(o[1].toString());
			c.setCustomerAge((short) o[2]);
			c.setUserName(o[3].toString());
			c.setPassword(o[4].toString());
		}
		return c;
	}

	private static Book getBook(Session session, String bName) {
		// TODO Auto-generated method stub
		SQLQuery query = session.createSQLQuery("select * from Book where book_name = " + "'" + bName + "'");
		List boo = query.list();
		/* Employee e=(Employee)emp.get(0); */

		Book b = new Book();

		Iterator it = boo.iterator();
		if (it.hasNext()) {
			Object o[] = (Object[]) it.next();

			System.out.println(Arrays.toString(o));

			b.setBookId(Integer.parseInt(o[0].toString()));
			b.setNoOfCopies(Integer.parseInt(o[1].toString()));
			b.setBookName(o[2].toString());
			b.setBookType(o[3].toString());
			System.out.println("No of copies in " + b.getBookName() + " are " + b.getNoOfCopies());
		}

		return b;
	}

	private static void searchByName(Session session, String bookName) {
		// TODO Auto-generated method stub
		display(session, "*", "book where book_name = '" + bookName + "'");
	}

	private static void searchByGenre(Session session, String genre) {
		// TODO Auto-generated method stub
		display(session, "*", "book where book_type = '" + genre + "'");

	}

	public static void book_Issued_Search(Session session, int cId) {
		String query = "select * from book_issue where cust_customer_id=" + cId;
		print1(session, query);
	}

	public static BookIssue issue(Book book, Customer customer) {
		BookIssue biss = new BookIssue();
		java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
		biss.setStartDate(sqlDate);
		biss.setNoOfDays((short) 0);
		biss.setBook(book);
		biss.setCust(customer);
		return biss;

	}

	public static Book addbook(BufferedReader br) throws IOException {
		System.out.println("Enter Book Name");
		String bookName = br.readLine();
		System.out.println("Enter Book No Of Copies");
		int NoOfCopies = Integer.parseInt(br.readLine());
		System.out.println("Enter Book Type");
		String bookType = br.readLine();
		Book suppi1 = new Book();
		suppi1.setNoOfCopies(NoOfCopies);
		suppi1.setBookName(bookName);
		suppi1.setBookType(bookType);
		System.out.println("New Book Created");
		return suppi1;
	}

	public static void deleteBook(BufferedReader br, Session session, int bookId) {

		SQLQuery del = session.createSQLQuery("update book set no_of_copies=0 where book_id=" + bookId);
		del.executeUpdate();
	}

	public static void return_Book(Session session, Customer c, Book b, BufferedReader br)
			throws NumberFormatException, IOException {

		String query = "Select issue_id from book_issue where cust_customer_id =" + c.getCustomerId()
				+ " and book_book_Id = " + b.getBookId();
		SQLQuery qry = session.createSQLQuery(query);
		List<BigInteger> inte = qry.list();
		BigInteger issId = inte.get(0);
		System.out.println("Enter the No Of Days");
		int noOfDays = Integer.parseInt(br.readLine());
		// cust type extract and custId extaract and book id extract from object
		int type = c.getCustomerType();
		int fine = calculateFine(noOfDays, type);
		System.out.println("The fine is" + fine);
		int fineCollected;
		if (fine > 0) {
			System.out.println("Enter 1 to pay else 0");
			fineCollected = Integer.parseInt(br.readLine());
		} else
			fineCollected = 1;
		if (fineCollected == 1) {
			String query1 = "update book_issue set no_of_days=" + noOfDays + " where cust_customer_id = "
					+ c.getCustomerId() + "  and book_book_id = " + b.getBookId();
			SQLQuery qry1 = session.createSQLQuery(query1);
			qry1.executeUpdate();

		}

	}

	public static int calculateFine(int days, int type) {
		int fine;
		if (type == 1) // TEACHER
		{
			if (days <= 180)
				fine = 0;
			else
				fine = (days - 180) * 2;
		} else // STUDENT
		{
			if (days <= 15)
				fine = 0;
			else
				fine = (days - 15) * 4;
		}
		return fine;
	}

	private static Customer register(BufferedReader br) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Enter your Name");
		String name = br.readLine();
		System.out.println("Enter your Age");
		int age = Integer.parseInt(br.readLine());
		String userName = "C_" + name;
		System.out.println("Enter your Password");
		String password = br.readLine();
		System.out.println("Are You a Student Or a teacher ? (s/t)");
		char sOrT = br.readLine().charAt(0);
		int type;
		if (sOrT == 't')
			type = 1;
		else
			type = 0;
		Customer cust = new Customer();
		cust.setCustomerName(name);
		cust.setCustomerAge((byte) age);
		cust.setUserName(userName);
		cust.setPassword(password);
		cust.setCustomerType(type);
		System.out.println("Your Username is " + userName);

		return cust;

	}

	public static Employee addEmployee(BufferedReader br) throws IOException {
		System.out.println("Enter Employee Name");

		String empName = br.readLine();

		Boolean type = false;
		System.out.println("Enter your Password");
		String password = br.readLine();
		Employee emp = new Employee();
		emp.setEmployeeName(empName);
		emp.setEmployeePassword(password);
		emp.setEmployeeType(type);
		emp.setEmployeeUserName("EMP_" + empName);

		return emp;
	}

	public static void del_Library_Staff(Session session, int eId) {
		String query = "delete from employee where employee_id=" + eId;
		SQLQuery qry = session.createSQLQuery(query);
		qry.executeUpdate();
		// sesson.save();
		String query1 = "select * from employee";
		print1(session, query);

	}
}
