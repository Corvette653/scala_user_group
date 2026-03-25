package BrainFuck

import alpaca.*
import org.scalatest.funsuite.AnyFunSuite
import java.io.{ByteArrayOutputStream, StringReader, PrintStream}

class BrainASTTest extends AnyFunSuite {
  test("eval should execute BrainAST correctly") {
    val out = ByteArrayOutputStream()
    val ast = BrainAST.Root(List(
      BrainAST.Read,
      BrainAST.Inc,
      BrainAST.Print
    ))

    Console.withIn(StringReader("A")):
      Console.withOut(out):
        ast.eval()

    assert(out.toString == "B")
  }
}