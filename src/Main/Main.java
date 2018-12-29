package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Frame.MyFrame;
import Model.Book;

public class Main {
	
	public static void main(String[] args)
	{
		List<Book> books = new ArrayList<>(loadBooks());
		
		MyFrame frame = new MyFrame(books);
		frame.start();
	}
	
	public static List<Book> loadBooks()
	{
		List<Book> books = new ArrayList<>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("Books.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("Wrong file");
		}

		while(scanner.hasNextLine())
		{
			String [] table = scanner.nextLine().split(";");
			books.add(new Book(table[0], table[1], Integer.parseInt(table[2]), table[3]));
		}
		
		return books;
	}

}
