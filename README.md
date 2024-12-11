# Performativ-Tech-Challenge  
**Financial Metrics Calculator System**  

---

## Guide to Setup the Project  

### 1) Download the Repository  

- Clone the repository or download the files as a ZIP.  

---

### 2) Download the JAR Files  

- In the repository, locate the `Supportive Files` folder, which contains all the required JAR files.  

---

### 3) Save the JAR Files  

- Save the `Supportive Files` folder in the same directory as the rest of the project files.  

---

### 4) Add the JAR Files to the Project’s Build Path  

#### a) For Eclipse Users:  
1. Right-click on the project in the **Package Explorer**.  
2. Select **Build Path -> Add External Archives...**.  
3. Browse to the `Supportive Files` folder and add all three JAR files.  

#### b) For IntelliJ IDEA Users:  
1. Open the **Project Structure** (File -> Project Structure or press `Ctrl+Alt+Shift+S`).  
2. Under **Modules**, select the module corresponding to this project.  
3. Go to the **Dependencies** tab, click the `+` button, and select **JARs or directories...**.  
4. Navigate to the `Supportive Files` folder and add the JAR files.  

---

### 5) Configure the Path to the JSON File  

The project relies on a JSON file containing position data. You must ensure that the file path is correctly set in the `MetricsCalculatorTest` class. This path will vary based on where you store the JSON file on your local system. Here's how to set it:  

1. Locate the JSON file in the `Supportive Files` folder.  
2. Note the full path to the JSON file on your system. For example:  
   - Windows: `C:/Users/YourName/FolderPath/tech-challenge-2024-positions.json`  
   - macOS/Linux: `/Users/YourName/FolderPath/tech-challenge-2024-positions.json`  
3. Open the `MetricsCalculatorTest.java` file in your IDE.  
4. Find the line containing the `filePath` variable:  
   ```java  
   String filePath = "C:/Users/koste/Desktop/My FolderS/JobFolder/IT JobS/PerFormatiV-Job/tech-challenge-2024-positions.json";  

### 6) Replace the existing path with the one corresponding to your system, for example : 

 ```java  

`String filePath = "YourSystemPath/tech-challenge-2024-positions.json";

``` 
### 7) Save All the changes and run the project

### Project Structure:

The Project is structured as follows:

Performativ-Tech-Challenge/  
├── src/  
│   ├── Task/  
│   │   ├── MetricsCalculator.java  
│   │   ├── MetricsCalculatorTest.java  
│   │   ├── FxRates.java  
│   │   ├── Position.java  
│   ├── Test_API/  
│   │   ├── APITest.java  
├── Supportive Files/  
│   ├── jackson-annotations-2.15.2.jar  
│   ├── jackson-core-2.15.2.jar  
│   ├── jackson-databind-2.15.2.jar  
│   ├── tech-challenge-2024-positions.json  
├── README.md  


### Key Folders : 

src/Task/: Contains the core implementation classes for the project:

MetricsCalculator.java: The main calculator class.
MetricsCalculatorTest.java: The test class to validate functionality.
FxRates.java: Handles currency conversion and API interaction.
Position.java: Represents a financial position.
src/Test_API/: Contains:

APITest.java: A class to validate API functionality.
Supportive Files/: Includes required dependencies and the JSON input file.


