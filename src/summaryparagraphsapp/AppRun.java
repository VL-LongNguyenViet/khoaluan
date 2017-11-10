package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 * Start view
 */

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.jfree.ui.RefineryUtilities;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.ScrolledComposite;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities;
import java.awt.Canvas;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.RowLayout; 

public class AppRun {

	protected static String userType;
	protected static int userID;
    protected static Shell shell;
	private  Text text;
	private ProgressBar progressBar;
	private Button btnNewButton_1;
	private Composite composite_1;
	private Text text_1;
	private Label lblNewLabel;
	private Text text_2;
	private Button btnNewButton_2;
	private Composite composite_2;
	private Composite composite_3;
	private Composite composite_4;
	private static Composite composite;
	private static Composite composite_5;
	private Text text_3;
	private Text text_4;
	private static Text text_5;
	private static Text text_6;
	private static Text text_7;
	private static Text text_8;
	private static Text text_9;
	private Label lblLogin;

	/**
	 * Launch the application.
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) {
		shell = new Shell();
		AppRun appRun = new AppRun();
		try {
			appRun.open();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void open() throws ClassNotFoundException, SQLException {
		Display display = Display.getDefault();
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				/*MessageBox box = new MessageBox(shell);
				box.setText("Thank you");
				box.setMessage("Thank you for using our app !");
				box.open();*/
			}
		});
		shell.setSize(450, 300);
		shell.setText("Summarizing app");
		createContents();
		shell.setMaximized(true);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	protected void createContents() throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtils.getMyConnection();
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		composite = new Composite(shell, SWT.BORDER);
		composite.setLayout(new GridLayout(3, true));
		
		lblLogin = new Label(composite, SWT.NONE);
		lblLogin.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblLogin.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblLogin.setText("LOGIN");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblUsername = new Label(composite, SWT.CENTER);
		lblUsername.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblUsername.setAlignment(SWT.CENTER);
		lblUsername.setText("Username: ");
		
		text_5 = new Text(composite, SWT.BORDER);
		GridData gd_text_5 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text_5.widthHint = 75;
		text_5.setLayoutData(gd_text_5);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblPassword = new Label(composite, SWT.CENTER);
		lblPassword.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblPassword.setText("Password: ");
		
		text_6 = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		text_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton.widthHint = 62;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("Log in");
		new Label(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					login (text_5.getText(), text_6.getText(), connection);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
				
		});
		
		composite_5 = new Composite(shell, SWT.BORDER);
		composite_5.setLayout(new GridLayout(3, true));
		
		Label lblRegister = new Label(composite_5, SWT.CENTER);
		lblRegister.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblRegister.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblRegister.setText("REGISTER");
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		
		Label lblUsername_1 = new Label(composite_5, SWT.NONE);
		lblUsername_1.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblUsername_1.setText("Username: ");
		
		text_7 = new Text(composite_5, SWT.BORDER);
		text_7.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		
		Label lblPassword_1 = new Label(composite_5, SWT.NONE);
		lblPassword_1.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblPassword_1.setText("Password: ");
		
		text_8 = new Text(composite_5, SWT.BORDER | SWT.PASSWORD);
		text_8.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		
		Label lblConfirmPassword = new Label(composite_5, SWT.NONE);
		lblConfirmPassword.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblConfirmPassword.setText("Confirm password: ");
		
		text_9 = new Text(composite_5, SWT.BORDER | SWT.PASSWORD);
		text_9.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		
		Button btnRegister = new Button(composite_5, SWT.NONE);
		btnRegister.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					register(text_7.getText(), text_8.getText(), text_9.getText(), connection);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridData gd_btnRegister = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnRegister.widthHint = 70;
		btnRegister.setLayoutData(gd_btnRegister);
		btnRegister.setText("Register");
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
	}
	
	//Register
	public static void register(String username, String password, String confirmPassword, Connection connection) throws SQLException {
		String sql1 = "SELECT username FROM users";
		PreparedStatement statement1 = connection.prepareStatement(sql1);
		ResultSet rs = statement1.executeQuery();
		boolean registeredAccount = false;
		while(rs.next()) {
			if (rs.getString("username").equals(username)) {
				MessageBox box = new MessageBox(shell, SWT.OK);
				box.setText("Message");
				box.setMessage("Registered username !");
				box.open();
				registeredAccount = true;
			}
		}
		if (registeredAccount == false) {
			if (!password.equals(confirmPassword)) {
				MessageBox box = new MessageBox(shell, SWT.OK);
				box.setText("Message");
				box.setMessage("Password and confirmation password not match !");
				box.open();
			}
			else {
				String sql = "INSERT INTO users(user_type, confirmation, username, password) VALUES (?,?,?,?)";
				PreparedStatement statement = connection.prepareStatement(sql);
		        statement.setString(1, "user");
		        statement.setInt(2, 0);
		        statement.setString(3, username);
		        statement.setString(4, password);
		        statement.execute();
		        MessageBox box = new MessageBox(shell, SWT.OK);
				box.setText("Message");
				box.setMessage("Sent register request to admin/super admin !");
				box.open();
			}
		}
	}
	
	// Login 
	public static void login(String username, String password, Connection connection) throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM users WHERE username LIKE ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, username);
		ResultSet rs = statement.executeQuery();
		boolean unregisteredAccount = true;
		while (rs.next()) {
			String registeredPassword = rs.getString("password");
			if (password.equals(registeredPassword)) {
				if (rs.getInt("confirmation") == 0) {
					MessageBox box = new MessageBox(shell, SWT.OK);
					box.setText("Message");
					box.setMessage("Waiting for confirmation from admin/super admin !");
					box.open();
				}
				else {
					composite.dispose();
					composite_5.dispose();
					userID = rs.getInt("id");
					userType = rs.getString("user_type");
					if (userType.equals("user")) {
						createUserView();
					}
					else if (userType.equals("super_admin")) {
						createSuperAdminView();
					}
					else if (userType.equals("admin")) {
						createAdminView();
					}
					// Save to activities_diary
					String activity = "Login";
					String content = "Login";
					String databaseImpact = "Nothing";
					MultiParagraphsSummarizingView.insertToActivitiesDiary(activity, content, databaseImpact);
					MessageBox box = new MessageBox(shell, SWT.OK);
					box.setText("Message");
					box.setMessage("Login successfully !");
					box.open();
				}
			}
			else {
				MessageBox box = new MessageBox(shell, SWT.OK);
				box.setText("Message");
				box.setMessage("Wrong password !");
				box.open();	
			}
			unregisteredAccount = false;
		}
		if (unregisteredAccount == true) {
			MessageBox box = new MessageBox(shell, SWT.OK);
			box.setText("Message");
			box.setMessage("Unregistered account !");
			box.open();
		}
	}
	
	// Create user view
	public static void createUserView() throws ClassNotFoundException, SQLException {
		UserView userView = new UserView(shell, SWT.NONE);
		shell.open();
		shell.layout();
	}
		
	// Create admin view
	public static void createAdminView() throws ClassNotFoundException, SQLException {
		AdminView adminView = new AdminView(shell, SWT.NONE);
		shell.open();
		shell.layout();
	}
		
	// Create super admin view
	public static void createSuperAdminView() throws ClassNotFoundException, SQLException {
		SuperAdminView superAdminView = new SuperAdminView(shell, SWT.NONE);
		shell.open();
		shell.layout();
	}
		
	// Create information view
	public static void createInformationView() throws ClassNotFoundException, SQLException {
		InformationView informationView = new InformationView(shell, SWT.NONE);
		shell.open();
		shell.layout();
	}
	
	// Create hot articles view
	public static void createHotArticlesView() throws ClassNotFoundException, SQLException {
		HotArticlesView hotArticlesView = new HotArticlesView(shell, SWT.NONE);
		shell.open();
		shell.layout();
	}
		
	// Create history view
	public static void createSingleSummarizingView() throws ClassNotFoundException, SQLException {
		SingleSummarizingView singleSummarizingView = new SingleSummarizingView(shell, SWT.NONE);
		shell.open();
		shell.layout();
	}
		
	// Create history view
	public static void createSingleSummarizingHistoryView() throws ClassNotFoundException, SQLException {
		SingleSummarizingHistoryView singleSummarizinghistoryView = new SingleSummarizingHistoryView(shell, SWT.NONE);
		shell.open();
		shell.layout();
	}
		
	// Create chart view
	public static void createChartView() {
		ChartView barChart = new ChartView (shell, SWT.NONE);
		shell.open();
		shell.layout();
	}
	
	// Create multi paragraps summarizing view
	public static void createMultiParargraphsSummarizingView() {
		MultiParagraphsSummarizingView multiSummarize = new MultiParagraphsSummarizingView (shell, SWT.NONE);
		shell.open();
		shell.layout();
	}
	
	// Create multi summarizing history view
	public static void createMultiSummarizingHistoryView() throws ClassNotFoundException, SQLException {
		MultiSummarizingHistoryView historyView = new MultiSummarizingHistoryView(shell, SWT.NONE);
		shell.open();
		shell.layout();
	}
}
