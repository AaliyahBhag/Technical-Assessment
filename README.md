# Technical Assessment

## **PART 1**  
This code presents a user interface for pricing South African Government Bonds (SAGBs), offering the option to use example inputs or input custom values. It calculates the clean price, accrued interest, and all-in-price of the bond based on the provided data.

### Using the Bond Pricing Application

#### Opening the Project
1. **Clone the Repository:**
   - Clone the files Main.java and Bond.java in the Bond Pricer folder from the repository containing the Java classes.
   - Save the two classes as Main.java and Bond.java to your local machine.
2. **Open the Project in IntelliJ IDEA:**
   - Launch IntelliJ IDEA.
   - Open the cloned directory as a project in IntelliJ IDEA.
#### Running the Code
1. **Execute the Program:**
   - Locate the Main.java and Bond.java file in the project explorer.
   - Right-click on Main.java and select "Run Main.main()" to execute the program.
#### Using the Application
1. **Initial Input:**
   - Upon running the program, it will display a welcome message and prompt you to insert a nominal value of a bond in Rand.
   - Once then nominal is submitted, you will be prompted to choose example inputs (1 or 2) or input your own values ("no"). 
2. **Example Inputs:**
   - The two predefined inputs are the R186 and R2032 examples given in the assessment.
   - Choosing example 1 or 2 will use predefined values for bond parameters and print the results to the console.
4. **Custom Inputs:**
   - If you choose "no", you'll be asked to input your own values for bond parameters.
   - The inputs required are the settlement date, maturity date, last coupon date, coupon rate, yield-to-maturity and nominal in rand.
   - Please follow the format specified in each prompt for your parameter inputs
5. **Calculations:**
   - The bond object will be created using the provided or predefined values.
   - Clean price, accrued interest, and dirty price will be calculated based on these values using methods defined in the `Bond.java` class.
   - Each calculation is elaborated on within the script.
6. **Displaying Results:**
   - The console output will display the calculated clean price, accrued interest, and dirty price.

### **Test Cases**  
1. Clone the folder Bond Testing in the repository to your local machine.
2. Create a Maven Project in IntelliJ
3. Open the Bond.java class, BondTest.java and pom.xml within the project created.
   - Each test case is justified within the BondTest script.
4. Execute the BondTest class.
5. Test case results will be displayed in the console 

### Efficiency Mechanisms
1. #### Caching Mechanism
   - To avoid redundant calculations, the system caches the present value of face value and coupons. This caching mechanism improves performance by eliminating          unnecessary recalculations. Once the present values are computed, they are stored and reused, significantly reducing the computational load during repeated         operations.
2. #### Optimized Coupon Date Generation
   - The system generates coupon dates only up to the maturity date of the bond. This optimization reduces unnecessary iterations and improves the efficiency of         the coupon date generation process. By limiting the date generation to the bond’s lifecycle, the system ensures that storage is not wasted on irrelevant dates.
3. #### Evaluation of Present Values
   - The system evaluates the present values of face value and coupons only when required. This evaluation strategy minimizes computation overhead and enhances          performance. By delaying the computation until the values are needed, the system avoids unnecessary calculations and conserves processing power.

### Solution Approach
1. #### Object-Oriented Design
   - The solution employs an object-oriented design approach to model bonds as objects with properties and behaviors. This design promotes modularity, extensibility, and code reuse. Each bond instance encapsulates its attributes and methods, allowing for easy manipulation and extension of bond-related functionality.  
2. #### Use of ChronoUnit
   - To accurately determine the date differences for calcalucations throughout the code, ChronoUnit was used. Date arithmetic is essential for generating the correct present value of coupons and nominal values of bonds. 
3. #### Separation of Concerns
   - The solution separates the bond pricing logic into distinct methods within the Bond class. This separation of concerns enhances code readability, maintainability, and testability. By breaking down the functionality into smaller, focused methods, the code becomes easier to understand, modify, and test.
4. #### Exception Handling
   - The solution handles exceptional cases, such as negative input values and invalid bond parameters, using Java exceptions. This approach ensures robustness and      reliability in handling erroneous input parameters. By validating inputs and catching exceptions, the system can provide meaningful error messages to ensure        the user inputs the oorrect parameters to prevent crashes.

### Data Structure
1. #### LocalDate
   - The LocalDate class  is used to represent dates in the bond pricing system. This data structure provides convenient methods for date manipulation and comparison. LocalDate helps in managing and computing dates accurately, which is crucial for bond pricing calculations. 
2. #### List
   - The List interface is used to store coupon dates generated by the system. This dynamic data structure allows flexible manipulation of coupon dates during the bond pricing process. Lists can grow or shrink as needed, making them ideal for managing a variable number of coupon dates.
3. #### Double
   - The double primitive data type is used to represent numerical values, such as coupon rate, yield, face value, and present values. This data type offers           efficient storage and arithmetic operations for floating-point numbers. Using `double` ensures that the calculations are precise and can handle the fractional      values typical in financial computations.

## **PART 2** 
The objective of this code is to provide an interface for querying interest rates from a predefined yield curve. Users can input a specific date and select a rate type (bid, ask, or mid). The code retrieves the appropriate interest rate for that date or performs linear interpolation between the nearest dates if the exact date is not present, ensuring precise rate information for financial decision-making.

### Using the Curve Interpolation Application

#### Opening the Project
1. **Clone the repository**
   - Clone the files Main.java and YieldCurve.java in the Interpolation folder from the repository containing the Java classes.
   - Save the two classes as Main.java and YieldCurve.java to your local machine.
2. **Open the Project in IntelliJ IDEA:**
   - Launch IntelliJ IDEA.
   - Open the cloned directory as a project in IntelliJ IDEA.
#### Running the Code
1. **Execute the Program:**
   - Locate the Main.java and YieldCurve.java file in the project explorer.
   - Right-click on Main.java and select "Run Main.main()" to execute the program.
#### Using the Application
1. **Initial Input:**
   - Upon running the program, it will prompt you to enter a date in yyyy-MM-dd format.
   - After entering the date, you will be prompted to specify the rate type (bid, mid, or ask).
2. **Retrieving Rates:**
   - The program will parse the input date and rate type.
   - It will then retrieve the appropriate rate from the yield curve. If the exact date is not present, the program will interpolate between the nearest available dates.
3. **Displaying Results:**
   - The calculated rate will be formatted and displayed in the console.
   - If an error occurs (e.g., the date is before the range of available dates or an invalid rate type is input), an appropriate error message will be displayed.
   - If the date is after the range of dates, a flat rate is extrapolated.
  
### Test Cases
1. Clone the folder Interpolation Testing in the repository to your local machine.
2. Create a Maven Project in IntelliJ
3. Open the YieldCurve.java class, YieldTest.java and pom.xml within the project created.
   - Each test case is justified within the YieldTest script.
4. Execute the YieldTest class.
5. Test case results will be displayed in the console.

### Efficiency Mechanisms
1. #### Use of TreeMap
   - The TreeMap ensures efficient and ordered storage and retrieval of dates and rates. TreeMap's only store data appliable to the operation and allows for complexity to insert, delete, and look-up relevant data. Trees are particularly efficient for searching and retrieving data, which is the objective of the interface.
2. #### Avoidance of Unnecessary Operations:
   - The program avoids unnecessary operations or calculations when the exact date queried by the user matches an entry in the TreeMap. In such cases, the program directly retrieves the corresponding rate without performing interpolation or additional look-up operations.
   
### Solution Approach 
1. #### Modular Design:
   - The solution adopts a modular design, with distinct classes (Main and YieldCurve) responsible for specific functionalities. This separation of concerns improves code organization, readability, and maintainability, making it easier to understand and extend the functionality of the program.
2. #### User Input Handling:
   - The program starts by taking user inputs for the date and rate type. This interactive approach ensures that users can query the yield curve dynamically.
3. #### Use of TreeMap: 
   - The use of the Tree map ensures the data is stored in the correct order and improves the readibilty of the code for the user to clearly identify the key-value pairs.
4. #### Use of ChronoUnit:
   - To accurately determine the date differences for calculations throughout the code, ChronoUnit was used. The enumeration ensures precision of the interpolation calculation of rates as the date differences determine the output rate. 
5. #### Exception Handing: 
   - The solution includes appropriate exception handing for invalid inputs by the user with clear instructions on how to correctly input the data to obtain the desired output. An example is when the user does not input a valid rate type, the error message will guide the user on the accepted inputs to achieve the rate for the date queried.

### Data Structures
1. #### Nested Static Class:
   - The Rate class is used to encapsulate bid and ask rates. A nested static class is a good way to group related data together, improves code organization and encapsulation.
2. #### Array:
   - Arrays are a simple and efficient way to store multiple values of the same type. They provide quick access to elements by index, which is useful for iterating through the dates and rates when initializing the yield curve
3. #### TreeMap:
   - TreeMap<LocalDate, Rate> is used to store dates and their corresponding rates. The ability for a TreeMap sort data allows for efficient range queries and nearest-neighbor searches, both of which are essential for the interpolation process.






