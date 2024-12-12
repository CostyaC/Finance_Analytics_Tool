package Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MetricsCalculatorTest {

	public static void main(String[] args) {
		
//////////////////////////////// Beginning of Parsing Phase		
		
		// The Path for the JSON File:
		
		String filePath = "C:/Users/koste/Desktop/My FolderS/JobFolder/IT JobS/PerFormatiV-Job/tech-challenge-2024-positions.json";



		// Creating an instance of the MetricCalculator class

		MetricsCalculator calculator = new MetricsCalculator();

		//Parsing the JSON File into a list of Position objects

		List<Position> positions = calculator.parsePositions(filePath);

		// Printing the parsed positions to verify 

		if(positions != null) {
			
			for (Position position : positions) {
				
				System.out.println(position);
				
			}
			
		 }  else {
				
				System.out.println("Failed to parse the positions");
				
			}
		
		/////////////////////////////////////// Ending of Parsing Phase
		
		
	      // Testing the IsOpen Method
		
         Position examplePosition = new Position();
         
         examplePosition.setOpen_date("2024-01-01");
         examplePosition.setClose_date("2024-12-31");
         
         LocalDate today = LocalDate.now();
         
         boolean isOpen = calculator.IsOpen(examplePosition, today);
         
         System.out.println("Is Open For Position ID "+examplePosition.getId() + "on" +today + " : " + isOpen);
         
         
         //Testing the Filtering Method
         
         List<Position> Allpositions = calculator.parsePositions(filePath); // Parsing positions from the jSON File
         
         //Defining a date Range
         
         LocalDate startDate = LocalDate.of(2023, 1, 1);
         LocalDate endDate   = LocalDate.of(2024, 11, 10);
         
         //Filtering the positions opened into the specified above range
         
         List<Position> openPositions = calculator.filterOpenPositions(Allpositions, startDate, endDate);
         
       //Printing   the total positions parsed and the filtered positions count 
         
        System.out.println("Total Positions parsed : "+Allpositions.size());
        System.out.println("Filtered open Positions : "+ openPositions.size());
        
        //Validating the filtering result
        
        if(openPositions.isEmpty()) {
        	
        System.out.println("Filterring Error : No positions were found open in the specified range");
        
         }  else {
        	
        	  System.out.println("\nFiltering Succesful : Open Positions found withing the speicified range");
        	  System.out.println("\nFiltered Open Positions : ");
        	  
        	  for (Position position : openPositions) {
        		  
        		    System.out.println(position);
        	  }
        	
        }
        
        
        
        /// Testing the calculateValue NMethod : 
        
        MetricsCalculator NewCalculator = new MetricsCalculator();
        
        Position position = new Position();
        
        // Testing a Sample
        
        position.setQuantity(50);
        position.setTool_id(10256);
        position.setTool_currency("EUR");
        position.setQuantity(50);
        
        
        // Testing a Sample for Position (Closed)
        
        Position closedPosition = new Position();
        closedPosition.setOpen_price("90.515");
        closedPosition.setClose_price("160");
        closedPosition.setQuantity(35);
        closedPosition.setTransaction_costs(0);
        
        
        // Testing a Sample for Position (Open)
        
        Position openPosition = new Position();
        openPosition.setOpen_price("80");
        openPosition.setClose_price("null");
        openPosition.setQuantity(50);
        openPosition.setTransaction_costs(0);
        
        
        
        //Sample for Price and FxRate
        double price  = NewCalculator.getPrice(position.getInstrument_id());
        double fxRate = NewCalculator.getFxRate(position.getInstrument_currency());
        
        // Sample for current price
        double currentPrice = 100;
        
        //////////////////// Calculation Area   /////////////////////
        
        try {
        	
        // Testing Dynamic calculation of the value	:
        	
        double value = calculator.calculateValue(position,price,fxRate);
        
        System.out.println("\nValue of the position in USD:  " +value);
        
        
    } catch(IllegalArgumentException e) {
    	
    	//Handles Invalid input exceptions. such as negative or zero price/fxRate
    	
    	System.out.println("Error in Value Calculation : "+e.getMessage());
    }
        
        
        
      /// Testing the CalculatePNL Method :
        
        try {
        	
        double closedPnL = NewCalculator.calculatePNL(closedPosition, currentPrice);
        System.out.println("\nPnL for closed : "+closedPnL);
        
        //Calculate PnL for an open position
        
        double openPnL = NewCalculator.calculatePNL(openPosition, currentPrice);
        System.out.println("PnL for an open position : "+openPnL);
        
        } catch(IllegalArgumentException e) {
        	
        	//Handles errors in parsing prices or invalid inputs during PNL calculations
        	
        	System.out.println("Error in PNL calculation :" + e.getMessage());
        	
        	
        }
        
        
        
    //////// Testing the FxRates Class 
        
     FxRates fxRates = new FxRates();
     
     /// Testing the Caching Rate 
     
     System.out.println("\nTesting cache and getRate : ");
     
     String baseCurrency = "EUR";
     String targetCurrency = "USD";
     LocalDate date = LocalDate.of(2023, 1, 1);
     double rate = 1.12;
     
     //Cache the Rate
     fxRates.cacheRate(baseCurrency, targetCurrency, date, rate);
     
     //Retrieve and print the cached rate 
     
     double cachedRate = fxRates.getRate(baseCurrency, targetCurrency, date);
     System.out.println("Cached Rate is : "+cachedRate);
     
     //Testing and Retrieving UN-cached rates
     
     System.out.println("\nTesting getRate for un-cached values :");
     
     String uncachedBaseCurrency = "GBP";
     String uncachedTargetCurrency = "USD";
     LocalDate uncachedDate = LocalDate.of(2023, 5, 1);
     double  uncachedRate = fxRates.getRate(uncachedBaseCurrency, uncachedTargetCurrency, uncachedDate);
     
     System.out.println("Uncached Rate : "+uncachedRate);
     
     
     ///// Testing the API Fetching for Valid Inputs
     
     System.out.println("\nTesting API Feetching for Valid inputs : ");
     
     try {
    	 
    	 String apiBaseCurrency = "EUR";
    	 String apiTargetCurrency = "USD";
    	 LocalDate apiDate = LocalDate.of(2023, 1, 1);
    	 
    	 
    	 double fetchedRate = fxRates.getRate(apiBaseCurrency, apiTargetCurrency, apiDate);
    	 
    	 System.out.println("Fetched Rate (from API or Cache) : "+fetchedRate);
    	 
    } catch(RuntimeException e) {
    	
    	System.out.println("Error fetching FX rate : "+e.getMessage());
    	
    }
     
     
     ///// Testing the API Fetching for INvalid Inputs
     
     System.out.println("\nTesting API Feetching for INvalid inputs : ");
     
     try {
    	 
    	 String validBaseCurrency = "USD";
    	 String validTargetCurrency = "EUR";
    	 LocalDate validDate = LocalDate.of(2023, 1, 1);
    	 
    	 double fetchedRate = fxRates.getRate(validBaseCurrency,validTargetCurrency,validDate);
    	 
    	 System.out.println("Fetched Rate (from API or Cache) :  "+fetchedRate);
    	 
    	 }  catch(RuntimeException e) {
    		 
    		 System.out.println("Expected Error for invalid inputs : "+e.getMessage());
    		 
    	 }
     
     
     
     
     /////////////// Testing the Main Method of the Assignment --> Calculate Metrics /////////////////////////////////////////////
     
     System.out.println("\n Testing the --Calculate Metrics -- Method :");
     
     /// Defining the inputs 
     
    String targetCurrency_Test      = "USD";
    LocalDate startDate_Test        = LocalDate.of(2023, 1, 1);
    LocalDate endDate_Test          = LocalDate.of(2024, 11, 10);
    
    
    List<Position> positions_test = NewCalculator.parsePositions(filePath);
    
    
    
      if(positions_test != null) {
    	  
    	  
   Map<String,Double> metricsResult = NewCalculator.calculateMetrics(positions_test, targetCurrency_Test, startDate_Test, endDate_Test);
   
   System.out.println("\n---Final Metrics Summary---");
   
   metricsResult.forEach((metric,value) -> System.out.println(metric + ": "+value));
    	  
    	  
      }  else {
    	  
    	  System.out.println("\nFailed to parse positions for testing");
    	  
      }


///////////////////=============== Testing the Submission Method ==================/////////////////////////////////////
      
      System.out.println("\nTesting the Submission Method :");
      
      try {
    	  
    	  NewCalculator.submitResults(positions_test, targetCurrency_Test, startDate_Test, endDate_Test);
    	  
    	  
      }   catch(Exception e) {
    	  
    	  System.out.println("\nError during submission test : "+e.getMessage());
    	  
      }
 
	}
	
}
