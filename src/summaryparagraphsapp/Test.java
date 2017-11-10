package summaryparagraphsapp;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;



public class Test {
	static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36";
	static String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";
	static String ACCEPT_LANGUAGE = "en,vi;q=0.8";
	static String ACCEPT_ENCODING = "gzip, deflate";
	
	public static JSONObject parseJSONObject(String jsonObject) {
        if (jsonObject == null || jsonObject.isEmpty()) {
            return null;
        }
 
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
	
	public static JSONArray parseJSONArray(String jsonArr) {
        if (jsonArr == null || jsonArr.isEmpty()) {
            return null;
        }
 
        JSONParser parser = new JSONParser();
        JSONArray arr = null;
        try {
            arr = (JSONArray) parser.parse(jsonArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

	StringBuffer sendingGetRequest(String u) throws Exception {
		  URL url = new URL(u);
		  HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 
		  // By default it is GET request
		  con.setRequestMethod("GET");
		  con.setUseCaches(false);
		  
		  //add request header
		  
		  con.setRequestProperty("User-Agent", Test.USER_AGENT);
		  con.setRequestProperty("Accept", Test.ACCEPT);
		  con.setRequestProperty("Accept-Language", Test.ACCEPT_LANGUAGE);
		  
		  // Reading response from input Stream
		  BufferedReader in = new BufferedReader(
		          new InputStreamReader(con.getInputStream()));
		  String output;
		  StringBuffer response = new StringBuffer();
		 
		  while ((output = in.readLine()) != null) {
		   response.append(output);
		  }
		  in.close();
		  
		 // return response;
  //printing result from response
		  //String jsonArr = response.toString();
		  //if(response.length() == 2)  System.out.println("a");
		  
		  //JSONArray arr = parseJSONArray(jsonArr);
		  //for(int i = 0; i < arr.size(); i++) {
			  //JSONObject jsonObject = parseJSONObject(arr.get(i).toString());
		      //String s = jsonObject.get("Content").toString();
		      //System.out.println(s);
		  //} 
		  
		  return response;
		 }
		 
		 // HTTP Post request
		 boolean sendingPostRequest(String hs) throws Exception {
		 
		  String url = "http://vspell.com/Spell/SpellCheck";
		  URL obj = new URL(url);
		  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 
		        // Setting basic post request
		  con.setRequestMethod("POST");
		  con.setRequestProperty("User-Agent", Test.USER_AGENT);
		  con.setRequestProperty("Accept-Language", Test.ACCEPT_LANGUAGE);
		  con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		  con.setRequestProperty("Accept-encoding", Test.ACCEPT_ENCODING);
		  
		  String postJsonData = "htmlString="+URLEncoder.encode(hs, "UTF-8");  	
		  // Send post request
		  con.setDoOutput(true);
		  DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		  wr.writeBytes(postJsonData);
		  wr.flush();
		  wr.close();
		 
		  BufferedReader in = new BufferedReader(
		          new InputStreamReader(con.getInputStream()));
		  String output;
		  StringBuffer response = new StringBuffer();
		 
		  while ((output = in.readLine()) != null) {
		   response.append(output);
		  }
		  in.close();
		  
		  //printing result from response
		  String st = response.toString();
		  JSONObject jsonObject = parseJSONObject(st);
	    	String s = jsonObject.get("SuggestionCount").toString();
	    	int foo = Integer.parseInt(s);
	    	if(foo==0) return true; //không có lỗi chính tả
	    	
	    	return false; //có lỗi chính tả
		 }
	  
	  public void getComment(String url) throws Exception {
	      String pattern = "[0-9]+.htm";

	      // Create a Pattern object
	      Pattern r = Pattern.compile(pattern);

	      // Now create matcher object.
	      Matcher m = r.matcher(url);
	      
	      String regex = "";       
	      if(m.find()) regex = m.group(0);
	      int pos = regex.indexOf(".");
	      String id = regex.substring(0, pos);
	      
	      String u = "";
	      int n = 0;
	      StringBuffer response  = new StringBuffer();
	      
	      do {
		     u = "http://wcm.dantri.com.vn/comment/list/1-" + id + "-0-" + n + "-5.htm";
	    	  
		      response = sendingGetRequest(u);
		      
		      //lấy các comment
		      String jsonArr = response.toString();
			  
			  JSONArray arr = parseJSONArray(jsonArr);
			  for(int i = 0; i < arr.size(); i++) {
				  JSONObject jsonObject = parseJSONObject(arr.get(i).toString());
			      String comment = jsonObject.get("Content").toString();
			      System.out.println(comment);
			      //check comment có lỗi chính tả không
			      if(sendingPostRequest(comment)) {   //nếu không có lỗi chính tả thì thêm comment vào csdl
			    	//thêm comment vào csdl
			    	  System.out.println(comment);
			      }
			      
			  } 	      
		      n += 5;
	      }
	      while(response.length() > 2); //response.lengt = 2 tức là response trả về mảng rỗng [];
	 
	  }
		 
	  public static void main(String[] args) throws Exception {
		  Test c = new Test();
		  c.getComment("http://dantri.com.vn/kinh-doanh/dai-gia-nhat-mo-tram-xang-tai-viet-nam-petrolimex-noi-gi-20171008021028393.htm");
		  //c.sendingGetRequest("http://wcm.dantri.com.vn/comment/list/1-20171008021028393-0-20-5.htm");
//		  c.sendingPostRequest("Thử soát lỗi \"chính tả\".");
	  }
	  
	  
}
