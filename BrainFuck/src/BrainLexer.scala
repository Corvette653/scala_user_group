package BrainFuck

import alpaca.*
import alpaca.internal.lexer.LineTracking
import alpaca.internal.lexer.PositionTracking
import scala.collection.mutable.Stack

case class BrainLexerCtx(
    var brackets: Stack[Char] = Stack.empty[Char],
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
  case "!" => Token["invocation"]
  case "\\(" => 
    ctx.brackets.push(')')
    Token["l_paren"]
  case "\\)" => 
    validateBracket(ctx, ')')
    Token["r_paren"]
  case "\\[" =>
    ctx.brackets.push(']')
    Token["l_bracket"]
  case "\\]" =>
    validateBracket(ctx, ']')
    Token["r_bracket"]
  case name @ "[a-zA-Z_][a-zA-Z0-9_]*" => Token["func_name"](name)
  case "\n" => Token.Ignored
  case "." => Token.Ignored

def validateBracket(ctx: BrainLexerCtx, expected: Char): Unit =
  if ctx.brackets.isEmpty then
    System.err.println(s"${filePath}:${ctx.line}:${ctx.position}: error: Mismatched '$expected' brackets")
    throw IllegalStateException(s"Unmatched bracket at line ${ctx.line}, position ${ctx.position}")
  else if ctx.brackets.pop() != expected then
    System.err.println(s"${filePath}:${ctx.line}:${ctx.position}: error: Mismatched '$expected' brackets")
    throw IllegalStateException(s"Unmatched bracket at line ${ctx.line}, position ${ctx.position}")
