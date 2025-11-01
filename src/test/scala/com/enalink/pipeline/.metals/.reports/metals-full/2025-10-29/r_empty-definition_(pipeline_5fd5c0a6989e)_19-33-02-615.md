error id: file:///C:/adc-pipeline/src/test/scala/com/enalink/pipeline/MainTest.scala:
file:///C:/adc-pipeline/src/test/scala/com/enalink/pipeline/MainTest.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -org/scalatest.
	 -scala/Predef.org.scalatest.
offset: 45
uri: file:///C:/adc-pipeline/src/test/scala/com/enalink/pipeline/MainTest.scala
text:
```scala
package com.enalink.pipeline

import org.sc@@alatest.funsuite.AnyFunSuite

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

```


#### Short summary: 

empty definition using pc, found symbol in pc: 