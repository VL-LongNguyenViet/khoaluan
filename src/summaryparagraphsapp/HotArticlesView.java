package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 * Hot articles view
 */

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;

public class HotArticlesView extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private ArrayList<CrawledWebsite> listHotArticles = new ArrayList<CrawledWebsite>();
	private Text text;
	
	public HotArticlesView(Composite parent, int style) throws ClassNotFoundException, SQLException {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite = new Composite(this, SWT.BORDER);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new GridLayout(3, true));
		
		Label lblHotArticles = new Label(composite, SWT.NONE);
		lblHotArticles.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblHotArticles.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblHotArticles.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblHotArticles.setAlignment(SWT.CENTER);
		GridData gd_lblHotArticles = new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1);
		gd_lblHotArticles.heightHint = 29;
		lblHotArticles.setLayoutData(gd_lblHotArticles);
		lblHotArticles.setText("Hot articles");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblHosts = new Label(composite, SWT.NONE);
		lblHosts.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblHosts.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblHosts.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblHosts.setAlignment(SWT.CENTER);
		lblHosts.setText("Hosts :");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Combo combo = new Combo(composite, SWT.NONE);
		
		GridData gd_combo = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_combo.widthHint = 231;
		combo.setLayoutData(gd_combo);
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblHotArticles_1 = new Label(composite, SWT.NONE);
		lblHotArticles_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblHotArticles_1.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblHotArticles_1.setAlignment(SWT.CENTER);
		lblHotArticles_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblHotArticles_1.setText("Hot articles :");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Composite composite_1 = new Composite(this, SWT.BORDER);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Connection connection = ConnectionUtils.getMyConnection();
		new Label(composite, SWT.NONE);
		
		// Get all hosts to combobox
		getAllHost(connection, combo);
		
		Combo combo_1 = new Combo(composite, SWT.NONE);
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String chosenHost = combo.getItem(combo.getSelectionIndex());
				try {
					getHotArticles(chosenHost, combo_1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String chosenArticles = combo_1.getItem(combo_1.getSelectionIndex());
				String inputURL = "";
				Document doc = null;
				for (CrawledWebsite each : listHotArticles) {
					if (each.getTitle().equals(chosenArticles)) {
						inputURL = each.getUrl();
					}
				}
				String paragraph = "";
		    	   
				// Get the instruction to crawl data from that URL
				int id_or_class = 0;
				String id_class_name = "";
				String sql = "SELECT * FROM url_process";
			      PreparedStatement statement = null;
				try {
					statement = connection.prepareStatement(sql);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			      ResultSet rs = null;
				try {
					rs = statement.executeQuery();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			      boolean notInDatabase = true;
			      String host = "";
			      try {
					while (rs.next()) {
						  if (inputURL.contains(rs.getString("page_name"))){
							  id_or_class = rs.getInt("content_id_or_class");
							  id_class_name = rs.getString("content_id_class_name");
							  host = rs.getString("page_name");
							  notInDatabase = false;
						  }
					  }
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			      if (notInDatabase == true) {
			    	  // Show message box if app haven't support that website
			    	  MessageBox box = new MessageBox(getShell(), SWT.OK);
						box.setText("Message");
						box.setMessage("So sorry, our app haven't supported that Website yet !"
								+ "\nTry another Website please !");
						box.open();
			      }
			      else {
			    	  try {
						doc = Jsoup.connect(inputURL).get();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			      // The <div> contains the content can only get by ID
					if (id_or_class == 0) {
						Element content = doc.getElementById(id_class_name);
						Elements data = content.getElementsByTag("p");
						for (Element eachData : data) {
				    		   paragraph += eachData.text() + "\n";
				    	   }
					}
					else { // The <div> contains the content can only get by class
						Elements contents = doc.getElementsByClass(id_class_name);
						for (Element element : contents) {
							Elements data = element.getElementsByTag("p");
					    	   for (Element eachData : data) {
					    		   paragraph += eachData.text() + "\n";
					    	   }
						}
					}
			      }
			      text.setText(paragraph);
			}
		});
		new Label(composite, SWT.NONE);
	}

	// Get all host
	public void getAllHost (Connection connection, Combo combo) throws SQLException {
		String sql = "SELECT DISTINCT page_name FROM url_process";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();
	      while (rs.next()) {
	    	  combo.add(rs.getString("page_name"));
	      }
	}
	
	// Get hot articles
	public void getHotArticles (String chosenHost, Combo combo_1) throws IOException {
		Document doc = null;
		combo_1.removeAll();
		if (chosenHost.equals("dantri.com")) {
			doc = Jsoup.connect("http://dantri.com.vn").get();
			Elements allArticlesTag = doc.getElementsByAttributeValue("data-linktype", "newsdetail");
			for (Element eachArticle : allArticlesTag) {
				combo_1.add(eachArticle.text());
				listHotArticles.add(new CrawledWebsite("http://dantri.com.vn" + eachArticle.attr("href"), eachArticle.attr("title")));
			}
		}
		else if (chosenHost.equals("vnexpress.net")) {
			doc = Jsoup.connect("https://vnexpress.net/").get();
			Elements allAHrefTag = doc.getElementsByClass("title_news");
			for (Element each : allAHrefTag) {
				Elements atag = each.getElementsByTag("a");
				for (Element each1 : atag) {
					if (!each1.attr("title").isEmpty()) {
						combo_1.add(each1.attr("title"));
						listHotArticles.add(new CrawledWebsite(each1.attr("href"), each1.attr("title")));
					}
				}
			}
		}
		else {
			MessageBox box = new MessageBox(getShell(), SWT.OK);
			box.setText("Message");
			box.setMessage("Not support that host yet !");
			box.open();
		}
	}

	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
