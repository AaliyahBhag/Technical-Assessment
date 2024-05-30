# Technical Assessment

## **PART 1**  
This code presents a user interface for pricing South African Government Bonds (SAGBs), offering the option to use example inputs or input custom values. It calculates the clean price, accrued interest, and all-in-price of the bond based on the provided data.

### Using the Bond Pricing Application

#### Opening the Project
1. **Clone the Repository:**
   - Clone the files Main and Bond within the Bond Pricer folder from the repository containing the Java classes.
   - Save the two classes as 'Main.java' and 'Bond.java' to your local machine.
2. **Open the Project in IntelliJ IDEA:**
   - Launch IntelliJ IDEA.
   - Open the cloned directory as a project in IntelliJ IDEA.
#### Running the Code
1. **Execute the Program:**
   - Locate the `Main.java` and 'Bond.java' file in the project explorer.
   - Right-click on `Main.java` and select "Run Main.main()" to execute the program.
#### Using the Application
1. **Initial Input:**
   - Upon running the program, it will display a welcome message and prompt you toinsert a nominal value of a bond in Rand.
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
6. **Viewing Results:**
   - The console output will display the calculated clean price, accrued interest, and dirty price.

### **Test Cases**  
1. Clone the folder labelled Bond Testing in the repository to your local machine.
2. Open the Bond.java class, BondTest.java and pom.xml in IntelliJ.
   - Each test case is justified within the BondTest script.
4. Execute the pom.xml script and run the BondTest class. 

### Efficiency Mechanisms

1. #### Caching Mechanism
   - To avoid redundant calculations, the system caches the present value of face value and coupons. This caching mechanism improves performance by eliminating          unnecessary recalculations. Once the present values are computed, they are stored and reused, significantly reducing the computational load during repeated         operations.

2. #### Optimized Coupon Date Generation
   - The system generates coupon dates only up to the maturity date of the bond. This optimization reduces unnecessary iterations and improves the efficiency of         the coupon date generation process. By limiting the date generation to the bond’s lifecycle, the system ensures that storage is not wasted on irrelevant dates.

3. #### Evaluation of Present Values
   - The system evaluates the present values of face value and coupons only when required. This evaluation strategy minimizes computation overhead and enhances          performance. By delaying the computation until the values are needed, the system avoids unnecessary calculations and conserves processing power.

### Solution Approach

1. #### Object-Oriented Design
   - The solution employs an object-oriented design approach to model bonds as objects with properties and behaviors. This design promotes modularity,                   extensibility, and code reuse. Each bond instance encapsulates its attributes and methods, allowing for easy manipulation and extension of bond-related             functionality.

2. #### Separation of Concerns
   - The solution separates the bond pricing logic into distinct methods within the Bond class. This separation of concerns enhances code readability,                   maintainability, and testability. By breaking down the functionality into smaller, focused methods, the code becomes easier to understand, modify, and test.

3. #### Exception Handling
   - The solution handles exceptional cases, such as negative input values and invalid bond parameters, using Java exceptions. This approach ensures robustness and      reliability in handling erroneous input parameters. By validating inputs and catching exceptions, the system can provide meaningful error messages to ensure        the user inputs the oorrect parameters to prevent crashes.

### Data Structure
1. #### LocalDate
   - The `LocalDate` class from the `java.time` package is used to represent dates in the bond pricing system. This data structure provides convenient methods           for date manipulation and comparison. `LocalDate` helps in managing and computing dates accurately, which is crucial for bond pricing calculations.

3. #### List
   - The `List` interface from the `java.util` package is used to store coupon dates generated by the system. This dynamic data structure allows flexible                manipulation of coupon dates during the bond pricing process. Lists can grow or shrink as needed, making them ideal for managing a variable number of               coupon dates.

4. #### Double
   - The `double` primitive data type is used to represent numerical values, such as coupon rate, yield, face value, and present values. This data type offers           efficient storage and arithmetic operations for floating-point numbers. Using `double` ensures that the calculations are precise and can handle the fractional      values typical in financial computations.
