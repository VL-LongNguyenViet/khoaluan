package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 * User view
 */

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;

public class UserView extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public UserView(Composite parent, int style) throws ClassNotFoundException, SQLException {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		
		TabItem tbtmInformation = new TabItem(tabFolder, SWT.NONE);
		tbtmInformation.setText("Information");
		
		InformationView informationViewDefault = new InformationView(tabFolder, SWT.NONE);
		tbtmInformation.setControl(informationViewDefault);
		
		TabItem tbtmNewItem_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem_1.setText("Hot articles");
		
		TabItem tbtmSingleSummarizing = new TabItem(tabFolder, SWT.NONE);
		tbtmSingleSummarizing.setText("Single summarizing");
		
		TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("Single summarizing history");
		
		TabItem tbtmMultipleSummarizing = new TabItem(tabFolder, SWT.NONE);
		tbtmMultipleSummarizing.setText("Multiple summarizing");
		
		TabItem tbtmMultipleSummarizingHistory = new TabItem(tabFolder, SWT.NONE);
		tbtmMultipleSummarizingHistory.setText("Multiple summarizing history");
		
		TabItem tbtmNewItem_2 = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem_2.setText("Statistics charts");

		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TabItem chosenTab = tabFolder.getSelection()[0];
				if (chosenTab.getText().equals("Information")) {
					try {
						informationViewDefault.dispose();
						InformationView informationView = new InformationView(tabFolder, SWT.NONE);
						tbtmInformation.setControl(informationView);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if (chosenTab.getText().equals("Hot articles")) {
					try {
						HotArticlesView hotArticlesView = new HotArticlesView(tabFolder, SWT.NONE);
						tbtmNewItem_1.setControl(hotArticlesView);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if (chosenTab.getText().equals("Single summarizing")) {
					SingleSummarizingView singleSummarizingView = new SingleSummarizingView(tabFolder, SWT.NONE);
					tbtmSingleSummarizing.setControl(singleSummarizingView);
				}
				else if (chosenTab.getText().equals("Single summarizing history")) {
					SingleSummarizingHistoryView singleSummarizingHistoryView = null;
					try {
						singleSummarizingHistoryView = new SingleSummarizingHistoryView(tabFolder, SWT.NONE);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tbtmNewItem.setControl(singleSummarizingHistoryView);
				}
				else if (chosenTab.getText().equals("Multiple summarizing")) {
					MultiParagraphsSummarizingView multiParagraphsSummarizingView = new MultiParagraphsSummarizingView(tabFolder, SWT.NONE);
					tbtmMultipleSummarizing.setControl(multiParagraphsSummarizingView);
				}
				else if (chosenTab.getText().equals("Multiple summarizing history")) {
					MultiSummarizingHistoryView multiSummarizingHistoryView = null;
					try {
						multiSummarizingHistoryView = new MultiSummarizingHistoryView(tabFolder, SWT.NONE);
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tbtmMultipleSummarizingHistory.setControl(multiSummarizingHistoryView);
				}
				else if (chosenTab.getText().equals("Statistics charts")) {
					ChartView chartView = new ChartView(tabFolder, SWT.NONE);
					tbtmNewItem_2.setControl(chartView);
				}
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
