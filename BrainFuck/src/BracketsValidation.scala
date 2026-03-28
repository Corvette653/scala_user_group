package BrainFuck

import alpaca.*
import alpaca.internal.lexer.BetweenStages

trait BracketsValidation extends LexerCtx:
  this: Product =>
  var line: Int
  var position: Int
  var openBrackets: Int

object BracketsValidation:
  given BetweenStages[BracketsValidation] =
    case (_, "[", ctx) => ctx.openBrackets += 1
    case (_, "]", ctx) =>
      ctx.openBrackets -= 1
      if ctx.openBrackets < 0 then
        error(ctx.line, ctx.position, "Unmatched ']' bracket")
    case _ => ()
