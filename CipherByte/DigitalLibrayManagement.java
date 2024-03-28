package digitalLibraryManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Book {
	private int id;
	String title;
	String author;
	String genre;
	int year;
	private boolean available;

	public Book(int id, String title, String author, String genre, int year) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.year = year;
		this.available = true;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getGenre() {
		return genre;
	}

	public int getYear() {
		return year;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "ID:" + id + ",Title:" + title + ",Author:" + author + ",Genre:" + genre + ",Year:" + year;
	}
}

class Library {
	private List<Book> books;
	private Map<Integer, String> users;
	private Map<String, String> credentials;

	public Library() {
		books = new ArrayList<>();
		users = new HashMap<>();
		credentials = new HashMap<>();
		credentials.put("admin", "admin123");
	}

	public void addBook(Book book) {
		books.add(book);
	}

	public void deleteBook(int bookId) {
		books.removeIf(book -> book.getId() == bookId);
	}

	public void modifyBook(int bookId, String newTitle, String newAuthor, String newGenre, int newYear) {
		for (Book book : books) {
			if (book.getId() == bookId) {
				book.title = newTitle;
				book.author = newAuthor;
				book.genre = newGenre;
				book.year = newYear;
				System.out.println("Book details modified successfully!");
				return;
			}
		}
		System.out.println("Book not found or invalid ID.");
	}

	public void issueBook(int bookId, int userId) {
		for (Book book : books) {
			if (book.getId() == bookId && book.isAvailable()) {
				book.setAvailable(false);
				users.put(userId, book.getTitle());
				System.out.println("Book issued successfully!");
				return;
			}
		}
		System.out.println("Book not available or invalid ID.");
	}

	public void returnBook(int userId) {
		if (users.containsKey(userId)) {
			String bookTitle = users.remove(userId);
			for (Book book : books) {
				if (book.getTitle().equals(bookTitle)) {
					book.setAvailable(true);
					System.out.println("Book returned successfully!");
					return;
				}
			}
		}
		System.out.println("No book issued to this user.");
	}

	public void generateReport() {
		System.out.println("Available Books:");
		for (Book book : books) {
			if (book.isAvailable()) {
				System.out.println(book);
			}
		}
	}

	public boolean authenticate(String username, String password) {
		return credentials.containsKey(username) && credentials.get(username).equals(password);
	}
}

public class DigitalLibrary {

	public static void main(String[] args) {
		Library library = new Library();

		library.addBook(new Book(1, "Book101", "Author123", "maths", 2000));
		library.addBook(new Book(2, "Book201", "Author234", "Science", 2010));
		library.addBook(new Book(3, "Book301", "Author345", "History", 1995));

		String username = "admin";
		String password = "admin123";
		if (library.authenticate(username, password)) {
			System.out.println("Authentication successful!");

			library.generateReport();

			library.issueBook(1, 101);
			library.generateReport();
			library.returnBook(101);
			library.generateReport();

			library.modifyBook(2, "New Book2 Title", "New Author2", "New Genre", 2020);
			library.generateReport();

			library.deleteBook(3);
			library.generateReport();
		} else {
			System.out.println("Authentication failed!");
		}
	}
}
