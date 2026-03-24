package BrainFuck

import alpaca.*
import BrainAST.*

object BrainParser extends Parser:
  val root: Rule[BrainAST] = rule:
    case (instruction.List(instructions)) => Root(instructions)

  val loop: Rule[BrainAST] = rule:
    case (BrainLexer.l_bracket(_), instruction.List(instructions), BrainLexer.r_bracket(_)) => Loop(instructions)

  val instruction: Rule[BrainAST] = rule(
    { case BrainLexer.next(_) => Next },
    { case BrainLexer.prev(_) => Prev },
    { case BrainLexer.inc(_) => Inc },
    { case BrainLexer.dec(_) => Dec },
    { case BrainLexer.print(_) => Print },
    { case BrainLexer.read(_) => Read },
    { case loop(loopAST) => loopAST }
  )
