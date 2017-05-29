package gui;

import java.awt.EventQueue;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import adventureMode.AdventureMain;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Toolkit;

public class MainWindow extends JFrame {

	// This simply gets rid of a warning. I don't know what it is.
	private static final long serialVersionUID = -8205062842025549985L;
	
	private JPanel contentPane;
	private static JTextArea control;
	private static JTextField textField;

	/**
	 * Launch the application.
	 * 
	 */
	
	private static boolean runUI = true;
	
	private JScrollPane scrollPane;
	
	public static void main(String[] args) {
		if(runUI) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						MainWindow frame = new MainWindow();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			

			ExecutorService pool = Executors.newFixedThreadPool(3);
			
			pool.execute(new Runnable() {
				public void run() {
					// Ensures that MainWindow has had time to instantialise control
					while(control == null || textField == null) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					PrintStream out = new PrintStream( new JTextAreaOutputStream( control ) );
					System.setOut( out );
					System.setErr( out );
					
					System.setIn(new JTextFieldInputStream(textField));
					
					System.out.println();
					
					AdventureMain.MainMenu();
				}
			});
		} else {
			AdventureMain.MainMenu();
		}
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		/*
		 * setIconImage should use the classpath to find an image rather than the absolute path being used.
		 */
		setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "\\resources\\Guthix_Tome.png"));
		setTitle("GURPSA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 812, 547);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		textField = new JTextField();
		textField.setBounds(10, 415, 776, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 776, 393);
		contentPane.add(scrollPane);
		
		control = new JTextArea();
		control.setEditable(false);
		scrollPane.setViewportView(control);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textField, contentPane, scrollPane, control}));
	}
}