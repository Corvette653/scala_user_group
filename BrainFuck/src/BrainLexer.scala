package BrainFuck

import alpaca.*
import alpaca.internal.lexer.LineTracking
import alpaca.internal.lexer.PositionTracking

case class BrainLexerCtx(
    var openBrackets: Int = 0,
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
  case "\\[" => Token["l_bracket"]
  case "\\]" => Token["r_bracket"]
  case "\n" => Token.Ignored
  case "." => Token.Ignored
