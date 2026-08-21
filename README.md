# E-commerce Website Automation Testing

A UI test automation framework built with **Selenium WebDriver**, **Cucumber (BDD)**,
and **TestNG**, following the **Page Object Model (POM)** design pattern. It
automates an end-to-end purchase flow on [automationexercise.com](https://automationexercise.com/) —
a public sandbox site built specifically for practicing web automation.

## What It Tests

A single, complete end-to-end scenario covering the full customer journey:

1. Land on the homepage and log in
2. Validate the homepage loaded correctly (categories, featured items)
3. Browse to Products, view a product's description page
4. Add the product to cart, confirm the "added to cart" popup
5. View cart, proceed to checkout
6. Place the order and enter payment details
7. Confirm the order was placed successfully (Congratulations page)

Written as a single Gherkin scenario in `Ecommerce.feature`, so it reads as a
plain-language spec of the purchase flow, independent of the underlying Java code.

## Tech Stack

- **Java 23**
- **Selenium WebDriver 4.46** — browser automation
- **Cucumber (Java + TestNG) 7.34** — BDD test scenarios written in Gherkin
- **TestNG** — test runner/assertions, via `AbstractTestNGCucumberTests`
- **WebDriverManager (Bonigarcia) 6.3** — automatically downloads/manages the correct
  ChromeDriver version, no manual driver setup required
- **Maven** — build and dependency management

## Project Structure

```
WebsiteAutomationTesting/
├── src/test/java/
│   ├── configs/
│   │   └── DriverFactory.java       # Creates/manages the WebDriver instance (Chrome)
│   ├── hooks/
│   │   └── Hooks.java                # @Before/@After — launches browser, navigates
│   │                                  #   to the site, quits browser after each scenario
│   ├── pages/
│   │   └── EcommercePages.java       # Page Object: all locators + page actions
│   ├── stepDefinitions/
│   │   └── Ecommerce.java            # Maps Gherkin steps to page object calls
│   ├── utils/
│   │   ├── ElementUtil.java          # Reusable wait/click/type/isDisplayed helpers
│   │   └── Constants.java            # Shared constants (e.g. site title)
│   └── runner/
│       └── Runner.java               # Cucumber-TestNG test runner + report config
├── src/test/resources/features/
│   └── Ecommerce.feature             # Gherkin scenario (the actual test spec)
└── pom.xml
```

**Design pattern:** the Page Object Model keeps element locators and page actions
(`EcommercePages.java`) separate from test logic (`Ecommerce.java`), and `ElementUtil`
centralizes explicit-wait logic (`WebDriverWait` + `ExpectedConditions`) so every
interaction waits for the element to be visible before acting on it, rather than
relying on flaky fixed sleeps.

## Prerequisites

- Java 23 (or update `maven.compiler.source`/`target` in `pom.xml` to match your JDK)
- Maven
- Google Chrome installed (WebDriverManager handles the matching ChromeDriver
  automatically — no manual driver download needed)

## Running the Tests

From the `WebsiteAutomationTesting/` directory:

```bash
mvn test
```

This launches Chrome, runs the full scenario against the live
`automationexercise.com` site, and generates reports at:

```
target/cucumber-reports/cucumber.html
target/cucumber-reports/cucumber.json
```

Open `cucumber.html` in a browser to see a readable, step-by-step pass/fail report.

## Notes

- Tests run against the **live public site**, not a local/mocked environment — so a
  test failure could reflect either a real regression or a change on the site itself.
- The login credentials used in the test (`Ecommerce.java`) are test-account details
  for the public sandbox site, not real/sensitive credentials.
- Currently configured for Chrome only; `DriverFactory` can be extended to support
  other browsers (Firefox, Edge) by adding cases to `initBrowser()`.
