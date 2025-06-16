# Reusable Java Build and Test GitHub Action

## Overview

This reusable GitHub Actions workflow is designed to **build and test Java applications** using **Maven** as the build tool. It performs all essential phases — dependency resolution, build, test, static code analysis, and artifact storage — in a unified and reusable way.

---

### Main Goals:

- Provide a reusable pipeline for Java projects.
- Support the **Maven** build system.
- Perform a clean build ignoring tests first, then execute the test suite separately.
- Validate supported Java versions and build tools.
- Install and cache project dependencies for faster builds.
- Run appropriate build and test commands:
  - **Maven:** `mvn clean install -DskipTests`, then `mvn test`
- Provide a summary of test results alongside a pass/fail report.

---

## Table of Contents

- [Features](#features)
- [Job and Step Explanation](#job-and-step-explanation)
- [How to Reuse in Main Workflow](#how-to-reuse-in-main-workflow)
- [Input Parameter Table](#input-parameter-table)
- [Workflow Directory Structure](#workflow-directory-structure)
- [Test Summary and Artifacts](#test-summary-and-artifacts)
- [Debugging and Possible Failures](#debugging-and-possible-failures)
- [Conclusion](#conclusion)

---

## Features

- Support for **Maven** build system.
- Performs **Clean Build ignoring Tests first**, then **Runs Tests separately**.
- Performs **Static Code Analysis** (Checkstyle) if configured.
- Initializes cache to reuse downloaded libraries across builds.
- Initializes Java with a specified Java version.
- Stores build and test reports as GitHub Actions artifacts.
- Allows customization through **inputs (java-version and build-tool)**.

---

## Job and Step Explanation

The reusable workflow comprises two main jobs:

---

### 1. Build Job:

- **Checkout code:** Initializes your codebase in the runner.
- **Validate build tool:** Verifies whether the specified build tool is supported.
- **Set up Java:** Initializes Java with the specified `java-version`.
- **Cache:** Caches downloaded libraries to cut down future build time.
- **Clean and Build:** Performs `mvn clean install -DskipTests`.
- **Publish Artifacts:** Stores resulting `.jar` files in GitHub Actions’ artifacts.

---

### 2. Test Job:

- **Checkout code:** Initializes code in the runner.
- **Set up Java:** Initializes Java with specified `java-version`.
- **Cache:** Restores downloaded libraries from cache.
- **Static Code Analysis:** Performs Checkstyle if configuration is present.
- **JUnit Tests:** Invokes `mvn test`.
- **Test Summary:** Parses test reports and produces a summary in GitHub Actions Summary view.
- **Publish Reports:** Stores test reports as artifacts for further inspection.

---

## How to Reuse in Main Workflow

Create a `.github/workflows/main.yaml` in your repository:

```yaml
name: Main Build and Tests

on:
  push:
    branches:
      - main
  pull_request:

jobs:
  build-and-test:
    uses: .github/workflows/reusable-java-build.yaml
    with:
      java-version: '17'
      build-tool: 'maven'
```
---

## Input Parameter Table

| Parameter      | Description                                             | Default |
| -------------- | ------------------------------------------------------- | ------- |
| `java-version` | Java version to use (e.g. 8, 11, 17)                    | 17      |
| `build-tool`   | Build tool to use (`maven`, `gradle`, `gradle-wrapper`) | `maven` |  

---

## Workflow Directory Structure

The directory structure for maven build tool ia as follows:

---

### 1. Maven Project

```
├─ .github/
│ └─ workflows/
│   ├─ main.yaml
│   └─ reusable-java-build.yaml
├─ src/
│ ├─ main/
│ └─ test/
├─ checkstyle.xml
├─ pom.xml
├─ README.md
```

---

## Test Summary and Artifacts

The workflow parses **JUnit reports** and displays a summary table directly in GitHub Actions’ Summary view:

| Test Class | Tests | Passed | Failures | Skipped |
| ---------- | ----- | ------ | -------- | ------- |

This lets you quickly view:

* Number of Tests Executed
* Number of Tests Passed or Failed
* Number of Tests Skipped

Additionally:

* The complete **JUnit reports** and **Checkstyle reports** are available under **Actions > Summary > Artifacts**.
* Download these files to view detailed information in your IDE or browser.

---

## Debugging and Possible Failures

Some common issues you may encounter:

* **Maven Wrapper script not found:**
  Ensure `mvnw` and `mvnw.cmd` exist in your directory structure.
* **Checkstyle config not found:**
  Confirm `checkstyle.xml` is present in `.github/workflows/` or `config/checkstyle/`.
* **Maven not installed:**
  The workflow already sets up Java and cache; make sure your `java-version` is supported.
* **Test Failures:**
  The summary will show:

  * Number of Tests Executed
  * Number of Tests Passed or Failed
  * Number of Tests Skipped
    Detailed reports can be downloaded from GitHub Actions’ "Artifacts".

---


## Conclusion

This reusable workflow lets you streamline your Java CI pipeline across different build tools.
It performs all essential phases — dependency resolution, build, test, static code analysis, and artifact storage — in a unified and reusable way.

*If you'd like, you can customize this workflow by adding or removing steps in `reusable-java-build.yaml`.*
