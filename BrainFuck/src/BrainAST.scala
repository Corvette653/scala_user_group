package BrainFuck

enum BrainAST:
  case Root(instructions: List[BrainAST])
  case Loop(instructions: List[BrainAST])
  case Next, Prev, Inc, Dec, Print, Read
