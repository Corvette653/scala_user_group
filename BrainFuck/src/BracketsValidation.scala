package BrainFuck

import alpaca.*
import alpaca.internal.lexer.BetweenStages
import scala.collection.mutable.Stack

trait BracketsValidation extends LexerCtx:
  this: Product =>
  var line: Int
  var position: Int
  var openBrackets: Stack[Char]

object BracketsValidation:
  given BetweenStages[BracketsValidation] =
    case (_, "[", ctx) => ctx.openBrackets.push('[')
    case (_, "(", ctx) => ctx.openBrackets.push('(')
    case (_, "]", ctx) =>
      if ctx.openBrackets.isEmpty || ctx.openBrackets.top != '[' then
        error(ctx.line, ctx.position, "Unmatched ']' bracket")
      ctx.openBrackets.pop()
    case (_, ")", ctx) =>
      if ctx.openBrackets.isEmpty || ctx.openBrackets.top != '(' then
        error(ctx.line, ctx.position, "Unmatched ')' bracket")
      ctx.openBrackets.pop()
    case _ => ()
