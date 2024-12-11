package Task;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class FxRates {
	
private Map <String,Double>fxRateCache;


public FxRates() {
	
this.fxRateCache = new HashMap<>();
	
}


///Retrieving the FX Rate 

public double getRate(String baseCurrency, String targetCurrency, LocalDate date) {
	
String key = baseCurrency + targetCurrency + ":" + date.toString();

if(fxRateCache.containsKey(key)) {
	
	return fxRateCache.get(key);
	
}


double rate = fetchRateFromAPI(baseCurrency,targetCurrency,date);

fxRateCache.put(key, rate);


return rate;
	
}


private double fetchRateFromAPI(String baseCurrency, String targetCurrency, LocalDate date) {

   int retries = 3; 
   
   while (retries > 0) {
	
	
	try {
	  
	String urlString = "https://api.challenges.performativ.com/fxrates?base=" 
	              + baseCurrency + "&target=" + targetCurrency + "&date=" +date.toString();
	
	System.out.println("Constructed URL : "+urlString);
	
	URL url = new URL(urlString);
	
	
	//Open the Connection 
	
	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	connection.setRequestMethod("GET");
	connection.setRequestProperty("x-api-key", "FSPkaSbQA55Do0nXhSZkH9eKWVlAMmNP7OKlI2oA");
	
	//Checking the response code
	
	int responseCode = connection.getResponseCode();
	System.out.println("Response code: "+responseCode);
	
	
	if(responseCode == 404) {
		
		System.out.println("Error: API returned 404 (Not Found). Please check the input values.");
		
		return 1.0;
	}
	
	
	if(responseCode != 200) {
		
		if(responseCode != 502) {
			
			System.out.println("Error : API returned a Bad GateWay (502). Retrying...");
			
			retries --;
			
			continue;
			
		} else {
			
			throw new RuntimeException("API Request Failed with Response Code : "+responseCode);
			
		}
		
		 
	}
	
//Reading the response
	
	BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	
	StringBuilder response = new StringBuilder();
	String inputLine;
	
	   while((inputLine = in.readLine()) != null) {
		   
		   response.append(inputLine);
		   
		  }
	   
	   in.close();
	   
	   System.out.println("Full API Response :"+response.toString());
	   
	  
	   /// Parsing the JSON Response
	   
	   ObjectMapper objectMapper = new ObjectMapper();
	   JsonNode rootNode = objectMapper.readTree(response.toString());
	   JsonNode rateNode = rootNode.get(baseCurrency + targetCurrency);
	   
	   
	   // Checking if rateNode is an array or object
	   
	   if(rateNode.isArray()) {
		   
		   //Handling the array case
		   
		   return rateNode.get(0).get("rate").asDouble();
		   
	   }
	   
	   
	   else if(rateNode.isObject()) {
		   
		   //Handling the object case
		   
		   return rateNode.get("rate").asDouble();
	   
	   } else {
		   
		   throw new RuntimeException("Unexpected JSON structure for FX rate. ");
		   
	   }
	   
   }    catch (Exception e) {
    	 
    	 e.printStackTrace();
    	 
    	 retries--;
    	 
    	 if(retries == 0) {
    		 
    		 throw new RuntimeException("Failed after retries: "+e.getMessage());
    	 }
    	 
    	 System.out.println("Retrying ... Remaining attempts : "+retries);
    	 
    	}
	
   }  return 1.0;
	
}


public void cacheRate(String baseCurrency,String targetCurrency,LocalDate date,double rate) {
	
String key = baseCurrency + targetCurrency + ":" + date.toString();

fxRateCache.put(key, rate);
	
}
	

}