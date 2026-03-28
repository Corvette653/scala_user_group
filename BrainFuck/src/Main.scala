package BrainFuck

import java.nio.file.Path
import alpaca.internal.lexer.LazyReader
import alpaca.*

@main def run(path: String): Unit = {
  Config.filePath = path

  val fileReader = LazyReader.from(Path.of(path))
  val (final_ctx, tokens) = BrainLexer.tokenize(fileReader)

  if !final_ctx.openBrackets.isEmpty then
    error(final_ctx.line, final_ctx.position, "Unclosed bracket")

  val (_, ast) = BrainParser.parse(tokens)
  ast.eval()
}
