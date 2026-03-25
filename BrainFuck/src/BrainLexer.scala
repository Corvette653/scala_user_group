package BrainFuck

import alpaca.*

case class BrainLexerCtx(var brackets: Int = 0) extends LexerCtx

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
      throw IllegalStateException(s"Unmatched bracket at line ???, position ???")
    Token["r_bracket"]
  case "\n" => Token.Ignored
  case "." => Token.Ignored
