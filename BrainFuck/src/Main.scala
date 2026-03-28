package BrainFuck

import alpaca.* 

@main def run(): Unit = {
  val input = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>."
  val (final_ctx, tokens) = BrainLexer.tokenize(input)

  if final_ctx.openBrackets != 0 then
    throw IllegalStateException(s"Unclosed brackets")

  val (_, ast) = BrainParser.parse(tokens)
  ast.eval()
}
