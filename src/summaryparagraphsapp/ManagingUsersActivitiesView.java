package summaryparagraphsapp;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ManagingUsersActivitiesView extends Composite {
	private Text text;
	private int selectedUserID;
	private Text text_1;
	private Text text_2;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public ManagingUsersActivitiesView(Composite parent, int style) throws ClassNotFoundException, SQLException {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite = new Composite(this, SWT.BORDER);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new GridLayout(2, true));
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		lblNewLabel.setText("Managing users' activities");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblChooseUser = new Label(composite, SWT.NONE);
		lblChooseUser.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblChooseUser.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblChooseUser.setAlignment(SWT.CENTER);
		lblChooseUser.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblChooseUser.setText("Choose user :");
		
		Combo combo = new Combo(composite, SWT.NONE);
		GridData gd_combo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_combo.widthHint = 155;
		combo.setLayoutData(gd_combo);
		
		// Get all accounts to combo
		getAllAccounts(combo);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblChooseTime = new Label(composite, SWT.NONE);
		lblChooseTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblChooseTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblChooseTime.setAlignment(SWT.CENTER);
		lblChooseTime.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblChooseTime.setText("Choose time :");
		
		Combo combo_1 = new Combo(composite, SWT.NONE);
		GridData gd_combo_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_combo_1.widthHint = 155;
		combo_1.setLayoutData(gd_combo_1);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblAction = new Label(composite, SWT.NONE);
		lblAction.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblAction.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAction.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblAction.setText("Action :");
		
		text = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text.widthHint = 200;
		text.setLayoutData(gd_text);
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_2 = new Composite(composite_1, SWT.BORDER);
		composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_1 = new Text(composite_2, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.MULTI);
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Composite composite_3 = new Composite(composite_1, SWT.BORDER);
		composite_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_2 = new Text(composite_3, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.MULTI);
		text_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedUsername = combo.getItem(combo.getSelectionIndex());
				try {
					getSelectedUserID(selectedUsername);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					getAllDatetimeUseFunctions(combo_1);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedDatetime = combo_1.getItem(combo_1.getSelectionIndex());
				try {
					showDiary(selectedDatetime);
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

	// Get all accounts to combobox
	public void getAllAccounts(Combo combo) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtils.getMyConnection();
		String sql = "SELECT * FROM users";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			combo.add(rs.getString("username"));
		}
	}
	
	// Get selected user_id
	public void getSelectedUserID (String username) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtils.getMyConnection();
		String sql = "SELECT id FROM users WHERE username LIKE ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, username);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			selectedUserID = rs.getInt("id");
		}
	}
	
	// Get all datetime selected user used functions
	public void getAllDatetimeUseFunctions (Combo combo_1) throws ClassNotFoundException, SQLException {
		combo_1.removeAll();
		Connection connection = ConnectionUtils.getMyConnection();
		String sql = "SELECT datetime FROM activities_diary WHERE user_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, selectedUserID);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			combo_1.add(rs.getString("datetime"));
		}
	}
	
	// Show diary result
	public void showDiary (String selectedDatetime) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtils.getMyConnection();
		String sql = "SELECT * FROM activities_diary WHERE user_id = ? AND datetime LIKE ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, selectedUserID);
		statement.setString(2, selectedDatetime);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			text.setText(rs.getString("activity"));
			text_1.setText(rs.getString("content"));
			text_2.setText(rs.getString("database_impact"));
		}
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
