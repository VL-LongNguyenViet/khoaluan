package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 * Admin view
 */

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AdminView extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public AdminView(Composite parent, int style) throws ClassNotFoundException, SQLException {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		
		TabItem tbtmManagingUsers = new TabItem(tabFolder, SWT.NONE);
		tbtmManagingUsers.setText("Managing users");
		
		AdminManagingView adminManagingViewDefault = new AdminManagingView(tabFolder, SWT.NONE);
		tbtmManagingUsers.setControl(adminManagingViewDefault);
		
		TabItem tbtmGeneralFunctions = new TabItem(tabFolder, SWT.NONE);
		tbtmGeneralFunctions.setText("General functions");
		
		UserView userView = new UserView(tabFolder, SWT.NONE);
		tbtmGeneralFunctions.setControl(userView);
		
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TabItem chosenTab = tabFolder.getSelection()[0];
				if (chosenTab.getText().equals("Managing users")) {
					AdminManagingView adminManagingView = null;
					try {
						adminManagingView = new AdminManagingView(tabFolder, SWT.NONE);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tbtmManagingUsers.setControl(adminManagingView);
				}
			}
		});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
