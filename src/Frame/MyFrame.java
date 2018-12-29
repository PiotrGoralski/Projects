package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import Label.LabelRenderer;
import Model.Book;
import Table.TableModel;

public class MyFrame {

    private List<Book> books;
    private TableModel model;

    public MyFrame(List<Book> books) {
        this.books = books;
    }

    public void start(){
        JFrame frameForTable = new JFrame();
        frameForTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columns = {"Name", "Author", "Price", "Cover"};
        List<Object[]> datas = new ArrayList<>();
                        
        for(int i=0; i<books.size(); i++)
        {
        	datas.add(books.get(i).toArray());
        }
        
        model = new TableModel(datas, columns);

        JTable table = new JTable(model);
        table.setLayout(new BorderLayout());

        JTableHeader headerEdit = table.getTableHeader();
        headerEdit.setFont(new Font("Arial", 1, 25));
        headerEdit.setForeground(Color.DARK_GRAY);
        table.setTableHeader(headerEdit);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.setDefaultRenderer(String.class, centerRenderer);
        table.setDefaultRenderer(Integer.class, centerRenderer);
        
        table.setFont(new Font("Times New Roman", 0, 20));
        table.setLayout(new FlowLayout(FlowLayout.CENTER));
        table.getColumn("Cover").setCellRenderer(new LabelRenderer());
       
        JScrollPane tabelPanel = new JScrollPane(table);

        JButton create = new JButton("Create");
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AskFrame askFrame = new AskFrame(model);
                askFrame.start();
            }
        });
        JButton delete = new JButton("Delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1)
                {
                	Scanner scanner=null;
					try {
						scanner = new Scanner(new File("Books.txt"));
					} catch (FileNotFoundException e2) {
						System.err.println("Error during opening Scanner");
					}
                	List<String> list = new ArrayList<>();
                	int line=0;
                	while(scanner.hasNextLine())
                	{
                		if(line!=table.getSelectedRow())
	                		list.add(scanner.nextLine());
                		else 
                			scanner.nextLine();
                		line++;
                	}
                	
                	PrintWriter print = null;
					try {
						print = new PrintWriter(new File("Books.txt"));
						for(int i=0; i<list.size()-1; i++)
						{
							print.println(list.get(i));
						}
						print.print(list.get(list.size()-1));
						print.close();
					} catch (FileNotFoundException e1) {
						System.err.println("Error during saving PrintWriter");
					}
						
                    model.removeRow(table.getSelectedRow());
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(create);
        buttonPanel.add(delete);

        frameForTable.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        frameForTable.getContentPane().add(tabelPanel);
        frameForTable.setSize(900, 700);
        frameForTable.setLocationRelativeTo(null);
        frameForTable.setVisible(true);
    }
}
