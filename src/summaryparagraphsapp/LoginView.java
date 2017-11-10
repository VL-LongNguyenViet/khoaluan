package summaryparagraphsapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class LoginView extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	private Label lblLogin;
	private static Text text_5;
	private static Text text_6;
	
	public LoginView(Composite parent, int style) throws ClassNotFoundException, SQLException {
		super(parent, style);
		Connection connection = ConnectionUtils.getMyConnection();
		setLayout(new GridLayout(3, true));
		
		lblLogin = new Label(this, SWT.NONE);
		lblLogin.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblLogin.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblLogin.setText("LOGIN");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblUsername = new Label(this, SWT.CENTER);
		lblUsername.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblUsername.setAlignment(SWT.CENTER);
		lblUsername.setText("Username: ");
		
		text_5 = new Text(this, SWT.BORDER);
		GridData gd_text_5 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text_5.widthHint = 75;
		text_5.setLayoutData(gd_text_5);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblPassword = new Label(this, SWT.CENTER);
		lblPassword.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblPassword.setText("Password: ");
		
		text_6 = new Text(this, SWT.BORDER | SWT.PASSWORD);
		text_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton.widthHint = 62;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("Log in");
		new Label(this, SWT.NONE);
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
						MessageBox box = new MessageBox(getShell(), SWT.OK);
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

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
