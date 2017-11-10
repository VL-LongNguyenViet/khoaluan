package summaryparagraphsapp;

/**
 * Author: Nguyen Viet Long
 * Single summarizing view
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SingleSummarizingView extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	private  Text text;
	private ProgressBar progressBar;
	private Composite composite_1;
	private Text text_1;
	private Label lblNewLabel;
	private Text text_2;
	private Composite composite_2;
	private Composite composite_3;
	private Composite composite_4;
	private Text text_3;
	private Text text_4;
	private Label lblUrl;
	
	public SingleSummarizingView(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new GridLayout(2, false));
		
		lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblNewLabel.setAlignment(SWT.CENTER);
		GridData gd_lblNewLabel = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_lblNewLabel.heightHint = 45;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("Single paragraph summarizing");
		
		lblUrl = new Label(composite, SWT.NONE);
		lblUrl.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		lblUrl.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblUrl.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblUrl.setText("URL :");
		
		text = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		GridData gd_text = new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1);
		gd_text.widthHint = 359;
		text.setLayoutData(gd_text);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_btnNewButton.widthHint = 81;
		gd_btnNewButton.heightHint = 26;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("Summarize");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getShell().update();
				String inputURL = text.getText();
				Document doc;
				if (inputURL == "") {
					// Show message box if user haven't enter URL
					MessageBox box = new MessageBox(getShell(), SWT.OK);
					box.setText("Message");
					box.setMessage("Please enter a valid URL !");
					box.open();
					
				}
				
				else {
					try {
						// Crawl data from website
						String paragraph = "";
			    	   
						// Get the instruction to crawl data from that URL
						Connection connection = ConnectionUtils.getMyConnection();
						int id_or_class = 0;
						String id_class_name = "";
						String sql = "SELECT * FROM url_process";
					      PreparedStatement statement = connection.prepareStatement(sql);
					      ResultSet rs = statement.executeQuery();
					      boolean notInDatabase = true;
					      String host = "";
					      while (rs.next()) {
					    	  if (inputURL.contains(rs.getString("page_name"))){
					    		  id_or_class = rs.getInt("content_id_or_class");
					    		  id_class_name = rs.getString("content_id_class_name");
					    		  host = rs.getString("page_name");
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
							
							
							// Prepare UTF-8 file to tokenize
					    	   prepareUTF8File(paragraph);
					    	   
					    	   // Use vnTokenizer
					    	   useVNTokenizer();
					    	   
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
					    	   /*try {
								Thread.sleep(3333);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}*/
					    	   
					    	   // Get all sentences (tokenized)
					    	   ArrayList<Sentence> sentences = new ArrayList<Sentence>();
					    	   getAllTokenizedSentences(sentences);
					    	   
			                   
			                   // Get all words and set sentencePos for each word (tokenized)
			                   getAllWords(sentences);
			                   
			                   // Count each word's appearance in each sentence
			                   countEachWordAppearanceInEachSentence(sentences);
			                   
			                  // Calculate each word's importance in each sentence
			                  calculateEachWordImportanceInEachSentence(sentences);
			                  
			                  // Calculate each sentence's importance
			                  calculateEachSentenceImportance(sentences);
			                                                  	
			                  // Display summary paragraph (50% total senetences)
			                  String summaryParagraph = summarizeParagraph(paragraph,sentences);
			                  text_1.setText(summaryParagraph);
			                  
			                  String googleSearchString = "";
			                  CrawledWebsite website = new CrawledWebsite(host,inputURL,summaryParagraph);
			                  for (String keyword : website.getKeywords()) {
			                	  System.out.print(keyword + " ");
			                	  googleSearchString += keyword + " ";
			                  }
			                  System.out.println("");
			                  googleSearchString = googleSearchString.replace(' ', '+');
			                  System.out.println(googleSearchString);
			                  System.out.println("");
			                  
			                                    
			                  // Get webId corresponded to inputURL
			                  int webId = getWebIdCorrespondedToInputURL(connection, inputURL);
			                  
			                  // Classify summarized paragraph
			                  classifySummarizedParagraph(connection, webId);
			               // Test
			                  for (Sentence sentence : sentences) {
			               	   System.out.println(sentence.getSentenceText() + " " + sentence.getSentencePos()
			               	   + " " + sentence.getSentenceImportance());
			               	   //sentence.showWordsInSentence();
			                  }  
			                  System.out.println("");
			                  ArrayList<String> relativeURLs = searchGoogleForRelativeURLs(googleSearchString);
			                  for (String eachURL : relativeURLs) {
			      		    	System.out.println(eachURL);
			      		    	}
			                  System.out.println("");
			                  String relativeParagraph = "";
			                  for (String relativeURL : relativeURLs) {
			                	  
									// Get the instruction to crawl data from that URL
			                	  	id_or_class = 0;
									id_class_name = "";
									sql = "SELECT * FROM url_process";
								      statement = connection.prepareStatement(sql);
								      rs = statement.executeQuery();
								      notInDatabase = true;
								      while (rs.next()) {
								    	  if (relativeURL.contains(rs.getString("page_name"))){
								    		  id_or_class = rs.getInt("content_id_or_class");
								    		  id_class_name = rs.getString("content_id_class_name");
								    		  notInDatabase = false;
								    	  }
								      }
								      if (notInDatabase == false) {
								    	  doc = Jsoup.connect(relativeURL).get();
								      // The <div> contains the content can only get by ID
										if (id_or_class == 0) {
											Element content = doc.getElementById(id_class_name);
											
											Elements data = content.getElementsByTag("p");
											if (data.isEmpty() == false) {
												for (Element eachData : data) {
										    		   relativeParagraph += eachData.text() + "\n";
										    	   }
											}
										}
										else { // The <div> contains the content can only get by class
											Elements contents = doc.getElementsByClass(id_class_name);
											for (Element element : contents) {
												Elements data = element.getElementsByTag("p");
												if (data.isEmpty() == false) {
										    	   for (Element eachData : data) {
										    		   relativeParagraph += eachData.text() + "\n";
										    	   }
												}
											}
										}
								      }
								}
								
								// Prepare UTF-8 file
			                  	prepareUTF8File(relativeParagraph);
								
								// Use vnTokenizer
								useVNTokenizer();
								
								 // Progress bar
						   		progressBarValue = 0;
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
						    	   ArrayList<Sentence> relativeSentences = new ArrayList<Sentence>();
						    	   getAllTokenizedSentences(relativeSentences);
						    	   
						    	   
					            
					            // Get all words and set sentencePos for each word (tokenized)
					            getAllWords(relativeSentences);
					            
					            // Count each word's appearance in each sentence
					            countEachWordAppearanceInEachSentence(relativeSentences);
					           
					           // Calculate each word's importance in each sentence
					            calculateEachWordImportanceInEachSentence(relativeSentences);
					           
					            
					           // Calculate each sentence's importance
					            calculateEachSentenceImportance(relativeSentences);
					            
					            // Calculate similarities
					            ArrayList<Similarity> similaritiesArray = new ArrayList<Similarity>();
					            for (int pos1 = 0; pos1 < relativeSentences.size() - 1; pos1++) {
					            	for (int pos2 = pos1 + 1; pos2 < relativeSentences.size(); pos2++) {
					            		similaritiesArray.add(new Similarity(relativeSentences.get(pos1), relativeSentences.get(pos2)));
					            		similaritiesArray.add(new Similarity(relativeSentences.get(pos2), relativeSentences.get(pos1)));
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
					            String summaryRelativeParagraph = MultiParagraphsSummarizingView.summarizeParagraph(similaritiesArray,  relativeSentences);
					            System.out.println();
					            System.out.println();
					            System.out.println();
					            System.out.println(summaryRelativeParagraph);
					            text_4.setText(summaryRelativeParagraph);
					      }
				    	   
						
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
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} 
			       
			});
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		text_2 = new Text(composite, SWT.BORDER);
		text_2.setEditable(false);
		GridData gd_text_2 = new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1);
		gd_text_2.widthHint = 134;
		text_2.setLayoutData(gd_text_2);
		
		progressBar = new ProgressBar(composite, SWT.NONE);
		GridData gd_progressBar = new GridData(SWT.CENTER, SWT.CENTER, false, true, 2, 1);
		gd_progressBar.widthHint = 147;
		gd_progressBar.heightHint = 19;
		progressBar.setLayoutData(gd_progressBar);
		progressBar.setMinimum(0);
		progressBar.setMaximum(2000);
		
		composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_1 = new Text(composite_1, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		composite_2 = new Composite(composite_1, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.VERTICAL));
		
		composite_3 = new Composite(composite_2, SWT.NONE);
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_3 = new Text(composite_3, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		composite_4 = new Composite(composite_2, SWT.NONE);
		composite_4.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_4 = new Text(composite_4, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
	}

	// Prepare UTF-8 file to tokenize
		public static void prepareUTF8File(String paragraph) throws IOException{
			File inputFileDir = new File("D:\\EclipseWorkplace\\SummaryParagraphsApp\\input.txt");
	 	   Writer writeInputFile = new BufferedWriter(new OutputStreamWriter(
	 	   new FileOutputStream(inputFileDir), "UTF8")); 
	 	   writeInputFile.append(paragraph).append("\r\n");
	 	   writeInputFile.close();
		}
		
		// Use vnTokenizer
		public static void useVNTokenizer() throws IOException {
			String runVNTokenizer = "cmd /c D:\\EclipseWorkplace\\SummaryParagraphsApp\\vnTokenizer.bat" +
			    	   " vnTokenizer -i input.txt -o output.txt -sd" ;
			    	   Runtime.getRuntime().exec(runVNTokenizer);
		}
		
		// Get all sentences (tokenized)
		public static void getAllTokenizedSentences(ArrayList<Sentence> sentences) throws IOException {
			File outputFileDir = new File("D:\\EclipseWorkplace\\SummaryParagraphsApp\\output.txt");
	 	   BufferedReader readInputFile = new BufferedReader(
			   		   new InputStreamReader(
			                         new FileInputStream(outputFileDir), "UTF8"));
				String readData;
				int sentencePos = 0;
		   		while ((readData = readInputFile.readLine()) != null) {
		   		    sentences.add(new Sentence(readData,sentencePos));
		   		    sentencePos++;
		   		}
	        readInputFile.close();
		}
		
		// Get all words and set sentencePos for each word (tokenized)
		public static void getAllWords(ArrayList<Sentence> sentences) {
			int sentencePos = 0;
	        for (Sentence sentence : sentences) {
	     	   for (String eachWord : sentence.getSentenceText().split(" ")) {
	     		   sentence.addWordInSentence(new Word(eachWord,sentencePos));
	     	   }
	     	   sentencePos++;
	        }
		}
		
		// Count each word's appearance in each sentence
		public static void countEachWordAppearanceInEachSentence(ArrayList<Sentence> sentences) {
			int count = 0;
	        for (Sentence sentence : sentences) {
	     	   for (Word word : sentence.getWordsInSentence()) {
	     		   for (Sentence sentence1 : sentences) {
	     			   for (Word word1 : sentence1.getWordsInSentence()) {
	     				   if (word1.getWordText().equals(word.getWordText())) {
	     					   count++;
	     				   }
	     			   }
	     			   word.setAppearInEachSentence(count);
	     			   count = 0;
	     		   }
	     	   }
	        }
		}
		
		// Calculate each word's importance in each sentence
		public static void calculateEachWordImportanceInEachSentence(ArrayList<Sentence> sentences) {
			double denominator = 0.0;
	        for (Sentence sentence : sentences) {
	      	  for (Word word : sentence.getWordsInSentence()) {
	      		  for (int appearInEachSentence : word.getAppearInEachSentence()) {
	      			  denominator += Math.pow(appearInEachSentence, 2);
	      		  }
	      		  denominator = Math.sqrt(denominator);
	      		  for (int appearInEachSentence : word.getAppearInEachSentence()) {
	      			  double importance =  appearInEachSentence / denominator;
	      			  word.setWordImportanceInEachSentence(importance);
	      		  }
	      		  denominator = 0.0;
	      	  }
	        }
		}
		
		// Calculate each sentence's importance
		public static void calculateEachSentenceImportance(ArrayList<Sentence> sentences) {
			double sentenceImportance = 0.0;
	        for (Sentence sentence : sentences) {
	      	  for (Word word : sentence.getWordsInSentence()) {
	      		  sentenceImportance += word.getWordImportanceInEachSentence()
	      				  .get(sentence.getSentencePos());
	      	  }
	      	  sentence.setSentenceImportance(sentenceImportance);
	      	  sentenceImportance = 0.0;
	        }
		}
		
		// Summarize paragraph (50% total sentences)
		public String summarizeParagraph(String paragraph, ArrayList<Sentence> sentences) {
			if (sentences.size() < 5) {
	      	  return paragraph;
	        }
	        else {
	      	  ArrayList<Sentence> tempSentences = new ArrayList<Sentence>();
	      	  for (int countSentence = 0; countSentence < sentences.size(); countSentence++) {
	      		  tempSentences.add(new Sentence(sentences.get(countSentence)));
	      	  }
	      	  // Sort sentences according to sentenceImportance
	      	  for (int pos1 = 0; pos1 < tempSentences.size() - 1; pos1++) {
	      		  for (int pos2 = pos1 + 1; pos2 < tempSentences.size(); pos2++) {
	      			  if (tempSentences.get(pos1).getSentenceImportance() < 
	      				  tempSentences.get(pos2).getSentenceImportance()) {
	      				  swap(tempSentences.get(pos1), tempSentences.get(pos2));	                				  
	      			  }
	      		  }
	      	  }
	      	  // Sort most importance sentences according to sentencePos (50% total sentences)
	      	  for (int pos1 = 0; pos1 <= (tempSentences.size() / 2) - 1; pos1++) {
	      		  for (int pos2 = pos1 + 1; pos2 <= tempSentences.size() / 2; pos2++) {
	      			  if (tempSentences.get(pos1).getSentencePos() >
	      					  tempSentences.get(pos2).getSentencePos()) {
	      			  swap(tempSentences.get(pos1), tempSentences.get(pos2));
	      			  }
	      		  }
	      	  }
	      	  // Get summary paragraph
	      	  String summaryResult = "";
	      	  for (int pos = 0; pos <= tempSentences.size() / 2; pos++) {
	      		  summaryResult += tempSentences.get(pos).getSentenceText() + "\n";
	      	  }
	      	  return summaryResult.replace("_", " ");
	        }
		}
		
		// Swap necessary data of 2 sentences
		// Note: this function doesn't swap wordsInSentence
		public static void swap(Sentence sentence1, Sentence sentence2) {
			String tempSentenceText = sentence1.getSentenceText();
			sentence1.setSentenceText(sentence2.getSentenceText());
			sentence2.setSentenceText(tempSentenceText);
			int tempSentencePos = sentence1.getSentencePos();
			sentence1.setSentencePos(sentence2.getSentencePos());
			sentence2.setSentencePos(tempSentencePos);
			double tempSentenceImportance = sentence1.getSentenceImportance();
			sentence1.setSentenceImportance(sentence2.getSentenceImportance());
			sentence2.setSentenceImportance(tempSentenceImportance);
		}
		
		// Get webId corresponded to inputURL
		public int getWebIdCorrespondedToInputURL(Connection connection, String inputURL) throws SQLException {
			String sql = "SELECT id FROM articles WHERE url LIKE ?";
			PreparedStatement getWebId = connection.prepareStatement(sql);
			getWebId.setString(1, inputURL);
			ResultSet rs = getWebId.executeQuery();
			int webId = 0;
			while (rs.next()) {
				webId = rs.getInt("id");			
			}
			return webId;
		}
		
		// Classify summarized paragraph and insert into type column in table history_website
		public void classifySummarizedParagraph(Connection connection, int webId) throws SQLException, ClassNotFoundException {
			Classify classify = new Classify(webId);
			String sql = "SELECT type FROM articles WHERE id = ?";
			PreparedStatement getWebId = connection.prepareStatement(sql);
			getWebId.setInt(1, webId);
			ResultSet rs = getWebId.executeQuery();
			int typeReturn = 2;
			while (rs.next()) {
				typeReturn = rs.getInt("type");
			}
			if (typeReturn == -1) {
				text_2.setText("Negative");
			}
			else if (typeReturn == 0) {
				text_2.setText("Neutral");
			}
			else if (typeReturn == 1) {
				text_2.setText("Positive");
			}
			else if (typeReturn == 2) {
				text_2.setText("");
			}
		}
		
		// Searching Google for relative URLs
		public ArrayList<String> searchGoogleForRelativeURLs(String googleSearchString) {
			ArrayList<String> result = new ArrayList<String>();
			try {
			    Process p = Runtime.getRuntime().exec("\"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe\"" + "https://www.google.com.vn/search?q="
			    		+ googleSearchString + "&oq=" + googleSearchString);
			    p.waitFor();
			    String url = "https://www.google.com.vn/search?q=" + googleSearchString + "&oq=" + googleSearchString;
			  
			    Document doc;
			    doc = Jsoup.connect(url).get();
			    Thread.sleep(1500);
			    Elements allRDivs = doc.getElementsByClass("r");
			    for (Element RDiv : allRDivs) {
			    	Elements aDivs = RDiv.getElementsByTag("a");
			    	for (Element aDiv : aDivs) {
			    		result.add(aDiv.attr("href"));
			    	}
			    }
			    String googleSearchResult = "";
			    for (String eachURL : result) {
			    	googleSearchResult += eachURL + "\n\n";
			    }
			    text_3.setText(googleSearchResult);
			} catch (Exception e) {
			    e.printStackTrace();
			}
			return result;
		}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
