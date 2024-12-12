package Task;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;



public class MetricsCalculator {
	
/** Parsing the JSON File and converting into a list of Position objects.
 * 
 * @param is the filePath : The path to the JSON file.
 * @return is a list of position objects
 *
 */
	
/// Parsing the info from JSON File	
	
public List <Position> parsePositions(String filePath){
	
	try { 
		
	// creating the ObhjectMapper instance
	ObjectMapper objectMapper = new ObjectMapper();
	
	//Reading the jSON File and Mapping to the Position array
	Position[] positionsArray = objectMapper.readValue(new File(filePath), Position[].class);
	
	// Converting the Array to a List and return it
	return List.of(positionsArray);
	
	} catch (Exception e) {
		
		e.printStackTrace();
		System.out.println("Error parsing the JSON File");
		
		return null;
	}
	
}


/// Implementation of the IsOPEN Method

public boolean IsOpen(Position position, LocalDate date) {
	
	LocalDate openDate = LocalDate.parse(position.getOpen_date(),DateTimeFormatter.ISO_DATE);
	
	LocalDate closeDate = null;
	
	// Parsing the close_date , if not null
	
	if(position.getClose_date() != null && !position.getClose_date().equals("null")) {
		
		closeDate = LocalDate.parse(position.getClose_date(),DateTimeFormatter.ISO_DATE);
		
		
	}
	
	
	//Checking the condition for being open
	
	return(date.isEqual(openDate) || date.isAfter(openDate) ) && 
			(closeDate == null || date.isBefore(closeDate));
	
}


// Implementation of FilteringPositions which filters the open positions during a specific range of data

public List<Position> filterOpenPositions(List<Position>positions,LocalDate startDate,LocalDate endDate){
	
	
	
	List<Position> openPositions = new ArrayList<>();
	
	
	for(Position position : positions) {
		
		LocalDate openDate = LocalDate.parse(position.getOpen_date(),DateTimeFormatter.ISO_DATE);
		
		LocalDate closeDate = null;
		
		if(position.getClose_date() != null && !position.getClose_date().equals("null")){
			
			closeDate = LocalDate.parse(position.getClose_date(),DateTimeFormatter.ISO_DATE);
			
			}
		
		
		if((openDate.isBefore(endDate) ||  openDate.isEqual(endDate))&& (closeDate == null || closeDate.isAfter(startDate) || closeDate.isEqual(startDate))) {
			
			openPositions.add(position);
			System.out.println("Positions added during filtering : " +position);
			
			
		}
		
		
		
		
		
		}
	
	return openPositions;
	
}



///////// Helper Methods For the Upcoming Method Called "calculateValue" : ///////////

// getPrice Method will fetch the price of an instrument/tool dynamically :


public double getPrice(int toolID) {
	
	//Simulating a price database (toolID --> price mapping)
	
	Map<Integer,Double> priceDatabase = Map.of(10256,90.5,32,115.0,21289,1527.0,21290,0.22);
	
	
	return priceDatabase.getOrDefault(toolID, 0.0);

}


// getFxRate it's used for converting to the target currency

public double getFxRate(String toolCurrency) {
	
	//The same simulation for the Fx rates currency
	
	Map<String,Double> fxRateDatabase = Map.of("USD",1.0,"EUR",1.1,"SEK",0.09,"DKK",0.15);
	
	
	return fxRateDatabase.getOrDefault(toolCurrency, 0.0);

}


// Implementation of calculateValue Method : 

public double calculateValue(Position position, double price, double fxRate) {
	
	return position.getQuantity() * price * fxRate;
	 
     }


// Implementation of calculatePNL Method

// This Method calculates the Profit and Loss of a position

public double calculatePNL(Position position,double currentPrice) {
	
	//Parsing the open and close prices
	
	double openPrice = Double.parseDouble(position.getOpen_price());
	 double closePrice = currentPrice; 
	 
	 //Checking if the position is closed
	 
	 if(position.getClose_price() != null && !position.getClose_price().equals("null")) {
		 
		 closePrice = Double.parseDouble(position.getClose_price());
		 
	 }
	 
	 double pnl = (closePrice - openPrice) * position.getQuantity() - position.getTransaction_costs();
	 
	 
	 return pnl;
	
}


////////////// The Main Method asked in the Assignment Task ///////////////////

public Map<String,Double> calculateMetrics(List<Position>positions,String targetCurrency, LocalDate startDate, LocalDate endDate) {
	
	/// Filtering the positions within the specified range
	
   List<Position> filteredPositions = filterOpenPositions(positions,startDate,endDate);
   
   
   double totalValue = 0.0;
   double totalPNL   = 0.0; 
   double totalReturnPerPeriod = 0.0;
   double totalReturnPerPeriodPercentage = 0.0;
   
   FxRates fxRates= new FxRates();
   
   for(Position position : filteredPositions) {
	   
	 try {
		 
		 /// Calculation of metrics
		 
		 double price  = getPrice(position.getInstrument_id());
		 
		 double fxRate = fxRates.getRate(position.getInstrument_currency(),targetCurrency,LocalDate.now());
		 
		 double Value  = calculateValue(position,price,fxRate);
		 
		 totalValue    += Value;
		 
		 double returnPerPeriod = calculatePNL(position,price) / filteredPositions.size();
		 
		 totalReturnPerPeriod += returnPerPeriod;
		 
		 double returnPerPeriodPercentage = (returnPerPeriod / Value) * 100;
		 
		 totalReturnPerPeriodPercentage  += returnPerPeriodPercentage;
		 
		 
		 System.out.println("Position ID : "+position.getId() + "Value : "+Value + "Return Per Period"+returnPerPeriod + 
				 "Return Per Period Percentage "+returnPerPeriodPercentage);
		 
		 
	 }  catch (Exception e) {
	    	
	    System.out.println("Error processing position ID "+position.getId() + " : "+e.getMessage());
	    	
	    	
	    }
	 
	 }
   
   /// Crating the metrics map - Summary 
   
   System.out.println("\n --- Metrics Summary ---");
   
   Map<String,Double> metricsSummary = new HashMap<>();
   metricsSummary.put("Total Value", totalValue);
   metricsSummary.put("Total PNL", totalPNL);
   metricsSummary.put("Total Return Per Period", totalReturnPerPeriod);
   
   double AvgRetPerPeriodPct = totalReturnPerPeriodPercentage / filteredPositions.size(); 
   
   //AvgRetPerPeriodPct --> Average Return Per Period Percentage
   
   metricsSummary.put("Average Return Per Period Percentage", AvgRetPerPeriodPct);
   
   metricsSummary.forEach((metric,value) -> System.out.println(metric + ": "+value));
   
 
   
   
   return metricsSummary;
    
  
  }

	////////////////////////////////////////// =========== SubMission Part ============== /////////////////////////////////////////////////


private String convertMetricsToJSON(Map<String,Double>metricsSummary) {
	
	JSONObject jsonObject = new JSONObject();
	
	for (Map.Entry<String, Double> entry : metricsSummary.entrySet()) {
		
		jsonObject.put(entry.getKey(), entry.getValue());
		
	}
	
	return jsonObject.toString();
}


public void submitResults(List<Position>positions,String targetCurrency,LocalDate startDate,LocalDate endDate) {
	
	
	Map<String, Double> metricsSummary = calculateMetrics(positions,targetCurrency,startDate,endDate);
	String metricsJSON = convertMetricsToJSON(metricsSummary);
	
	
	System.out.println("JSON Payload : "+metricsJSON);
	
	
	  try {
		  
		 URL url = new URL("https://api.challenges.performativ.com/submit");
		 
		 
		 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		 
		 connection.setRequestMethod("POST");
		 connection.setRequestProperty("Content-Type","application/json");
		 connection.setRequestProperty("x-api-key","FSPkaSbQA55Do0nXhSZkH9eKWVlAMmNP7OKlI2oA");
		 connection.setDoOutput(true);
		 
		     
		       try(OutputStream os = connection.getOutputStream()) {
		    	   
		    	   byte[] input = metricsJSON.getBytes("utf-8");
		    	   
		    	   os.write(input, 0, input.length);
		    	   
		    	   }
		       
		       
		       
		  int responseCode = connection.getResponseCode();
		  System.out.println("Response Code : "+responseCode);
		  
		  if(responseCode == 200) {
			  
			  try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"))){
				  
				  StringBuilder response = new StringBuilder();
				  
				  String responseLine;
				  
				      while((responseLine = br.readLine()) != null) {
				    	  
				    	  response.append(responseLine.trim());
				    	  
				    	 } 
				      
				      System.out.println("Response :"+response.toString());
				  
			  }
			  
			  
			  
		  } else {
			  
			  System.out.println("Failed to submit : "+responseCode);
			  
		  }
		  
		  
		  
		  
	  }  catch(Exception e) {
		  
		  System.out.println("Error during submission"+e.getMessage());
		  e.printStackTrace();
		  
	  }
	
   }

}
