package BrainFuck

object Config:
  var filePath: String = ""

def error(line: Int, position: Int, msg: String): Unit = 
	System.err.println(s"${Config.filePath}:${line}:${position}: error: $msg")
	throw IllegalStateException(s"$msg at line ${line}, position ${position}")