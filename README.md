# Tendable Automation Test Suite

## Overview

This project contains automated tests for the Tendable website. The tests use TestNG and Selenium WebDriver to verify the functionality of various components on the site.

## Test Cases

1. **Top-Level Menus Accessibility**: Verifies that the top-level menus ("Home," "Our Story," "Our Solution," and "Why Tendable") are accessible and that the "Request a Demo" button is present and active on each page.
2. **Contact Us Form Validation**: Navigates to the "Contact Us" section, selects "Marketing," completes the form excluding the "Message" field, and ensures that an error message is displayed on submission if the "Message" field is left empty.

## Setup

1. **Install Dependencies**:
   - Ensure you have Java Development Kit (JDK) installed.
   - Download and set up [Selenium WebDriver](https://www.selenium.dev/downloads/).
   - Add TestNG library to your project (e.g., via Maven or manually).

2. **Set Up WebDriver**:
   - Download the appropriate WebDriver executable for your browser (e.g., ChromeDriver) and specify its path in the script.
Running the Tests:

1. Compile and Run:
   - Compile the Java files and run the TestNG tests from your IDE or using Maven.

2. Test Results:
   - Review test results in the IDE or in the generated report.

3. **Bug Reporting**:

In case of failure in the contact form validation test, a bug report will be drafted manually and uploaded to the GitHub repository.

4. **Strategy**

1.**Top-Level Menus Verification**:
   - Iterate through each top-level menu, check its accessibility, and verify the presence and activity of the "Request a Demo" button.

2. **Form Validation**:
   - Navigate to the "Contact Us" section, fill out the form excluding the "Message" field, submit the form, and verify if the appropriate error message is displayed.

3. **Error Handling**:
   - If the expected error message is not displayed, draft a detailed bug report and upload it to GitHub.

