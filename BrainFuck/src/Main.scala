package BrainFuck

@main def run(input: String): Unit = {
  val (final_ctx, _tokens) = BrainLexer.tokenize(input)
  if final_ctx.brackets != 0 then
    throw IllegalStateException(s"Unclosed bracket at line ${final_ctx.line}, position ${final_ctx.position}")
}
