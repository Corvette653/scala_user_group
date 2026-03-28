package BrainFuck

import alpaca.*
import alpaca.internal.lexer.LineTracking
import alpaca.internal.lexer.PositionTracking
import scala.collection.mutable.Stack

case class BrainLexerCtx(
    var openBrackets: Stack[Char] = Stack(),
    var line: Int = 1,
    var position: Int = 1
) extends LexerCtx
    with BracketsValidation
    with LineTracking
    with PositionTracking

val BrainLexer = lexer[BrainLexerCtx]:
  case ">" => Token["next"]
  case "<" => Token["prev"]
  case "\\+" => Token["inc"]
  case "-" => Token["dec"]
  case "\\." => Token["print"]
  case "," => Token["read"]
  case "!" => Token["invocation"]
  case "\\(" => Token["l_paren"]
  case "\\)" => Token["r_paren"]
  case "\\[" => Token["l_bracket"]
  case "\\]" => Token["r_bracket"]
  case name @ "[a-zA-Z_][a-zA-Z0-9_]*" => Token["func_name"](name)
  case "\n" => Token.Ignored
  case "." => Token.Ignored
