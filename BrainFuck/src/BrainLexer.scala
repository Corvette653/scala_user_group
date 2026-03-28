package BrainFuck

import alpaca.*
import alpaca.internal.lexer.LineTracking
import alpaca.internal.lexer.PositionTracking

case class BrainLexerCtx(
    var openBrackets: Int = 0,
    var line: Int = 1,
    var position: Int = 1
) extends LexerCtx
    with LineTracking
    with PositionTracking

val BrainLexer = lexer[BrainLexerCtx]:
  case ">" => Token["next"]
  case "<" => Token["prev"]
  case "\\+" => Token["inc"]
  case "-" => Token["dec"]
  case "\\." => Token["print"]
  case "," => Token["read"]
  case "\\[" =>
    ctx.openBrackets += 1
    Token["l_bracket"]
  case "\\]" =>
    ctx.openBrackets -= 1
    if ctx.openBrackets < 0 then
      throw IllegalStateException(s"Unmatched closing bracket at line ${ctx.line}, position ${ctx.position}")
    Token["r_bracket"]
  case "\n" => Token.Ignored
  case "." => Token.Ignored
