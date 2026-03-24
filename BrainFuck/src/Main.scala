package BrainFuck

import alpaca.* 

@main def run(): Unit = {
  val input = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>."
  val (_, tokens) = BrainLexer.tokenize(input)
  val (_, ast) = BrainParser.parse(tokens)
  ast.eval()
}
