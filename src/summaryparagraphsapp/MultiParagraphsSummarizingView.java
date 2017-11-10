package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 * Multiple paragraphs summarizing view
 */

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.eclipse.swt.widgets.ProgressBar;

public class MultiParagraphsSummarizingView extends Composite {

	private Text text;
	private Text text_1;
	int numOfURLs = 0;
	private ProgressBar progressBar;
	ArrayList<String> URLs = new ArrayList<String>();

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public MultiParagraphsSummarizingView(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));		
		
		Composite composite_2 = new Composite(this, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblMultiParagraphsSummarizing = new Label(composite_2, SWT.NONE);
		lblMultiParagraphsSummarizing.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMultiParagraphsSummarizing.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblMultiParagraphsSummarizing.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblMultiParagraphsSummarizing.setAlignment(SWT.CENTER);
		lblMultiParagraphsSummarizing.setText("Multi paragraphs summarizing");
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		RowLayout rl_composite = new RowLayout(SWT.HORIZONTAL);
		rl_composite.center = true;
		rl_composite.fill = true;
		rl_composite.justify = true;
		composite.setLayout(rl_composite);
		
		Label lblUrl = new Label(composite, SWT.NONE);
		lblUrl.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblUrl.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblUrl.setText("URL :");
		
		text_1 = new Text(composite, SWT.BORDER);
		text_1.setLayoutData(new RowData(367, 27));
		
		Button btnAddMoreUrl = new Button(composite, SWT.NONE);
		btnAddMoreUrl.setLayoutData(new RowData(96, SWT.DEFAULT));
		btnAddMoreUrl.setText("Add more URL");
		
		Label lblNumberOfUrl = new Label(composite, SWT.WRAP | SWT.CENTER);
		lblNumberOfUrl.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNumberOfUrl.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblNumberOfUrl.setLayoutData(new RowData(155, 26));
		lblNumberOfUrl.setText("Number of URLs : 0");
		
		Button btnConfirrm = new Button(composite, SWT.NONE);
		btnConfirrm.setLayoutData(new RowData(97, SWT.DEFAULT));
		btnConfirrm.setText("Confirrm");
		
		Button btnSummarize = new Button(composite, SWT.NONE);
		btnSummarize.setLayoutData(new RowData(86, SWT.DEFAULT));
		btnSummarize.setText("Summarize");
		
		progressBar = new ProgressBar(composite, SWT.NONE);
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		btnAddMoreUrl.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_1.setText("");
			}
		});
		
		btnConfirrm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean addOrNot = true;
				if (URLs.isEmpty() == true) {
					URLs.add(text_1.getText());
					numOfURLs++;
					lblNumberOfUrl.setText("Number of URLs : " + numOfURLs);
				}
				else if (URLs.isEmpty() == false) {
					String newURL = text_1.getText();
					for (String URL : URLs) {
						if (newURL.equals(URL)) {
							addOrNot = false;
							break;
						}
					}
					if (addOrNot == true) {
						URLs.add(newURL);
						numOfURLs++;
						lblNumberOfUrl.setText("Number of URLs : " + numOfURLs);
					}
					else if (addOrNot == false) {
						MessageBox box = new MessageBox(getShell(), SWT.OK);
						box.setText("Message");
						box.setMessage("Already added URL !");
						box.open();
					}
				}
			}
		});
		
		btnSummarize.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Document doc;
				String paragraph = "";
				if (numOfURLs < 2) {
					// Show message box if user haven't enter URL
					MessageBox box = new MessageBox(getShell(), SWT.OK);
					box.setText("Message");
					box.setMessage("Please enter (min 2) valid URLs !");
					box.open();
					
				}
				
				else {
					try {
						for (String inputURL : URLs) {
							// Crawl data from website
							
				    	   
							// Get the instruction to crawl data from that URL
							Connection connection = ConnectionUtils.getMyConnection();
							int id_or_class = 0;
							String id_class_name = "";
							String sql = "SELECT * FROM url_process";
						      PreparedStatement statement = connection.prepareStatement(sql);
						      ResultSet rs = statement.executeQuery();
						      boolean notInDatabase = true;
						      while (rs.next()) {
						    	  if (inputURL.contains(rs.getString("page_name"))){
						    		  id_or_class = rs.getInt("content_id_or_class");
						    		  id_class_name = rs.getString("content_id_class_name");
						    		  notInDatabase = false;
						    	  }
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
						    	  doc = Jsoup.connect(inputURL).get();
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
						}
						String urlSet = "";
						for (String inputURL : URLs) {
							urlSet += "\n" + inputURL + "\n";
						}
						
						// test
						//System.out.println(paragraph);
						
						// Prepare UTF-8 file
						SingleSummarizingView.prepareUTF8File(paragraph);
						
						// Use vnTokenizer
						SingleSummarizingView.useVNTokenizer();
						
						 // Progress bar
				   		int progressBarValue = 0;
				   		progressBar.setSelection(progressBarValue);
				    	   while (progressBarValue < 2000) {
				               progressBar.setSelection(progressBarValue);
				               try {
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
				                progressBarValue += 333;
				           }
				    	   
				    	// Get all sentences (tokenized)
				    	   ArrayList<Sentence> sentences = new ArrayList<Sentence>();
				    	   SingleSummarizingView.getAllTokenizedSentences(sentences);
				    	   
				    	   
			            
			            // Get all words and set sentencePos for each word (tokenized)
			            SingleSummarizingView.getAllWords(sentences);
			            
			            // Count each word's appearance in each sentence
			            SingleSummarizingView.countEachWordAppearanceInEachSentence(sentences);
			           
			           // Calculate each word's importance in each sentence
			            SingleSummarizingView.calculateEachWordImportanceInEachSentence(sentences);
			           
			            
			           // Calculate each sentence's importance
			            SingleSummarizingView.calculateEachSentenceImportance(sentences);
			            
			            // Calculate similarities
			            ArrayList<Similarity> similaritiesArray = new ArrayList<Similarity>();
			            for (int pos1 = 0; pos1 < sentences.size() - 1; pos1++) {
			            	for (int pos2 = pos1 + 1; pos2 < sentences.size(); pos2++) {
			            		similaritiesArray.add(new Similarity(sentences.get(pos1), sentences.get(pos2)));
			            		similaritiesArray.add(new Similarity(sentences.get(pos2), sentences.get(pos1)));
			            	}
			            }					            
			       
			            for (Similarity similarity : similaritiesArray) {
			            	if (similarity.getSimilarityRate() <= 0.6) {
			            		System.out.println(similarity.getFirstSentence().getSentenceText() + " <<<<<>>>>> "
			            				+ similarity.getSecondSentence().getSentenceText() + " <<<<<>>>>>" 
			            				+ similarity.getSimilarityRate());
			            	}
			            }
			            System.out.println();
			            System.out.println();
			            System.out.println();
			            String summaryParagraph = summarizeParagraph(similaritiesArray,  sentences);
			            System.out.println();
			            System.out.println();
			            System.out.println();
			            System.out.println(summaryParagraph);
			            text.setText(summaryParagraph);
			            /*DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			            Date dateobj = new Date();
			            String dateTime = df.format(dateobj);*/
			            insertToMultiSummarizingHistory(urlSet,summaryParagraph);
			            String activity = "Multiple summarizing";
			            String content = "URLs set: " + urlSet + "\n\n"
			            		+ "Summarizing result: " + summaryParagraph + "\n\n";
			            String databaseImpact = "INSERT INTO multi_summarizing_history (user_id, `url_set`, `summary_paragraph`)"
			            		+ " VALUES (" + AppRun.userID + ", " + urlSet + ", " + summaryParagraph + ")" + "\n\n";
			            insertToActivitiesDiary(activity, content, databaseImpact);
					}
						      catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (ClassNotFoundException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								} catch (SQLException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
					}
					
				}				
		});
	}

	// Summarize paragraph
	public static String summarizeParagraph(ArrayList<Similarity> similarities, ArrayList<Sentence> sentences) {
		ArrayList<Sentence> tempSentences = new ArrayList<Sentence>();
    	  for (int countSentence = 0; countSentence < sentences.size(); countSentence++) {
    		  tempSentences.add(new Sentence(sentences.get(countSentence)));
    	  }
    	  // Sort sentences according to sentenceImportance
    	  for (int pos1 = 0; pos1 < tempSentences.size() - 1; pos1++) {
    		  for (int pos2 = pos1 + 1; pos2 < tempSentences.size(); pos2++) {
    			  if (tempSentences.get(pos1).getSentenceImportance() < 
    				  tempSentences.get(pos2).getSentenceImportance()) {
    				  SingleSummarizingView.swap(tempSentences.get(pos1), tempSentences.get(pos2));	                				  
    			  }
    		  }
    	  }
    	  
    	  String result = "";
    	  // Get result
    	  int pos1 = 0;
    	  while (pos1 < tempSentences.size() - 1) {
    		  int pos2 = pos1 + 1;
    		  boolean changed = false;
    		  while (pos2 < tempSentences.size() && changed == false) {
    			  for (Similarity similarity : similarities) {
    				  if (similarity.getFirstSentence().getSentenceText().equals(tempSentences.get(pos1).getSentenceText()) && similarity.getSecondSentence().getSentenceText().equals(tempSentences.get(pos2).getSentenceText())) {
    					  double similarityRate = similarity.getSimilarityRate();
    					  if (similarityRate <= 0.6) {
    						  result += "\n" + tempSentences.get(pos1).getSentenceText() + "\n" + tempSentences.get(pos2).getSentenceText();
    						  tempSentences.remove(pos1);
    						  
    						  // "pos2 - 1" because of the above "remove(pos1)" sentence
    	    				  tempSentences.remove(pos2 - 1);
    	    				  pos1--;
    	    				  changed = true;
;    					  }
    					  break;
    				  }
    			  }
    			  pos2++;
    		  }
    		  pos1++;
    	  }
		return result.replace("_", " ");
	}
	
	// Insert to table multi_summarizing_history in database
	public void insertToMultiSummarizingHistory(String urlSet, String summaryParagraph) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtils.getMyConnection();
		String sql = "INSERT INTO multi_summarizing_history (user_id, `url_set`, `summary_paragraph`) VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, AppRun.userID);
        statement.setString(2, urlSet);
        statement.setString(3, summaryParagraph);
        statement.execute();
	}
	
	// Insert into table activities_diary in database
	public static void insertToActivitiesDiary(String activity, String content, String databaseImpact) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtils.getMyConnection();
		String sql = "INSERT INTO activities_diary (user_id, `activity`, `content`, database_impact) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, AppRun.userID);
        statement.setString(2, activity);
        statement.setString(3, content);
        statement.setString(4, databaseImpact);
        statement.execute();
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
