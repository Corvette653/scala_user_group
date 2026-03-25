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

  test("parse should handle brainfuck> correctly") {
    val code = "customFunc(+>+) customFunc!"
    val (_, tokens) = BrainLexer.tokenize(code)
    val (_, ast) = BrainParser.parse(tokens)

    val expectedAST = BrainAST.Root(List(
      BrainAST.FuncDef("customFunc", List(
        BrainAST.Inc,
        BrainAST.Next,
        BrainAST.Inc
      )),
      BrainAST.FuncCall("customFunc")
    ))

    assert(ast == expectedAST)
  }

  test("parse should throw on unknown function invocation") {
    val code = "unknownFunc!"
    val (_, tokens) = BrainLexer.tokenize(code)
    val exception = intercept[IllegalStateException] {
      BrainParser.parse(tokens)
    }
    assert(exception.getMessage == "Unknown function 'unknownFunc' at line 1, position 12")
  }

  test("parse should throw on function redefinition") {
    val code = "funcA() funcA()"
    val (_, tokens) = BrainLexer.tokenize(code)
    val exception = intercept[IllegalStateException] {
      BrainParser.parse(tokens)
    }
    assert(exception.getMessage == "Function redefinition 'funcA' at line 1, position 14")
  }
}
