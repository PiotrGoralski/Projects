package Frame;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Table.TableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AskFrame {
    private TableModel model;
    private String[] inputData = new String[4];

    public AskFrame(TableModel model) {
        this.model = model;
    }

    public void start() {
        JFrame frame = new JFrame();

        JLabel l1 = new JLabel("Write name: ");
        l1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JTextField t1 = getFocusedTextField();

        JLabel l2 = new JLabel("Write author: ");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JTextField t2 = getFocusedTextField();

        JLabel l3 = new JLabel("Write price: ");
        l3.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JTextField t3 = getFocusedTextField();
        
        JLabel l4 = new JLabel("Choose cover: ");
        l4.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JTextField t4 = new JTextField();
        t4.setForeground(Color.DARK_GRAY);
        t4.setFont(new Font("Arial", Font.ITALIC, 20));
        t4.setText("Click to choose");
        t4.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent arg0) {
				
				if(t4.getText().equals("Click to choose") || t4.getText().equals(""))
				{
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogTitle("Choose cover");
					chooser.setCurrentDirectory(new File("resources\\covers"));
			        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png", "jpeg");
			            chooser.setFileFilter(filter);
			            int returnVal = chooser.showOpenDialog(chooser);
			            if(returnVal == JFileChooser.APPROVE_OPTION) {
			            	t4.setText(chooser.getSelectedFile().getAbsolutePath());
			            }
			            else 
			            	t4.setText("None");
				}
			}
			
			public void focusLost(FocusEvent e)
			{
				String extension = getFileExtension(t4);
		        File tmpFile = new File(t4.getText());
				boolean r1 = extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png");
		        boolean r2 = tmpFile.exists() && !tmpFile.isDirectory();
		        if (!r1 || !r2)
		        	t4.setText("");
			}
			
        });

        JPanel tmpPanel = new JPanel();
        tmpPanel.setLayout(new GridLayout(4, 2));
        tmpPanel.add(l1);
        tmpPanel.add(t1);
        tmpPanel.add(l2);
        tmpPanel.add(t2);
        tmpPanel.add(l3);
        tmpPanel.add(t3);
        tmpPanel.add(l4);
        tmpPanel.add(t4);

        JPanel labFieldPan = new JPanel();
        labFieldPan.setLayout(new FlowLayout(FlowLayout.CENTER));
        labFieldPan.add(tmpPanel);

        JButton accept = new JButton("Confirm");
        accept.setFont(new Font("Times New Roman", 1, 15));
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(checkInput(t1, t2, t3, t4))
            	{
                    inputData[0] = t1.getText();
                    inputData[1] = t2.getText();
                    inputData[2] = t3.getText();
                    inputData[3] = t4.getText();

                    model.addRow(getRow());
                    
                    FileWriter file = null;
                    BufferedWriter out = null;
                    try {
						file = new FileWriter("Books.txt", true);
						out = new BufferedWriter(file);
						out.newLine();
						out.write(inputData[0]+";"+inputData[1]+";"+inputData[2]+";"+inputData[3]);
	                    out.close();
					} catch (IOException e1) {
						System.err.println("Cannot save book in file");
					}
                   
                    frame.dispose();
            	}
            }
        });

        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("Times New Roman", 1, 15));
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(accept);
        buttonPanel.add(cancel);

        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        frame.getContentPane().add(labFieldPan);
        frame.setSize(350, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private boolean checkInput(JTextField t1, JTextField t2, JTextField t3, JTextField t4) {
        String def = "Enter text here...";
        if (t1.getText().equals("") || t2.getText().equals("") ||
                t3.getText().equals("") || t4.getText().equals("") || t1.getText().equals(def)
                || t2.getText().equals(def) || t3.getText().equals(def) || t4.getText().equals(def)) {
            JOptionPane.showMessageDialog(null, "Wszystkie pola muszą być wypełnione.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String extension = getFileExtension(t4);
        File tmpFile = new File(t4.getText());
        boolean r1 = extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png");
        boolean r2 = tmpFile.exists() && !tmpFile.isDirectory();
        if (!r1 || !r2) {
            JOptionPane.showMessageDialog(null, "Ścieżka musi być do istniejącego obrazku.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String str = t3.getText();
        if (!str.matches("[0-9]*")) {
            JOptionPane.showMessageDialog(null, "Cena musi być liczbą całkowitą.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private Object[] getRow() {
        Object[] res = new Object[4];

        JLabel label = cropIcon(new ImageIcon(inputData[3]));
        res[3] = label;

        for (int i = 0; i < 3; i++) {
            res[i] = inputData[i];
        }
        return res;
    }

    private JLabel cropIcon(ImageIcon imageIcon) {
    	Image tmpImg = imageIcon.getImage().getScaledInstance(150, 180, Image.SCALE_SMOOTH);
    	imageIcon = new ImageIcon(tmpImg);
		JLabel label = new JLabel(imageIcon);
        label.setIcon(imageIcon);
        return label;
    }

    private JTextField getFocusedTextField() {
        JTextField textField = new JTextField("Enter text here...");
        textField.setForeground(Color.DARK_GRAY);
        textField.setFont(new Font("Arial", Font.ITALIC, 20));
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setText("");
            }
        });
        return textField;
    }
    
    private String getFileExtension(JTextField t) {
        String name = t.getText();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }
}