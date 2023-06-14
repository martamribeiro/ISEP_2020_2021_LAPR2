# US 9 - As an administrator, I want to specify a new type of test and its collecting methods

## 1. Requirements Engineering

*In this section, it is suggested to capture the requirement description and specifications as provided by the client as well as any further clarification on it. It is also suggested to capture the requirements acceptance criteria and existing dependencies to other requirements. At last, identfy the involved input and output data and depicted an Actor-System interaction in order to fulfill the requirement.*

### 1.1. User Story Description

As an ***administrator***, I want to specify a **new type of test** and its **collecting methods**

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**
>"Once there, a receptionist asks the client’s citizen card number, the lab order (which
 **contains the type of test** and parameters to be measured)"
>
>"Despite being out of scope, the system should be developed having in mind the **need to
 easily support other kinds of tests (e.g., urine)**. Regardless, such tests rely on measuring one
 or more parameters that can be grouped/organized by categories."

**From the client clarifications:**
>Q1: Does a type of test holds any atributte besides it's name and collecting methods?
>
>A1:The attributes for a new test type are:
    Code: five alphanumeric characters. The code is not automatically generated.
    Description: a string with no more than 15 characters.
    Collecting Method: a string with no more than 20 characters.
    Each test type should have a set of categories. Each category should be chosen from a list of categories.
    From a previous post: "Each category has a name and a unique code. There are no subcategories."
>   There exists only one collection method per test type.
>
>Q2: Are the collecting methods stored simpled as a word or a sentence, or does it also must contain it's description, and/or another attributes?
>
>A2:From a previous post: "To make a Covid test you need a swab to collect a sample. To make a blood test you need sample tubes and a syringe.
    When the administrator (US9) specifies a new type of test, the administrator also specifies the method to collect a sample. The administrator introduces a brief description for specifying the collecting method. "
>   There exists only one collection method per test type.
>
>Question 1 and 2 link [here](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=7507).
>
>Q3: "In the US9 what do you mean by the collecting methods and  what collecting methods  are available??"
>
>A3: "To make a Covid test you need a swab to collect a sample. To make a blood test you need sample tubes and a syringe. 
>When the administrator (US9) specifies a new type of test, the administrator also specifies the method to collect a sample. The administrator introduces a brief description for each collecting method."
>
>Question 3 link [here](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=7752).
>
>Q4: "US9- Analog to US8 do you have any acceptance criteria you want to introduce in US9?"
>
>A4: "For now I have nothing more to say about the acceptance criteria of US9."
>
>Question 4 link [here](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=7744).


### 1.3. Acceptance Criteria

* **AC1:** The code must have 5 alphanumeric characters
* **AC2:** Description must be a string with no more than 15 characters.
* **AC3:** Collecting Method must be a string with no more than 20 characters.
* **AC4:** Each test type should have a set of categories chosen from a list of categories.
* **AC5:** Every test type must have one collecting method.

### 1.4. Found out Dependencies

There is a dependency to "US 11 - to specify a new parameter category" since the test type must be associated with at least one Category

### 1.5 Input and Output Data

* Typed data:
    * Code
    * Description
    * Collecting method
* Selected data:
    * Categories
    
### 1.6. System Sequence Diagram (SSD)

*Insert here a SSD depicting the envisioned Actor-System interactions and throughout which data is inputted and outputted to fulfill the requirement. All interactions must be numbered.*

![US9_SSD](US9_SSD.svg)


### 1.7 Other Relevant Remarks

The present US is held only a couple of times in the start of the business to address the current testing of blood and covid-19, Then it is rarely used.

## 2. OO Analysis

### 2.1. Relevant Domain Model Excerpt 
*In this section, it is suggested to present an excerpt of the domain model that is seen as relevant to fulfill this requirement.* 

![USXX-MD](US9_DM.svg)

### 2.2. Other Remarks

n/a

## 3. Design - User Story Realization 

### 3.1. Rationale

**The rationale grounds on the SSD interactions and the identified input/output data.**

| Interaction ID | Question: Which class is responsible for... | Answer  | Justification (with patterns)  |
|:-------------  |:--------------------- |:------------|:---------------------------- |
| Step 1: Asks to specify a new test type	                          |	        ... instantiating a new test type?				                        | Test Type Store             | Creator: R1/2                                        |
| Step 2: Request test type data(code, description, collectingMethod) |			n/a				                                                        |                             |                                                      |
| Step 3: Types requested data		                                  |			...saving input data?				                                    | Test Type                   | IE: The object created in step 1 has its own data.   |
| Step 4: Shows available categories and request to select one or more|			...knowing the types of test to show?                                   | Parameter Category Store    | IE: The category store knows its parameter categories|              
|                                                                     |			...Creating a parameter category dto                                    | Categories mapper           | LC: Pass a DTO to reduce coupling between layers     |              
| Step 5: Selects requested data                                      |          ...saving the input data?				                                | Test Type                   | IE: Object created in step 1 has a set of categories.|         
| Step 6: Shows test type data and requests confirmation              |			… validating the data locally (e.g.: mandatory vs. non-mandatory data)? | Test Type                   | IE: Knows its own data.                              |              
|                                                                     |          … validating the data globally (e.g.: duplicated)?				        | Test Type Store             | IE: Knows all Test Type objects.                     |              
| Step 7: Confirms data                                               |			... saving the created test type?				                        | Test Type Store             | IE: Records/adopts all the Test Type objects.        |              
| Step 8: Informs operation success                                   |			... informing operation success?				                        | UI                          | IE: responsible for user interaction                 |              

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

 * TestType
 * Company
 * ParameterCategory

Other software classes (i.e. Pure Fabrication) identified: 
 * ParameterCategoryStore
 * ParameterCategoryDto
 * ParameterCategoryMapper 
 * CreateTestTypeUI  
 * CreateTestTypeController
 * TestTypeStore

## 3.2. Sequence Diagram (SD)

*In this section, it is suggested to present an UML dynamic view stating the sequence of domain related software objects' interactions that allows to fulfill the requirement.* 

![USXX-SD](US9_SD.svg)

## 3.3. Class Diagram (CD)

*In this section, it is suggested to present an UML static view representing the main domain related software classes that are involved in fulfilling the requirement as well as and their relations, attributes and methods.*

![USXX-CD](US9_CD.svg)

# 4. Tests 
*In this section, it is suggested to systematize how the tests were designed to allow a correct measurement of requirements fulfilling.* 

**_DO NOT COPY ALL DEVELOPED TESTS HERE_**

###4.1 TestType instances values:
**Test 1:** Check that it is not possible to create an instance of the TestType class with null values. 

	@Test(expected = IllegalArgumentException.class)
		public void ensureNullIsNotAllowed() {
		Exemplo instance = new Exemplo(null, null);
	}
**Test 2:** Check if it's not possible to create a Test Type with empty field for each attribute.
**For Example:**

    @Test(expected = IllegalArgumentException.class)
    public void ensureTestTypeCodeIsNotEmpty() {
        TestType instance = new TestType("", "blood analysis", "needle", pcList);
    }

 
**Test 3:** Check if it is not possible to create a Test type with each attribute's lenght not following the specified criteria.
>* **AC1:** The code must have 5 alphanumeric characters
>* **AC2:** Description must be a string with no more than 15 characters.
>* **AC3:** Collecting Method must be a string with no more than 20 characters.

**For Example:**

    @Test(expected = IllegalArgumentException.class)
    public void ensureTestTypeCodeIsNotMoreThanFiveCharacteres() {
        TestType instance = new TestType("AAA123", "blood analysis", "needle", pcList);
    }
	
**Test 4:** Check that it is not possible to create an instance of the TestType class with code holding not alphanumeric characters.
 
    @Test(expected = IllegalArgumentException.class)
     public void ensureTestTypeCodeIsAlphanumeric() {
        TestType instance = new TestType("AA23@", "blood analysis", "needle", pcList);
     }

##4.2 TestTypeStore

**Test 10:** Check that it is not possible to save a repeated Test Type in the store
* Same Object
* Different Object, same code
**For Example:**


    @Test
    public void ensureTestTypeIsNotSavedRepeatedWithSameObject() {
        TestType t1 = company.getTestTypeStore().createTestType("AAA23", "blood analysis", "needle", pcList);
        company.getTestTypeStore().saveTestType(t1);
        boolean actual = company.getTestTypeStore().saveTestType(t1);
        assertFalse(actual);
    }

**Test 11:** Check that it is not possible to save a null Test Type in the store

    @Test
    public void ensureTestTypeIsNotSavedIfNull() {
        System.out.println("ensureTestTypeIsNotSavedIfNull");
        assertFalse(company.getTestTypeStore().saveTestType(null));
    }

**Test 12:** Check that it is not possible to get the test types by code with not assigned codes 

    @Test(expected = UnsupportedOperationException.class)
    public void ensureGetWithInvalidTestTypeCodeThrowsException() {
        System.out.println("ensureGetWithInvalidTestTypeCodeThrowsException");
        TestTypeStore testTypeStore = company.getTestTypeStore();
        TestType t1 = testTypeStore.createTestType("AAAA1", "blood analysis", "needle", pcList);
        TestType t2 = testTypeStore.createTestType("AAAA2", "blood analysis", "needle", pcList);
        testTypeStore.saveTestType(t1);
        testTypeStore.saveTestType(t2);
        List<String> codeList = new ArrayList<String>();
        codeList.add("AAAA1");
        codeList.add("AAAAA");

        testTypeStore.getTestTypesByCode(codeList);
    }

*It is also recommended to organize this content by subsections.* 

# 5. Construction (Implementation)

## Class CreateTestTypeController

    public boolean createTestType(String code, String description, String collectingMethod, List<String> selectedCategoriesCodes){
        ParameterCategoryStore parameterCategoryStore = this.company.getParameterCategoryStore();
        List<ParameterCategory> selectedCategories = parameterCategoryStore.getCategoriesByCode(selectedCategoriesCodes);
        this.testType = this.company.getTestTypeStore().createTestType(code, description, collectingMethod, selectedCategories);
        return this.company.getTestTypeStore().validateTestType(testType);
    }

    //...Omitted
    
    public boolean saveTestType(){
        return this.company.getTestTypeStore().saveTestType(testType);
    }
    
## Class TestTypeStore

    public TestType createTestType(String code, String description, String collectingMethod, List<ParameterCategory> selectedCategories){
        return new TestType(code, description, collectingMethod, selectedCategories);
    }

    //...Omitted
    
    public boolean validateTestType(TestType testType){
        if (testType == null)
            return false;
        return !this.testTypeList.contains(testType);
    }

    public boolean saveTestType(TestType testType){
        if (!validateTestType(testType))
            return false;
        return this.testTypeList.add(testType);
    }
*It is also recommended to organize this content by subsections.* 

# 6. Integration and Demo 

To create a Test type, it is necessary to know the list of parameter categories available in the system.
Therefore, in order to reduce coupling, it was created a CategoriesDTO as well as a CategoriesMapper to process the data and convert the list of parameter categories to a Dto.


# 7. Observations

*In this section, it is suggested to present a critical perspective on the developed work, pointing, for example, to other alternatives and or future related work.*





