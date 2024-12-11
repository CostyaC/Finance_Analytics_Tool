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
