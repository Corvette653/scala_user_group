package BrainFuck

import alpaca.*
case class BrainLexerCtx(var openBrackets: Int = 0) extends LexerCtx

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
      throw IllegalStateException(s"Unmatched closing bracket")
    Token["r_bracket"]
  case "\n" => Token.Ignored
  case "." => Token.Ignored
