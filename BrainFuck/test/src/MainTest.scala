package BrainFuck

import org.scalatest.funsuite.AnyFunSuite

class MainTest extends AnyFunSuite {
  test("main should throw on unclosed bracket") {
    val exception = intercept[IllegalStateException] {
      BrainFuck.run(".[+")
    }
    assert(exception.getMessage == "Unclosed bracket at line 1, position 4")
  }
}
