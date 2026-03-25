package BrainFuck

import org.scalatest.funsuite.AnyFunSuite
import java.io.ByteArrayOutputStream
import java.nio.file.{Files, Path}
import scala.util.Using
import alpaca.internal.lexer.LazyReader
import alpaca.*

class MainTest extends AnyFunSuite {
  val newValue = "main should throw on unclosed bracket"
  test(newValue) {
    withFile(".[+") { path =>
      val exception = intercept[IllegalStateException] {
        BrainFuck.run(path.toString())
      }
      assert(exception.getMessage == "Unclosed bracket at line 1, position 4")
    }
  }

  test("main should execute hello world correctly") {
    withFile("++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.") { path =>
      val out = ByteArrayOutputStream()

      Console.withOut(out):
        BrainFuck.run(path.toString())

      assert(out.toString == "Hello World!\n")
    }
  }

  inline def withFile[A](fileContent: String)(inline action: Path => A): A =
    val tempFile = Files.createTempFile("test", ".fck")
    try
      Files.write(tempFile, fileContent.getBytes)
      action(tempFile)
    finally Files.deleteIfExists(tempFile)
}
