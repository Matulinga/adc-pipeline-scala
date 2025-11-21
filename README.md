##📌 Project Overview
**ADC Pipeline** is a Maven-built Scala application designed for structured data ingestion, transformation, and enrichment using Apache Spark. 
It supports both **Databricks notebook execution** and **standalone JAR deployment**, making it ideal for cloud-native workflows and local testing.

The pipeline loads customer, product, and country datasets from JSON files, enforces schemas, registers SQL views, and performs multi-source joins to produce enriched customer intelligence.

##Project Structure
adc-pipeline-scala/
├── src/
│   ├── main/
│   │   └── scala/com/enalink/pipeline/Main.scala
│   └── test/
│       └── scala/com/enalink/pipeline/MainTest.scala
├── pom.xml
└── .vscode/settings.json

##Build Instructions
**1. Package the JAR**
mvn clean package
**2.Run Locally**
spark-submit --class com.enalink.pipeline.Main target/adc-pipeline-1.0.jar
**3. Run on Databricks**
import com.enalink.pipeline.Main
val resultDF = Main.runPipeline(spark)
resultDF.show()

**Data Scources**
-customers.json
-products.json
-countries.json

**SQL Logic**
-Customers → Country (by country_code)
-Products → Country (by origin_country_code)
-Final output includes customer info, product preferences, and regional metadata

**Testing**
-Unit tests are written using ScalaTest and include:
-Basic math and string assertions
-List membership checks
-Framework validation for pipeline readiness
-Run tests with:
mvn test




