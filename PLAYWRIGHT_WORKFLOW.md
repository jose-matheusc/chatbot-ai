# Playwright GitHub Actions Workflow

## Overview

This GitHub Actions workflow automatically runs Playwright tests on every pull request and push to main/develop branches.

## Workflow File

- **Location:** `.github/workflows/playwright-tests.yml`
- **Trigger Events:**
  - Pull Requests (opened, synchronized, reopened)
  - Pushes to `main` and `develop` branches

## What the Workflow Does

### 1. **Repository Checkout**
   - Checks out the latest code

### 2. **Java 25 Setup**
   - Sets up JDK 25 with Temurin distribution

### 3. **Dependency Caching**
   - Caches Maven dependencies for faster builds

### 4. **Project Build**
   - Builds the project with Maven (`mvn clean install -DskipTests`)

### 5. **Playwright Browser Installation**
   - Installs Chromium, Firefox, and WebKit browsers for Playwright

### 6. **Test Execution**
   - Runs `PlaywrightTest` class
   - Runs `PlaywrightExternalAPIsTest` class

### 7. **Results Collection**
   - Uploads test artifacts (`target/surefire-reports/`)
   - Publishes test results to GitHub Actions

### 8. **PR Comments**
   - On **Success**: Posts a green comment with test summary
   - On **Failure**: Posts a red comment with failure details and link to logs

## Test Classes

### PlaywrightTest.java
- Tests JSONPlaceholder API
- Tests multiple sequential requests
- Tests REST Countries API
- Monitors network requests and responses
- Tests multiple APIs
- Measures API response times

### PlaywrightExternalAPIsTest.java
- Tests various public external APIs (PokeAPI, Dog CEO, etc.)
- Demonstrates how to test different API endpoints

## Execution Time

- **Timeout:** 30 minutes
- **Average Duration:** 3-5 minutes (depending on API response times)

## Viewing Results

### In GitHub UI
1. Go to the repository
2. Click on **Actions** tab
3. Select **Playwright Tests** workflow
4. Click on the latest run
5. View logs and artifacts

### Test Reports
- Artifacts are saved as `playwright-test-results`
- Contains JUnit XML reports in `target/surefire-reports/`

## Manual Workflow Trigger

You can manually trigger the workflow by:

```bash
# Trigger on push to develop
git push origin develop

# Or create a PR to trigger
```

## Local Testing

Run the same tests locally:

```bash
# Run all Playwright tests
mvn test -Dtest=PlaywrightTest,PlaywrightExternalAPIsTest

# Run specific test class
mvn test -Dtest=PlaywrightTest
```

## Environment Variables

No special environment variables are required. The workflow uses:
- GitHub's default runners (`ubuntu-latest`)
- Standard Maven caching
- GitHub secrets for PR comments (`${{ secrets.GITHUB_TOKEN }}`)

## Dependencies Required

The workflow assumes the following are already in `pom.xml`:
- Playwright dependency (v1.40.0 or compatible)
- JUnit Jupiter for testing
- Maven Surefire Plugin

## Troubleshooting

### Workflow Fails with "Cannot install browsers"
- Playwright browsers need x86_64 architecture
- The workflow uses `ubuntu-latest` which provides this

### Tests Timeout
- External API calls may be slow
- Timeout is set to 30 minutes (configurable in workflow)
- Consider adding individual test timeouts

### PR Comment Not Posted
- Check GitHub Actions permissions
- Ensure workflow has `pull-requests: write` permission (already set)

## Customization

To modify the workflow:

1. Edit `.github/workflows/playwright-tests.yml`
2. Change trigger events, test classes, or steps
3. Push changes to activate new workflow

### Common Customizations

**Add more test classes:**
```yaml
- name: Run Integration Tests
  run: mvn test -Dtest=MyIntegrationTest
```

**Change timeout:**
```yaml
timeout-minutes: 60  # Change from 30
```

**Add environment variables:**
```yaml
env:
  API_TIMEOUT: 10000
```

## References

- [Playwright Documentation](https://playwright.dev/java/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)
- [EnricoMi Test Report Publisher](https://github.com/EnricoMi/publish-unit-test-result-action)

