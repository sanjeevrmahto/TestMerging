package com.novel.test;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperRunManager;

public class ReportTest {

	public static void main(String[] args) {
		System.out.println("HI");
		ReportTest  test   = new ReportTest();
		//T_PRICE_BID_ID
		Map<String, Object> params =  new HashMap<>();
		params.put("M_BPARTNER_ID", 1478l);
		params.put("Report_Dir", "d:\\vivek\\Reports\\MyReports\\");
		test.generateReport("RegistrationPDF", params, "d:\\eatApp\\docs\\", "PDF");
		
	/*	params.put("T_PRICE_BID_ID", 1530l);*/
		/*params.put("T_BIDDER_ID", 1182l);*/
		/*params.put("T_ITEM_BID_ID", 1393l);*/
/*		params.put("Report_Dir", "E:\\eatApp\\reports\\");
		params.put("M_BPARTNER_ID", 2420l);
*/		
/*		params.put("Report_Dir", "d:\\vivek\\Reports\\MyReports\\");*/
		/*test.generateReport("Price_Bid_Req_Doc", params, "E:\\eatApp\\docs\\", "PDF");*/
		/*test.generateReport("Commercial_Req_Doc", params, "E:\\eatApp\\docs\\", "PDF");*/
		/*test.generateReport("Final_Commercial_Scrutiny_Required_Documents_Details", params, "E:\\eatApp\\docs\\", "PDF");*/
		/*test.generateReport("Final_Technical_Scrutiny_Required_Documents_Details", params, "E:\\eatApp\\docs\\", "PDF");*/
		/*test.generateReport("Preliminary_Commercial_Scrutiny_Required_Documents_Details", params, "E:\\eatApp\\docs\\", "PDF");*/
		/*test.generateReport("RegistrationPDF", params, "E:\\eatApp\\docs\\", "PDF");*/

//		params.put("Report_Dir", "E:\\eatApp\\reports\\");
		params.put("T_TECHNICAL_BID_ID", 1157l);

		test.generateReport("Technical_Bid", params, "E:\\eatApp\\docs\\", "PDF");
		
		
		
		
		
		

	}

	public Connection getConnection(){
		Connection con =null;
		try{
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		con =DriverManager.getConnection(  
		"jdbc:oracle:thin:@172.20.20.168:1521:xe","eat","PASS123");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public void fillReportData(InputStream input,  Map<String, Object> params, OutputStream output, String outputFormat) {
		
		Connection connection = getConnection();
		try{
			JasperRunManager.runReportToPdfStream(input, output, params, connection);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{
				connection.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
//			dbUtil.close(connection);
			closeInputStream(input);
		}
	}
	
	public void closeInputStream(InputStream input){
		try{
			input.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeOutputStream(OutputStream output){
		try{
			output.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String generateReport(String reportName, Map<String, Object> params, OutputStream output, String outputFormat) {
		long timestamp= System.currentTimeMillis();
		String reportFile = reportName+".jasper";
		String filePath =  "E:\\eatApp\\reports\\"+reportFile;
		InputStream input = getInputStream(filePath); 
		fillReportData(input, params, output, outputFormat);
		String outputFile = reportName +timestamp;	
		return outputFile;
	}

	public InputStream getInputStream(String filePath){
		InputStream in = null;
		try{
			in = new FileInputStream(filePath);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return in;
	}

	
	public String generateReport(String reportName, Map<String, Object> params, String outputDir, String outputFormat) {
		StringBuilder filePathBuilder =  new StringBuilder();
		filePathBuilder.append(outputDir)
			.append(reportName)
			.append("_")
			.append(System.currentTimeMillis())
			 .append(".")
				.append(outputFormat);
		String outputFile = filePathBuilder.toString();
		OutputStream outputStream = null;
		try{
			outputStream =  new FileOutputStream(outputFile);
			generateReport(reportName, params, outputStream, outputFormat);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try{
				outputStream.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return outputFile;
	}
}

