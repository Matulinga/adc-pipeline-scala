package com.enalink.pipeline

import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.types._

object Main {

  // Notebook-safe entry point
  def runPipeline(spark: SparkSession): DataFrame = {
    println("✅ Spark session received")

    // Correct schemas
    val customerSchema = StructType(Seq(
      StructField("customer_id", StringType, nullable = true),
      StructField("name", StringType, nullable = true),
      StructField("email", StringType, nullable = true),
      StructField("country_code", StringType, nullable = true),
      StructField("favorite_product_id", StringType, nullable = true)
    ))

    val productSchema = StructType(Seq(
      StructField("product_id", StringType, nullable = true),
      StructField("product_name", StringType, nullable = true),
      StructField("category", StringType, nullable = true),
      StructField("price", DoubleType, nullable = true),
      StructField("origin_country_code", StringType, nullable = true)
    ))

    val countrySchema = StructType(Seq(
      StructField("country_code", StringType, nullable = true),
      StructField("country_name", StringType, nullable = true),
      StructField("region", StringType, nullable = true)
    ))

    // Load JSON files from DBFS
    val customerDF = loadJson(spark, "dbfs:/FileStore/tables/customers.json", customerSchema)
    val productDF  = loadJson(spark, "dbfs:/FileStore/tables/products.json", productSchema)
    val countryDF  = loadJson(spark, "dbfs:/FileStore/tables/countries.json", countrySchema)

    // Register views
    customerDF.createOrReplaceTempView("customers_intelligence_view")
    productDF.createOrReplaceTempView("products_intelligence_view")
    countryDF.createOrReplaceTempView("countries_intelligence_view")

    println("✅ Views registered: customers_intelligence_view, products_intelligence_view, countries_intelligence_view")

    // Example join: customer -> country (by country_code), product -> country (by origin_country_code)
    val resultDF = spark.sql("""
      SELECT 
        c.customer_id, c.name AS customer_name, c.email, 
        co.country_name, co.region,
        p.product_id, p.product_name, p.category, p.price
      FROM customers_intelligence_view c
      JOIN countries_intelligence_view co ON c.country_code = co.country_code
      LEFT JOIN products_intelligence_view p ON c.favorite_product_id = p.product_id
    """)

    println("✅ Sample join query executed")
    resultDF
  }

  // CLI entry point for JAR execution
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("ADC Pipeline")
      .getOrCreate()

    println("✅ Spark session started")

    val resultDF = runPipeline(spark)
    resultDF.show()

    spark.stop()
    println("✅ Spark session stopped")
  }

  // JSON loader with schema enforcement
  def loadJson(spark: SparkSession, path: String, schema: StructType): DataFrame = {
    println(s"📂 Loading JSON from: $path")
    val df = spark.read.schema(schema).json(path)
    println(s"✅ Loaded ${df.count()} rows from $path")
    df
  }
}