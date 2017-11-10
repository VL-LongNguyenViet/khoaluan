package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 * Super admin managing view
 */

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SuperAdminManagingView extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	
	private String chosenAccount;
	
	public SuperAdminManagingView(Composite parent, int style) throws ClassNotFoundException, SQLException {
		super(parent, style);
		setLayout(new GridLayout(3, true));
		
		Label lblManagingAccounts = new Label(this, SWT.NONE);
		lblManagingAccounts.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1));
		lblManagingAccounts.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblManagingAccounts.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblManagingAccounts.setText("Managing accounts");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblAdmins = new Label(this, SWT.NONE);
		lblAdmins.setAlignment(SWT.CENTER);
		lblAdmins.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblAdmins.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblAdmins.setText("Choose type of account :");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Combo combo = new Combo(this, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo.add("Admins");
		combo.add("Users");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblUsers = new Label(this, SWT.NONE);
		lblUsers.setAlignment(SWT.CENTER);
		lblUsers.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblUsers.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblUsers.setText("Admins/Users list :");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Combo combo_1 = new Combo(this, SWT.NONE);
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblOptions = new Label(this, SWT.NONE);
		lblOptions.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblOptions.setText("Options :");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Button btnSetAsAdmin = new Button(this, SWT.RADIO);
		btnSetAsAdmin.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btnSetAsAdmin.setText("Set as admin");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Button btnSetAsUser = new Button(this, SWT.RADIO);
		btnSetAsUser.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btnSetAsUser.setText("Set as user ");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Button btnSetAsUnconfirmed = new Button(this, SWT.RADIO);
		btnSetAsUnconfirmed.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btnSetAsUnconfirmed.setText("Set as unconfirmed user");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Button btnDeleteAccount = new Button(this, SWT.RADIO);
		btnDeleteAccount.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btnDeleteAccount.setText("Delete account");
		new Label(this, SWT.NONE);

		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selection = combo.getItem(combo.getSelectionIndex());
				if (selection.equals("Admins")) {
					try {
						// Get admins list
						getAllAdmins(combo_1);
						lblUsers.setText("Admins list :");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if (selection.equals("Users")) {
					try {
						// Get users list
						getAllUsers(combo_1);
						lblUsers.setText("Users list :");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				chosenAccount = combo_1.getItem(combo_1.getSelectionIndex());
				if (combo.getItem(combo.getSelectionIndex()).equals("Admins")) {
					btnSetAsUser.setSelection(false);
					btnSetAsUnconfirmed.setSelection(false);
					btnSetAsAdmin.setSelection(true);
					btnDeleteAccount.setSelection(false);
				}
				else if (combo.getItem(combo.getSelectionIndex()).equals("Users")) {
					try {
						AdminManagingView.getUserConfirmation(chosenAccount, btnSetAsUser, btnSetAsUnconfirmed);
						btnSetAsAdmin.setSelection(false);
						btnDeleteAccount.setSelection(false);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		btnSetAsUser.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnSetAsUser.getSelection() == true && chosenAccount != null) {
					try {
						Connection connection = ConnectionUtils.getMyConnection();
						String sql = "UPDATE users SET user_type = ?, confirmation = 1 WHERE username LIKE ?";
						PreparedStatement statement = connection.prepareStatement(sql);
						statement.setString(1, "user");
						statement.setString(2, chosenAccount);
						statement.execute();
						String activity = "Change account type";
						String content = "Set " + chosenAccount + " as user";
						String databaseImpact = "UPDATE users SET user_type = user, confirmation = 1 WHERE username LIKE " + chosenAccount + "\n\n";
						MultiParagraphsSummarizingView.insertToActivitiesDiary(activity, content, databaseImpact);
						MessageBox box = new MessageBox(getShell(), SWT.OK);
						box.setText("Message");
						box.setMessage("Set " + chosenAccount + " as a user !");
						box.open();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		btnSetAsUnconfirmed.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnSetAsUnconfirmed.getSelection() == true && chosenAccount != null) {
					try {
						Connection connection = ConnectionUtils.getMyConnection();
						String sql = "UPDATE users SET user_type = ?, confirmation = 0 WHERE username LIKE ?";
						PreparedStatement statement = connection.prepareStatement(sql);
						statement.setString(1, "user");
						statement.setString(2, chosenAccount);
						statement.execute();
						String activity = "Change account type";
						String content = "Set " + chosenAccount + " as unconfirmed user";
						String databaseImpact = "UPDATE users SET user_type = user, confirmation = 0 WHERE username LIKE " + chosenAccount + "\n\n";
						MultiParagraphsSummarizingView.insertToActivitiesDiary(activity, content, databaseImpact);
						MessageBox box = new MessageBox(getShell(), SWT.OK);
						box.setText("Message");
						box.setMessage("Set " + chosenAccount + " as an unconfirmed user !");
						box.open();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		btnSetAsAdmin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnSetAsAdmin.getSelection() == true && chosenAccount != null) {
					try {
						Connection connection = ConnectionUtils.getMyConnection();
						String sql = "UPDATE users SET user_type = ?, confirmation = 1 WHERE username LIKE ?";
						PreparedStatement statement = connection.prepareStatement(sql);
						statement.setString(1, "admin");
						statement.setString(2, chosenAccount);
						statement.execute();
						String activity = "Change account type";
						String content = "Set " + chosenAccount + " as admin";
						String databaseImpact = "UPDATE users SET user_type = admin, confirmation = 1 WHERE username LIKE " + chosenAccount + "\n\n";
						MultiParagraphsSummarizingView.insertToActivitiesDiary(activity, content, databaseImpact);
						MessageBox box = new MessageBox(getShell(), SWT.OK);
						box.setText("Message");
						box.setMessage("Set " + chosenAccount + " as an admin !");
						box.open();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		btnDeleteAccount.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnDeleteAccount.getSelection() == true && chosenAccount != null) {
					try {
						Connection connection = ConnectionUtils.getMyConnection();
						String sql = "DELETE FROM users WHERE username LIKE ?";
						PreparedStatement statement = connection.prepareStatement(sql);
						statement.setString(1, chosenAccount);
						statement.execute();
						combo_1.remove(chosenAccount);
						String activity = "Delete account";
						String content = "Delete account: " + chosenAccount;
						String databaseImpact = "DELETE FROM users WHERE username LIKE " + chosenAccount + "\n\n";
						MultiParagraphsSummarizingView.insertToActivitiesDiary(activity, content, databaseImpact);
						MessageBox box = new MessageBox(getShell(), SWT.OK);
						box.setText("Message");
						box.setMessage("Deleted this account !");
						box.open();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
	}

	// Get all admins to comboBox
	public void getAllAdmins(Combo combo) throws ClassNotFoundException, SQLException {
		combo.removeAll();
		Connection connection = ConnectionUtils.getMyConnection();
		String sql = "SELECT * FROM users WHERE user_type = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "admin");
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			combo.add(rs.getString("username"));
		}
	}
	
	// Get all users to comboBox
	public static void getAllUsers(Combo combo) throws ClassNotFoundException, SQLException {
		combo.removeAll();
		Connection connection = ConnectionUtils.getMyConnection();
		String sql = "SELECT * FROM users WHERE user_type = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "user");
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			combo.add(rs.getString("username"));
		}
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
