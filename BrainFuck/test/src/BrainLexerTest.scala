package BrainFuck

import org.scalatest.funsuite.AnyFunSuite

class BrainLexerTest extends AnyFunSuite {
  test("tokenize should parse Brainfuck code correctly") {
    val (_, result) = BrainLexer.tokenize(",[>+.<-]")
    val expectedNames = List("read", "l_bracket", "next", "inc", "print", "prev", "dec", "r_bracket")
    assert(result.map(_.name) == expectedNames)
  }

  test("tokenize should ignore whitespace in Brainfuck code") {
    val codeWithWhitespace = """
    ,[
      > + . < - 
    ]
    """
    val (_, result) = BrainLexer.tokenize(codeWithWhitespace)
    val expectedNames = List("read", "l_bracket", "next", "inc", "print", "prev", "dec", "r_bracket")
    assert(result.map(_.name) == expectedNames)
  }
}
