package BrainFuck

import alpaca.*
import alpaca.internal.lexer.LineTracking
import alpaca.internal.lexer.PositionTracking

case class BrainLexerCtx(
    var brackets: Int = 0,
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
    ctx.brackets += 1
    Token["l_bracket"]
  case "\\]" => 
    ctx.brackets -= 1
    if ctx.brackets < 0 then
      System.err.println(s"${filePath}:${ctx.line}:${ctx.position}: error: Mismatched ']' brackets")
      throw IllegalStateException(s"Unmatched bracket at line ${ctx.line}, position ${ctx.position}")
    Token["r_bracket"]
  case "\n" => Token.Ignored
  case "." => Token.Ignored
