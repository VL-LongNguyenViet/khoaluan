package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 * Super admin view
 */

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class SuperAdminView extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public SuperAdminView(Composite parent, int style) throws ClassNotFoundException, SQLException {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		
		TabItem tbtmManagingAccounts = new TabItem(tabFolder, SWT.NONE);
		tbtmManagingAccounts.setText("Managing accounts");
		
		SuperAdminManagingView superAdminManagingViewDefault = new SuperAdminManagingView(tabFolder, SWT.NONE);
		tbtmManagingAccounts.setControl(superAdminManagingViewDefault);
		
		TabItem tbtmManagingUsersActivities = new TabItem(tabFolder, SWT.NONE);
		tbtmManagingUsersActivities.setText("Managing users activities");
		
		ManagingUsersActivitiesView managingUsersActivitiesView = new ManagingUsersActivitiesView(tabFolder, SWT.NONE);
		tbtmManagingUsersActivities.setControl(managingUsersActivitiesView);
		
		TabItem tbtmGeneral = new TabItem(tabFolder, SWT.NONE);
		tbtmGeneral.setText("General functions");
		
		UserView userView = new UserView(tabFolder, SWT.NONE);
		tbtmGeneral.setControl(userView);

		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SuperAdminManagingView superAdminManagingView = null;
				try {
					superAdminManagingView = new SuperAdminManagingView(tabFolder, SWT.NONE);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tbtmManagingAccounts.setControl(superAdminManagingView);
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
