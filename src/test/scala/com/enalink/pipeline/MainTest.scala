package com.enalink.pipeline

import org.scalatest.funsuite.AnyFunSuite

class MainTest extends AnyFunSuite {

  test("Basic math sanity check") {
    assert(2 + 2 == 4)
  }

  test("String equality check") {
    val expected = "Databricks"
    val actual = "Databricks"
    assert(expected == actual)
  }

  test("List contains element") {
    val items = List("spark", "scala", "maven")
    assert(items.contains("scala"))
  }
}
