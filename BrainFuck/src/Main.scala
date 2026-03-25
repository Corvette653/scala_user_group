package BrainFuck

import alpaca.*

@main def run(input: String): Unit = {
  val (final_ctx, tokens) = BrainLexer.tokenize(input)
  if final_ctx.brackets != 0 then
    throw IllegalStateException(s"Unclosed bracket at line ${final_ctx.line}, position ${final_ctx.position}")

  val (_, ast) = BrainParser.parse(tokens)
  ast.eval()
}
