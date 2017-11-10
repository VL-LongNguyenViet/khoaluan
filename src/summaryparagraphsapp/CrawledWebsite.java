package summaryparagraphsapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
*
* @author Ngo Phuong Tuan
* Edited by: Nguyen Viet Long
* thống kê số lượng bài báo theo keyword
*/

// NOTE: Haven't deal anything with attribute TYPE yet !
public class CrawledWebsite {

	private String host;
	private String url;
	private String summary;
	private int type = 0;
	private java.sql.Date date;
	private String title;
	private ArrayList<String> keywords = new ArrayList<String>();
	
	CrawledWebsite(String _host, String _url, String _summary) throws IOException, ClassNotFoundException, SQLException, ParseException{
		host = _host;
		url = _url;
		summary = _summary;
		type = 0;
		Document doc;
		doc = Jsoup.connect(url).get();
		
		// Set title
		setTitle(doc);
		
		// Get all keywords from website
		setKeywords(doc);
		
		// Set date
		setDate(doc);
		
		// Save to history_website
		saveToHistoryWebsite();
		
		// Save to activities_diary
		String activity = "Single summarizing";
		String content = "Host: " + host + "\n\n"
				+ "URL: " + url + "\n\n"
				+ "Title: " + title + "\n\n"
				+ "Article date: " + date + "\n\n"
				+ "Summarizing result: " + summary + "\n\n";
		String databaseImpact = "INSERT INTO articles (user_id, `host`, `url`, title, `summary`,date)"
				+ "VALUES (" + AppRun.userID + ", " + host + ", " + url + ", " + title + ", "
				+ summary + ", " + date + ")" + "\n\n";
		MultiParagraphsSummarizingView.insertToActivitiesDiary(activity, content, databaseImpact);
	}
	
	CrawledWebsite(String _url, String _title){
		url = _url;
		title = _title;
	}
	
	String getHost() {
		return host;
	}
	
	String getUrl() {
		return url;
	}
	
	String getSummary() {
		return summary;
	}
	
	int getType() {
		return type;
	}
	
	Date getDate() {
		return date;
	}
	
	String getTitle() {
		return title;
	}
	
	ArrayList<String> getKeywords(){
		return keywords;
	}
	
	void setHost (String _host) {
		host = _host;
	}
	
	void setUrl (String _url) {
		url = _url;
	}
	
	void setSummary (String _summary) {
		summary = _summary;
	}
	
	void setType (int _type) {
		type = _type;
	}
	
	void setDate (Date _date) {
		date = _date;
	}
	
	void setTitle(String _title) {
		title = _title;
	}
	
	// Set title - by longnv
	void setTitle(Document doc) {
		Elements titleTags = doc.getElementsByTag("title");
		for (Element eachTitleTag : titleTags) {
			title = eachTitleTag.text();
		}
	}
	
	// Get all keywords from website - by longnv
	void setKeywords (Document doc) {
		Elements allMetaTags = doc.getElementsByTag("meta");
		for (Element metaTag : allMetaTags) {
			if (metaTag.attr("name").contains("keyword")) {
				for (String keyword : metaTag.attr("content").split(",")) {
					keywords.add(keyword);
				}
			}
		}
	}
	
	// Set date - by longnv (NOTE: doesn't work for some pages)
	void setDate (Document doc) throws ClassNotFoundException, SQLException, ParseException {
		Connection connection = ConnectionUtils.getMyConnection();
		String sql = "SELECT get_time FROM url_process WHERE page_name LIKE ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, host);
		ResultSet rs = statement.executeQuery();
		String wayToGetTime = "";
		while (rs.next()) {
			wayToGetTime = rs.getString("get_time");
		}
		String dateString = "";
		Elements allTagWithThatClass = doc.getElementsByClass(wayToGetTime);
		for (Element eachTag : allTagWithThatClass) {
			for (String eachString : eachTag.text().trim().split(" ")) {
				if (eachString.matches("(.*)/(.*)/(.*)") || eachString.matches("(.*)-(.*)-(.*)")) {
					ArrayList<String> partsOfDate = new ArrayList<String>();
					if (eachString.matches("(.*)/(.*)/(.*)") ) {
						for (String eachPart : eachString.split("/")) {
							 partsOfDate.add(eachPart);
						} 
					}
					else if (eachString.matches("(.*)-(.*)-(.*)")) {
						for (String eachPart : eachString.split("-")) {
							 partsOfDate.add(eachPart);
						} 
					}
					for (int pos = 2; pos >= 0; pos--) {
						if (pos != 0) {
							dateString += partsOfDate.get(pos) + "-";
						}
						else {
							dateString += partsOfDate.get(pos);
						}
					}
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date dateUtil = dateFormat.parse(dateString);
					date = new java.sql.Date(dateUtil.getTime());
				}
			}
		}
	}
	
	// Save to articles table in database - by tuannp
		public void saveToHistoryWebsite() throws SQLException, ClassNotFoundException {
			//thêm thông tin về bài báo
			Connection connection = ConnectionUtils.getMyConnection();
	        String sql = "INSERT INTO articles (user_id, `host`, `url`, title, `summary`,date) VALUES (?,?,?,?,?,?)";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setInt(1, AppRun.userID);
	        statement.setString(2, host);
	        statement.setString(3, url);
	        statement.setString(4, title);
	        statement.setString(5, summary);
	        statement.setDate(6, date);
	        statement.execute();
	        
	        //tìm id bài báo vừa thêm
	        sql = "SELECT id FROM articles WHERE url LIKE ?";
	        PreparedStatement statementa = connection.prepareStatement(sql);
	        statementa.setString(1, url);
	        ResultSet rsa = statementa.executeQuery();
	        
	        while(rsa.next()) {
		        int websiteId = rsa.getInt("id");
		        
		        for(String keyword : keywords) {
		            //kiểm tra keyword tồn tại chưa
		            sql = "SELECT name FROM keyword WHERE name LIKE ?";
		            PreparedStatement statement1 = connection.prepareStatement(sql);
		            statement1.setString(1, keyword);
		            ResultSet rs1 = statement1.executeQuery();
		            
		            int check = 0;
		            while(rs1.next()){
		                check++;
		            }
		            
		            //check = 0 là chưa tồn tại
		            if(check == 0) {
		                //thêm keyword mới
		                sql = "INSERT INTO `keyword`(`name`) VALUES (?)";
		                PreparedStatement statement2 = connection.prepareStatement(sql);
		                statement2.setString(1, keyword);
		                statement2.execute();
		            }
		            
		            
		            //tìm id keyword 
		            sql = "SELECT id FROM keyword WHERE name LIKE ?";
		            PreparedStatement statement3 = connection.prepareStatement(sql);
		            statement3.setString(1, keyword);
		            ResultSet rs3 = statement3.executeQuery();
		            rs3.next();
		            int keywordId = rs3.getInt("id");
		            
		            //thêm quan hệ website-keyword
		            sql = "INSERT INTO `keyword_web`(`keywordId`, `websiteId`) VALUES (?,?)";
		            PreparedStatement statement4 = connection.prepareStatement(sql);
		            statement4.setInt(1, keywordId);
		            statement4.setInt(2, websiteId);
		            statement4.execute();
		        }
	        }
	       
		}
		
		
}
