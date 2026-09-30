Added BDD UI test automation for the Classifieds web service

**Stack:** Cucumber + JUnit 5, Selenium 4.31.0, REST Assured, PicoContainer, Allure

**Covered scenarios:**
* Registration — successful sign-up, duplicate user
* Login
* Ad lifecycle — create, edit and delete an ad

**Implementation:**
* Scenarios written in Gherkin (`.feature` files)
* Page Object pattern for UI interactions
* PicoContainer for dependency injection and sharing state between step definitions
* REST Assured for test data setup and cleanup via API
* Allure reports with steps and screenshots
