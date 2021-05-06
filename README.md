# Load Testing

Load testing using Gatling framework for "https://api.qa.workbook.bowery.link"


## Key Features
- Tests are written on Scala
- Built-in Gatling reports with load testing results

## Repo Layout

- bowery_load/scr/test/scala/by/aliakseimashko/gatling/simulations/ - tests simulations

## Config
- bowery_load/scr/test/resources/ - data used in tests
- bowery_load/pom.xml - file used in Scala environment configuration


## Usage

### Install/Build
Run commands in terminal IDE:
1. Install JDK
2. mvn clean

### Run Project Tests (Locally)
Run commands in terminal IDE:
1. mvn gatling:execute (run tests)
2. cd target\gatling\results\{name_of_latest_build} (move to directory with report)
3. index.html (open report)
