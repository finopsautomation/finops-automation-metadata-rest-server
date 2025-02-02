# finops-automation-metadata-rest-server
FinOps Automation - Metadata REST Server

A simple REST solution for tracking provider accounts, portfolios and contact details.

[![CI/CD](https://github.com/finopsautomation/finops-automation-metadata-rest-server/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/finopsautomation/finops-automation-metadata-rest-server/actions/workflows/maven.yml)

## Deployment:

Download the latest release package and execute the JAR file.

## Data attributes

```mermaid
erDiagram
    AccountDefinition {
        String AccountID PK "Native provider account identifier"
        String ProviderType PK "Provider of account"
        String BillingAccountID "Identifier of billing account"
        String AccountName "Name of account"
        String FriendlyAccountName "Human friendly name assigned to account"
        String LinkedAccountID "Optional linked account identifier"
        Date ProvisionDate "When was the account created or provisioned?"
        Date DecommissionDate "When was the account disabled?"
        String AccountNotes "Ad-hoc notes for the account"
        String TechnicalContactName "Technical Contact Name"
        String TechnicalContactEmailAddress "Technical Email Address"
        String BusinessContactName "Business Contact Name"
        String BusinessContactEmailAddress "Business Email Address"
    }

    PortfolioDefinition {
        String PortfolioID PK "Internal Identifier for the portfolio"
        String PortfolioName "Public name of the portfolio"
        String PortfolioNotes "Ad-hoc notes for the portfolio"
        Date StartDate "When was the portfolio authorized for use?"
        Date EndDate "When was the portfolio no longer authorized for use?"
        String TechnicalContactName "Technical Contact Name"
        String TechnicalContactEmailAddress "Technical Email Address"
        String BusinessContactName "Business Contact Name"
        String BusinessContactEmailAddress "Business Email Address"
    }

```

## Core Technology Stack

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)

| Technology | Description | Use |
| -- | -- | -- |
| [finops-automation-metadata-services](https://github.com/finopsautomation/finops-automation-metadata-services) | FinOps Automation Metadata Services | Compile |
| [finops-automation-metadata-repository-aws](https://github.com/finopsautomation/finops-automation-metadata-repository-aws) | Storage of metadata within AWS DynamoDB | Compile |

## Building from Source

finops-automation-metadata-rest-server uses a [Apache Maven](https://maven.apache.org/)-based build system.

In the instructions below, `mvnw` is invoked from the root of the source tree and serves as
a cross-platform, self-contained bootstrap mechanism for the build.

### Prerequisites
[Git](https://help.github.com/set-up-git-redirect) and [JDK 17](https://www.oracle.com/technetwork/java/javase/downloads).

Be sure that your `JAVA_HOME` environment variable points to the `jdk-17` folder extracted from the JDK download.

### Check out sources

```
git clone https://github.com/finopsautomation/finops-automation-metadata-rest-server.git
```

### Compile and run unit tests; build all jars, distribution zips, and docs

Linux
```
./build.sh
```

Windows
```
build
```

