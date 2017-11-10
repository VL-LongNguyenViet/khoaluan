package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 * Admin managing view view
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AdminManagingView extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	
	private String chosenUser;
	
	public AdminManagingView(Composite parent, int style) throws ClassNotFoundException, SQLException {
		super(parent, style);
		setLayout(new GridLayout(3, true));
		
		Label lblManagingUsers = new Label(this, SWT.NONE);
		lblManagingUsers.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1));
		lblManagingUsers.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblManagingUsers.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblManagingUsers.setText("Managing users");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblUsers = new Label(this, SWT.NONE);
		lblUsers.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblUsers.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblUsers.setText("Users :");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Combo combo = new Combo(this, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
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
		
		Button btnSetAsUser = new Button(this, SWT.RADIO);
		btnSetAsUser.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnSetAsUser.getSelection() == true && chosenUser != null) {
					try {
						Connection connection = ConnectionUtils.getMyConnection();
						String sql = "UPDATE users SET confirmation = 1 WHERE username LIKE ?";
						PreparedStatement statement = connection.prepareStatement(sql);
						statement.setString(1, chosenUser);
						statement.execute();
						String activity = "Change account type";
						String content = "Set " + chosenUser + " as user";
						String databaseImpact = "UPDATE users SET confirmation = 1 WHERE username LIKE " + chosenUser + "\n\n";
						MultiParagraphsSummarizingView.insertToActivitiesDiary(activity, content, databaseImpact);
						MessageBox box = new MessageBox(getShell(), SWT.OK);
						box.setText("Message");
						box.setMessage("Set " + chosenUser + " as a user !");
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
		btnSetAsUser.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btnSetAsUser.setText("Set as user");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Button btnSetAsUnconfirm = new Button(this, SWT.RADIO);
		btnSetAsUnconfirm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnSetAsUnconfirm.getSelection() == true && chosenUser != null) {
					try {
						Connection connection = ConnectionUtils.getMyConnection();
						String sql = "UPDATE users SET confirmation = 0 WHERE username LIKE ?";
						PreparedStatement statement = connection.prepareStatement(sql);
						statement.setString(1, chosenUser);
						statement.execute();
						String activity = "Change account type";
						String content = "Set " + chosenUser + " as unconfirmed user";
						String databaseImpact = "UPDATE users SET confirmation = 0 WHERE username LIKE " + chosenUser + "\n\n";
						MultiParagraphsSummarizingView.insertToActivitiesDiary(activity, content, databaseImpact);
						MessageBox box = new MessageBox(getShell(), SWT.OK);
						box.setText("Message");
						box.setMessage("Set " + chosenUser + " as an unconfirmed user !");
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
		btnSetAsUnconfirm.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btnSetAsUnconfirm.setText("Set as unconfirmed user");
		new Label(this, SWT.NONE);

		// Get all users to combobox
		SuperAdminManagingView.getAllUsers(combo);
		
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				chosenUser = combo.getItem(combo.getSelectionIndex());
				try {
					getUserConfirmation(chosenUser, btnSetAsUser, btnSetAsUnconfirm);
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

	// Get information about the chosen user's confirmation
	public static void getUserConfirmation(String chosenUser, Button btnSetAsUser, Button btnSetAsUnconfirm) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtils.getMyConnection();
		String sql = "SELECT * FROM users WHERE username LIKE ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, chosenUser);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			if (rs.getInt("confirmation") == 0) {
				btnSetAsUnconfirm.setSelection(true);
				btnSetAsUser.setSelection(false);
			}
			else {
				btnSetAsUser.setSelection(true);
				btnSetAsUnconfirm.setSelection(false);
			}
		}
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
