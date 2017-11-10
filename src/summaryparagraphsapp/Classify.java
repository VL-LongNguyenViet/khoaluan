package summaryparagraphsapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.widgets.Shell;

/**
 * Author: Ngo Phuong Tuan
 * Classify
 */
public class Classify {
	
    // Classify document
    Classify(int webId) throws SQLException, ClassNotFoundException {

        //khởi tạo rate
        float rate1 = 1;   //type = -1
        float rate2 = 1;   //type = 0
        float rate3 = 1;   //type = 1


   	// tính P(c_i)

        Connection connection = ConnectionUtils.getMyConnection();
        String sql = "SELECT * FROM articles where type = -1";   //lấy số lượng bài loại -1
	PreparedStatement statement = connection.prepareStatement(sql);
	ResultSet rs = statement.executeQuery();
	float c1 = 0;
        while(rs.next()) {
            c1++;
        }

	sql = "SELECT * FROM articles where type = 0";   //lấy số lượng bài loại 0
	PreparedStatement statement1 = connection.prepareStatement(sql);
	ResultSet rs1 = statement1.executeQuery();
	float c2 = 0;
        while(rs1.next()) {
            c2++;
        }

	sql = "SELECT * FROM articles where type = 1";   //lấy số lượng bài loại 1
	PreparedStatement statement2 = connection.prepareStatement(sql);
	ResultSet rs2 = statement2.executeQuery();
	float c3 = 0;
        while(rs2.next()) {
            c3++;
        }

	float sum = c1 + c2 + c3;
	float pc1 = c1 / sum;
	float pc2 = c2 / sum;
	float pc3 = c3 / sum;

	rate1 *= pc1;   
        rate2 *= pc2;	  
        rate3 *= pc3;

	sql = "SELECT * FROM wordweb where webId = " + webId;   //lấy số lượng bài loại 1
	PreparedStatement statement3 = connection.prepareStatement(sql);
	ResultSet rs3 = statement3.executeQuery();
	while(rs3.next()) {
		//tính P(x_pos|c_i)
		int wordId = rs3.getInt("wordId");
		int pos = rs3.getInt("pos");

		
		//P(x_pos|c_1)
		sql = "SELECT * FROM wordweb WHERE wordId= " + wordId + " and pos = " + pos + " and webId in (select id from articles WHERE type = -1)";   //lấy số lượng bài loại -1
		PreparedStatement statement4 = connection.prepareStatement(sql);
                ResultSet rs4 = statement4.executeQuery();
		
                float cxpos1 = 0;
                while(rs4.next()) {
                    cxpos1++;
                }
		if(cxpos1 != 0) rate1 *= cxpos1 / c1;
		System.out.println("a"  +rate1 + " " +cxpos1+" "+c1);
		//P(x_pos|c_2)
		sql = "SELECT * FROM wordweb WHERE wordId= " + wordId + " and pos = " + pos + " and webId in (select id from articles WHERE type = 0)";   //lấy số lượng bài loại 0
		PreparedStatement statement5 = connection.prepareStatement(sql);
                ResultSet rs5 = statement5.executeQuery();
		
                float cxpos2 = 0;
                while(rs5.next()) {
                    cxpos2++;
                }
		if(cxpos2 != 0) rate2 *= cxpos2 / c2;
		System.out.println("b" +rate2 + " " +cxpos2+" "+c2);
		//P(x_pos|c_3)
		sql = "SELECT * FROM wordweb WHERE wordId= " + wordId + " and pos = " + pos + " and webId in (select id from articles WHERE type = 1)";   //lấy số lượng bài loại 1
		PreparedStatement statement6 = connection.prepareStatement(sql);
                ResultSet rs6 = statement6.executeQuery();
		
                float cxpos3 = 0;
                while(rs6.next()) {
                    cxpos3++;
                }
		if(cxpos3 != 0) rate3 *= cxpos3 / c3;
		System.out.println("c"  +rate3 + " " +cxpos3+" "+c3);
	}

	float max = Math.max(Math.max(rate1, rate2), rate3);
	System.out.println(rate1+ " " +rate2 + " " +rate3);
	int type = -2;
	if(max == rate1) {
		type = -1;
	}
	else if(max == rate2) {
           type = 0;
	}
        else type = 1;
	System.out.println(type);
	sql = "UPDATE `articles` SET `type`= ? WHERE id = ?";   // cap nhat database
	PreparedStatement statement6 = connection.prepareStatement(sql);
    statement6.setInt(1, type);
    statement6.setInt(2, webId);
    statement6.execute();
    }


}