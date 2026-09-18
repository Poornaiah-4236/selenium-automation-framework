# Selenium Automation Framework

A Selenium + Cucumber (BDD) + TestNG automation framework targeting
[saucedemo.com](https://www.saucedemo.com/), built with the Page Object
Model, data-driven Excel test data, Extent Reports, and Log4j2 logging.

## Tech Stack

- **Language:** Java 17
- **Browser automation:** Selenium WebDriver 4.43 (Selenium Manager resolves
  driver binaries automatically - no manual setup needed)
- **Test frameworks:** TestNG + Cucumber (Gherkin/BDD)
- **Build tool:** Maven
- **Reporting:** ExtentReports (`TestResult/`) and Cucumber/TestNG's own
  HTML/XML reports (`target/`, `test-output/`)
- **Logging:** Log4j2
- **CI/CD:** GitHub Actions (`.github/workflows/ci.yml`)

## Project Structure

```
src/main/java/com/
  drivermanagement/   WebDriver lifecycle (local + remote/Grid), explicit waits
  listeners/          TestNG listeners: Extent reporting, retry, annotation transform
  pages/               Page Object Model classes
  testdata/            Excel-backed test data reader
  utils/               Screenshot capture
  configuration/       Config.properties loader
src/test/java/com/
  hooks/               Cucumber @Before/@After hooks
  runner/              Cucumber-TestNG runner
  stepdefinitions/     Cucumber step definitions
  tests/               Plain TestNG tests (BaseTest, LoginTest)
src/test/resources/
  features/            Gherkin .feature files
  TestData/            Excel test data files
  log4j2-test.xml      Logging configuration
```

## Configuration

Runtime settings live in `Config.properties` (repo root). Any key can be
overridden per-run with a JVM system property, e.g. `-Dbrowser=firefox`.

| Key | Purpose | Default |
|---|---|---|
| `browser` | `chrome` / `firefox` / `edge` | `chrome` |
| `url` | Application under test | `https://www.saucedemo.com/` |
| `username` / `password` | SauceDemo's public demo credentials (not a real secret) | `standard_user` / `secret_sauce` |
| `implicitWaitSeconds` | Global implicit wait | `10` |
| `explicitWaitSeconds` | `WaitUtils` explicit wait timeout | `20` |
| `remote` | Run against a Selenium Grid hub instead of a local browser | `false` |
| `gridUrl` | Grid hub URL, used when `remote=true` | `http://localhost:4444/wd/hub` |
| `headless` | Run the browser headless | `false` |

## Running Tests

```bash
mvn test
```

This runs the full cross-browser suite defined in `testng.xml` (Chrome,
Firefox, Edge in parallel). Useful overrides:

```bash
# Single browser, headless (what CI runs)
mvn test -DsuiteXmlFile=testng-ci.xml -Dheadless=true

# Only a specific Cucumber tag
mvn test -Dcucumber.filter.tags=@Smoke

# Against a running Selenium Grid hub
mvn test -Dremote=true -DgridUrl=http://localhost:4444/wd/hub
```

## Reports

After a run, check:

- `TestResult/extent-report-*.html` - ExtentReports HTML report (pass/fail,
  screenshots on failure)
- `target/cucumber-reports.html` - Cucumber's own HTML report
- `test-output/` - TestNG's default HTML/XML report

None of these are committed to the repo (see `.gitignore`).

## CI/CD

`.github/workflows/ci.yml` runs the headless Chrome suite on every push and
pull request against `main`, and uploads the Extent/TestNG reports as build
artifacts.

## Contact

Poornaiah - poornamadipalli@gmail.com
