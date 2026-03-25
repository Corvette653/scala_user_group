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
        System.err.println(s"${filePath}:${funcName.line}:${funcName.position}: error: Function redefinition '${funcName.value}'")
        throw IllegalStateException(s"Function redefinition '${funcName.value}' at line ${funcName.line}, position ${funcName.position}")
      else
        ctx.functions += funcName.value
      FuncDef(funcName.value, instructions)

  val funcCall: Rule[BrainAST] = rule:
    case (BrainLexer.func_name(funcName), BrainLexer.invocation(_)) => 
      if !ctx.functions.contains(funcName.value) then
        System.err.println(s"${filePath}:${funcName.line}:${funcName.position}: error: Unknown function '${funcName.value}'")
        throw IllegalStateException(s"Unknown function '${funcName.value}' at line ${funcName.line}, position ${funcName.position}")
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
