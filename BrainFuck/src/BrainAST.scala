package BrainFuck

class Memory(val underlying: Array[Int] = new Array(256), var pointer: Int = 0)

enum BrainAST:
  case Root(instructions: List[BrainAST])
  case Loop(instructions: List[BrainAST])
  case Next, Prev, Inc, Dec, Print, Read

  def eval(mem: Memory = Memory()): Unit = this match
    case BrainAST.Root(instructions) => instructions.foreach(_.eval(mem))
    case BrainAST.Next => mem.pointer += 1
    case BrainAST.Prev => mem.pointer -= 1
    case BrainAST.Inc => mem.underlying(mem.pointer) += 1
    case BrainAST.Dec => mem.underlying(mem.pointer) -= 1
    case BrainAST.Print => print(mem.underlying(mem.pointer).toChar)
    case BrainAST.Read => mem.underlying(mem.pointer) = scala.io.StdIn.readChar().toInt
    case BrainAST.Loop(instructions) =>
      while mem.underlying(mem.pointer) != 0 do
        instructions.foreach(_.eval(mem))
