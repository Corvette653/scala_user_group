package BrainFuck

import alpaca.*
import BrainAST.*

case class BrainParserCtx(var functions: Set[String] = Set.empty[String]) extends ParserCtx

object BrainParser extends Parser[BrainParserCtx]:
  val root: Rule[BrainAST] = rule:
    case (instruction.List(instructions)) => Root(instructions)

  val loop: Rule[BrainAST] = rule:
    case (BrainLexer.l_bracket(_), instruction.List(instructions), BrainLexer.r_bracket(_)) => Loop(instructions)

  val funcDef: Rule[BrainAST] = rule:
    case (BrainLexer.func_name(funcName), BrainLexer.l_paren(_), instruction.List(instructions), BrainLexer.r_paren(_)) => 
      if ctx.functions.contains(funcName.value) then 
        error(funcName.line, funcName.position, s"Function ${funcName.value} already defined")
      ctx.functions += funcName.value
      FuncDef(funcName.value, instructions)

  val funcCall: Rule[BrainAST] = rule:
    case (BrainLexer.func_name(funcName), BrainLexer.invocation(_)) => 
      if !ctx.functions.contains(funcName.value) then 
        error(funcName.line, funcName.position, s"Function ${funcName.value} not defined")
      FuncCall(funcName.value)

  val instruction: Rule[BrainAST] = rule(
    { case BrainLexer.next(_) => Next },
    { case BrainLexer.prev(_) => Prev },
    { case BrainLexer.inc(_) => Inc },
    { case BrainLexer.dec(_) => Dec },
    { case BrainLexer.print(_) => Print },
    { case BrainLexer.read(_) => Read },
    { case loop(loopAST) => loopAST },
    { case funcDef(funcDefAST) => funcDefAST },
    { case funcCall(funcCallAST) => funcCallAST }
  )
