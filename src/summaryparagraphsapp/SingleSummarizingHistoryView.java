package summaryparagraphsapp;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * Author: Nguyen Viet Long
 * Display history view
 */
public class SingleSummarizingHistoryView extends Composite {
	private Text text;
	private Text text_1;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public SingleSummarizingHistoryView(Composite parent, int style) throws ClassNotFoundException, SQLException {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new GridLayout(3, true));
		
		Label lblHistory = new Label(composite, SWT.NONE);
		lblHistory.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblHistory.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblHistory.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblHistory.setAlignment(SWT.CENTER);
		GridData gd_lblHistory = new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1);
		gd_lblHistory.heightHint = 44;
		lblHistory.setLayoutData(gd_lblHistory);
		lblHistory.setText("History");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblChooseHistory = new Label(composite, SWT.NONE);
		lblChooseHistory.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblChooseHistory.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblChooseHistory.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblChooseHistory.setText("Choose history :");
		new Label(composite, SWT.NONE);
		
		Combo combo = new Combo(composite, SWT.NONE);
		GridData gd_combo = new GridData(SWT.CENTER, SWT.CENTER, true, true, 3, 1);
		gd_combo.heightHint = 119;
		gd_combo.widthHint = 321;
		combo.setLayoutData(gd_combo);
		
		// Add history URL to combo
		Connection connection = ConnectionUtils.getMyConnection();
		getHistoryToCombo(connection,combo);
		
		// Choose history to view
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String history = combo.getItem(combo.getSelectionIndex());
				try {
					// Show summary paragraph
					showSummary(connection,history);
					
					// Get webId corresponded to historyURL	
					String sql = "SELECT id FROM articles WHERE title LIKE ? AND user_id = ?";
					PreparedStatement getWebId = connection.prepareStatement(sql);
					getWebId.setString(1, history);
					getWebId.setInt(2, AppRun.userID);
					ResultSet rs = getWebId.executeQuery();
					int webId = 0;
					while (rs.next()) {
						webId = rs.getInt("id");			
					}
					
					// Show classify result
					showClassifyResult(connection, webId);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		text_1 = new Text(composite, SWT.BORDER);
		text_1.setEditable(false);
		GridData gd_text_1 = new GridData(SWT.CENTER, SWT.CENTER, true, false, 3, 1);
		gd_text_1.widthHint = 166;
		text_1.setLayoutData(gd_text_1);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	// Add history URL to combo
	public void getHistoryToCombo(Connection connection, Combo combo) throws SQLException {
		String sql = "SELECT DISTINCT title FROM articles WHERE user_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, AppRun.userID);
	      ResultSet rs = statement.executeQuery();
	      while (rs.next()) {
	    	  combo.add(rs.getString("title"));
	      }
	}
	
	// Show summary paragraph
	public void showSummary(Connection connection, String history) throws SQLException {
		String sql = "SELECT summary FROM articles WHERE title LIKE ? AND user_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, history);
		statement.setInt(2, AppRun.userID);
		ResultSet rs = statement.executeQuery();
	      while (rs.next()) {
	    	  text.setText(rs.getString("summary"));
	      }
	}
	
	// Show classify result
	public void showClassifyResult(Connection connection, int webId) throws SQLException, ClassNotFoundException {
		String sql = "SELECT type FROM articles WHERE id = ?";
		PreparedStatement getWebId = connection.prepareStatement(sql);
		getWebId.setInt(1, webId);
		ResultSet rs = getWebId.executeQuery();
		int typeReturn = 2;
		while (rs.next()) {
			typeReturn = rs.getInt("type");
		}
		if (typeReturn == -1) {
			text_1.setText("Negative");
		}
		else if (typeReturn == 0) {
			text_1.setText("Neutral");
		}
		else if (typeReturn == 1) {
			text_1.setText("Positive");
		}
		else if (typeReturn == 2) {
			text_1.setText("");
		}
	}
}
