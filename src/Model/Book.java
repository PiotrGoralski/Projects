package Model;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Book {

	String name;
	String author;
	int price;
	ImageIcon bookCover;
	
	public Book(String name, String author, int price, String bookCover) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.bookCover = new ImageIcon(bookCover);
    }
	
	public Object[] toArray()
	{
		Image tmpImg = bookCover.getImage().getScaledInstance(150, 180, Image.SCALE_SMOOTH);
        bookCover = new ImageIcon(tmpImg);
		JLabel label = new JLabel(bookCover);
        label.setIcon(bookCover);
		return new Object[] {name, author, price, label};
	}
	
	public String toString() 
	{
		return name + " " + author + " " + price + " " + bookCover;
	}

}
