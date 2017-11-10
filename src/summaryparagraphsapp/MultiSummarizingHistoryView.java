package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 * Multiple paragraphs summarizing - history view
 */

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.GestureListener;
import org.eclipse.swt.events.GestureEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;

public class MultiSummarizingHistoryView extends Composite {
	private Text text;
	private Text text_1;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public MultiSummarizingHistoryView(Composite parent, int style) throws ClassNotFoundException, SQLException {
		super(parent, style);
		this.update();
		setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_2 = new Composite(composite, SWT.BORDER);
		composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_2.setLayout(new GridLayout(4, false));
		
		Label lblMultisummarizingHistory = new Label(composite_2, SWT.NONE);
		lblMultisummarizingHistory.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMultisummarizingHistory.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblMultisummarizingHistory.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblMultisummarizingHistory.setAlignment(SWT.CENTER);
		lblMultisummarizingHistory.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 4, 1));
		lblMultisummarizingHistory.setText("Multi-summarizing History");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		
		Composite composite_3 = new Composite(composite, SWT.NONE);
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_1 = new Text(composite_3, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Connection connection = ConnectionUtils.getMyConnection();
		
		Label lblChooseHistory = new Label(composite_2, SWT.NONE);
		GridData gd_lblChooseHistory = new GridData(SWT.CENTER, SWT.CENTER, false, false, 4, 1);
		gd_lblChooseHistory.heightHint = 23;
		lblChooseHistory.setLayoutData(gd_lblChooseHistory);
		lblChooseHistory.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblChooseHistory.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblChooseHistory.setAlignment(SWT.CENTER);
		lblChooseHistory.setText("Choose history :");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		
		Combo combo = new Combo(composite_2, SWT.NONE);
		GridData gd_combo = new GridData(SWT.CENTER, SWT.CENTER, true, false, 4, 1);
		gd_combo.heightHint = 72;
		gd_combo.widthHint = 169;
		combo.setLayoutData(gd_combo);
		getHistoryToCombo(connection,combo);
		
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String dateTime = combo.getItem(combo.getSelectionIndex());
				try {
					showSummary(connection, dateTime);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	// Add history URL to combo
	public void getHistoryToCombo(Connection connection, Combo combo) throws SQLException {
		String sql = "SELECT datetime_use FROM multi_summarizing_history WHERE user_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, AppRun.userID);
	      ResultSet rs = statement.executeQuery();
	      while (rs.next()) {
	    	  combo.add(rs.getString("datetime_use"));
	      }
	}
		
	// Show summary paragraph
	public void showSummary(Connection connection, String dateTime) throws SQLException {
		String sql = "SELECT * FROM multi_summarizing_history WHERE datetime_use LIKE ? AND user_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, dateTime);
		statement.setInt(2, AppRun.userID);
		ResultSet rs = statement.executeQuery();
	      while (rs.next()) {
	    	  text.setText(rs.getString("summary_paragraph"));
	    	  text_1.setText(rs.getString("url_set"));
	      }
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
