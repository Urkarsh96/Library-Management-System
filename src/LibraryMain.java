import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
			SessionFactory factory = new AnnotationConfiguration().addAnnotatedClass(Book.class)
					.addAnnotatedClass(Customer.class).addAnnotatedClass(BookIssue.class)
					.addAnnotatedClass(Employee.class).configure()
					// .addClass(Book.class)
					// .addClass(Customer.class)
					.buildSessionFactory();
			System.out.println("Factory created");
			ArrayList<Object> objects = new ArrayList<>();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			Session session = factory.openSession();
			Transaction trans = session.beginTransaction();

			Customer c1 = null;

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
							break;
						} else
							System.out.println("Invalid User Login");
						// select * from customer where customer_username = 'C_Utkarsh ' and
						// customer_password = 'qwer'
					} else if (username.startsWith("EMP_")) {
						System.out.println("Employee Login");
						System.out.println("Enter your Password");
						String password = br.readLine();
						if (display(session, "*", "employee where employee_username = '" + username
								+ "' and employee_password = '" + password + "'") == 1) {
							System.out.println("Successfully Logged In");
							while (true) {
								System.out.println("Enter Employees ? (y/n)");
								if (br.readLine().charAt(0) == 'y')
									objects.add(addEmployee(br));
								
								else
									break;
							}//session.flush(); trans.commit();
							break;
						} else
							System.out.println("Invalid User Login");
					}

				}

			}
			if (ch == '2')
				c1 = register(br);

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

	public static BookIssue issue(Book book, Customer customer) {
		BookIssue biss = new BookIssue();
		java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
		biss.setStartDate(sqlDate);
		book.setNoOfCopies((book.getNoOfCopies() - 1));
		biss.setBook(book);
		biss.setCust(customer);
		return biss;

	}

	public static Book addbook(BufferedReader br) throws IOException {
		String bookName = br.readLine();
		int NoOfCopies = Integer.parseInt(br.readLine());
		Book suppi1 = new Book();
		suppi1.setNoOfCopies(NoOfCopies);
		suppi1.setBookName(bookName);
		return suppi1;
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
		Customer cust = new Customer();
		cust.setCustomerName(name);
		cust.setCustomerAge((byte) age);
		cust.setUserName(userName);
		cust.setPassword(password);
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
}
