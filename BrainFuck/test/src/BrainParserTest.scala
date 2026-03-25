package BrainFuck

import org.scalatest.funsuite.AnyFunSuite
import alpaca.*

class BrainParserTest extends AnyFunSuite {
  test("parse should convert tokens to AST correctly") {
    val (_, tokens) = BrainLexer.tokenize(",[>+<-]>.")
    val (_, ast) = BrainParser.parse(tokens)

    val expectedAST = BrainAST.Root(List(
      BrainAST.Read,
      BrainAST.Loop(List(
        BrainAST.Next,
        BrainAST.Inc,
        BrainAST.Prev,
        BrainAST.Dec
      )),
      BrainAST.Next,
      BrainAST.Print
    ))

    assert(ast == expectedAST)
  }
}
