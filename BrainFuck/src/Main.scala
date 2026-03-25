package BrainFuck

import java.nio.file.Path
import alpaca.internal.lexer.LazyReader
import alpaca.*

var filePath: String = ""

@main def run(path: String): Unit = {
  filePath = path

  val fileReader = LazyReader.from(Path.of(path))
  val (final_ctx, tokens) = BrainLexer.tokenize(fileReader)

  if final_ctx.brackets != 0 then
    System.err.println(s"${filePath}:${final_ctx.line}:${final_ctx.position}: error: Mismatched ']' brackets")
    throw IllegalStateException(s"Unclosed bracket at line ${final_ctx.line}, position ${final_ctx.position}")

  val (_, ast) = BrainParser.parse(tokens)
  ast.eval()
}
