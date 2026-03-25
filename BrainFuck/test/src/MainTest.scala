package BrainFuck

import org.scalatest.funsuite.AnyFunSuite
import java.io.ByteArrayOutputStream

class MainTest extends AnyFunSuite {
  test("main should throw on unclosed bracket") {
    val exception = intercept[IllegalStateException] {
      BrainFuck.run(".[+")
    }
    assert(exception.getMessage == "Unclosed bracket at line 1, position 4")
  }

  test("main should execute hello world correctly") {
    val input = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++."

    val out = ByteArrayOutputStream()

    Console.withOut(out):
      BrainFuck.run(input)

    assert(out.toString == "Hello World!\n")
  }
}
