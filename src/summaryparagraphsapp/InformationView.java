package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 * Information view
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class InformationView extends Composite {
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_8;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public InformationView(Composite parent, int style) throws ClassNotFoundException, SQLException {
		super(parent, style);
		setLayout(new GridLayout(3, true));
		
		Label lblInformationView = new Label(this, SWT.NONE);
		lblInformationView.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblInformationView.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblInformationView.setAlignment(SWT.CENTER);
		GridData gd_lblInformationView = new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1);
		gd_lblInformationView.widthHint = 166;
		lblInformationView.setLayoutData(gd_lblInformationView);
		lblInformationView.setText("Information");
		
		Label lblUsername = new Label(this, SWT.NONE);
		lblUsername.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUsername.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblUsername.setText("Username :");
		
		text = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Label lblPassword = new Label(this, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassword.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblPassword.setText("Password :");
		
		text_1 = new Text(this, SWT.BORDER | SWT.PASSWORD);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Label lblName = new Label(this, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblName.setText("Name :");
		
		text_2 = new Text(this, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblNewLabel.setText("User type :");
		
		text_3 = new Text(this, SWT.BORDER);
		text_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		text_3.setEditable(false);
		text_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Label lblDateCreated = new Label(this, SWT.NONE);
		lblDateCreated.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDateCreated.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblDateCreated.setText("Date created :");
		
		text_4 = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		text_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		text_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Label lblDepartment = new Label(this, SWT.NONE);
		lblDepartment.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDepartment.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblDepartment.setText("Department :");
		
		text_5 = new Text(this, SWT.BORDER);
		text_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Label lblPosition = new Label(this, SWT.NONE);
		lblPosition.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPosition.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblPosition.setText("Position :");
		
		text_6 = new Text(this, SWT.BORDER);
		text_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Label lblAddress = new Label(this, SWT.NONE);
		lblAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAddress.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblAddress.setText("Address :");
		
		text_7 = new Text(this, SWT.BORDER);
		text_7.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Label lblNote = new Label(this, SWT.NONE);
		lblNote.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNote.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblNote.setText("Note :");
		
		text_8 = new Text(this, SWT.BORDER);
		text_8.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		getInformation();
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		Button btnSave = new Button(this, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					saveInformation(text_1.getText(), text_2.getText(), text_5.getText(), text_6.getText(), text_7.getText(), text_8.getText());
					String activity = "Change account information";
					String content = "New account information: \n\n"
							+ "Password: " + text_1.getText() + "\n\n"
							+ "Name: " + text_2.getText() + "\n\n"
							+ "Department: " + text_5.getText() + "\n\n"
							+ "Position: " + text_6.getText() + "\n\n"
							+ "Address: " + text_7.getText() + "\n\n"
							+ "Note: " + text_8.getText() + "\n\n";
					String databaseImpact = "UPDATE users SET password = " + text_1.getText()
					+ ", name = " + text_2.getText()
					+ ", department = " + text_5.getText()
					+ ", position = " + text_6.getText()
					+ ", address = " + text_7.getText()
					+ ", note = " + text_8.getText()
					+ " WHERE id = " + AppRun.userID;
					MultiParagraphsSummarizingView.insertToActivitiesDiary(activity, content, databaseImpact);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridData gd_btnSave = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnSave.widthHint = 77;
		btnSave.setLayoutData(gd_btnSave);
		btnSave.setText("Save");
		new Label(this, SWT.NONE);

	}
	
	// Get information from database
	public void getInformation() throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtils.getMyConnection();
		String sql = "SELECT * FROM users WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, AppRun.userID);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			text.setText(rs.getString("username"));
			text_1.setText(rs.getString("password"));
			text_2.setText(rs.getString("name"));
			text_3.setText(rs.getString("user_type"));
			text_4.setText(rs.getString("date_created"));
			text_5.setText(rs.getString("department"));
			text_6.setText(rs.getString("position"));
			text_7.setText(rs.getString("address"));
			text_8.setText(rs.getString("note"));
		}
	}
	
	// Save information to database
	public void saveInformation(String password, String name, String department, String position, String address, String note) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtils.getMyConnection();
		String sql = "UPDATE users SET password = ?, name = ?, department = ?, position = ?, address = ?, note = ? WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, password);
		statement.setString(2, name);
		statement.setString(3, department);
		statement.setString(4, position);
		statement.setString(5, address);
		statement.setString(6, note);
		statement.setInt(7, AppRun.userID);
		statement.execute();
		MessageBox box = new MessageBox(getShell(), SWT.OK);
		box.setText("Message");
		box.setMessage("Saved information !");
		box.open();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
