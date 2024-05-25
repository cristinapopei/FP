case class Board(val board: List[List[Player]], val player: Player) {

  type Line = List[Player]

  def isFree(x: Int, y: Int): Boolean = board(x)(y) == Empty

  def getColumns: Board = new Board(board.transpose, player)

  def getFstDiag: List[Player] = board.indices.map(i => board(i)(i)).toList

  def getSndDiag: List[Player] = board.indices.map(i => board(i)(board.size - 1 - i)).toList


  def getBelowFstDiag: List[Line] = {
    (1 until board.size).map { start =>
      (0 until board.size - start).map(i => board(start + i)(i)).toList
    }.toList
  }

  def getAboveFstDiag: List[Line] = {
    val tr = board.transpose
    (1 until tr.size).map { start =>
      (0 until tr.size - start).map(i => tr(start + i)(i)).toList
    }.toList
  }

  def getBelowSndDiag: List[Line] = {
    (1 until board.size).map { start =>
      (0 until board.size - start).map(i => board(start + i)(board.size - 1 - i)).toList
    }.toList
  }

  def getAboveSndDiag: List[Line] = {
    (1 until board.size).map { start =>
      (0 until board.size - start).map(i => board(i)(board.size - 1 - i - start )).toList
    }.toList

  }

  def winner: Boolean = {
    def checkLine(line: Line): Boolean = line.sliding(5).exists(seq => seq.forall(_ == player))

    val rows = board
    val columns = board.transpose
    val fstDiag = List(getFstDiag)
    val sndDiag = List(getSndDiag)
    val aboveFstDiags = getAboveFstDiag
    val belowFstDiags = getBelowFstDiag
    val aboveSndDiags = getAboveSndDiag
    val belowSndDiags = getBelowSndDiag

    rows.exists(checkLine) || columns.exists(checkLine) ||
      fstDiag.exists(checkLine) || sndDiag.exists(checkLine) || aboveFstDiags.exists(checkLine) ||
      belowFstDiags.exists(checkLine) || aboveSndDiags.exists(checkLine) || belowSndDiags.exists(checkLine)
  }

  def update(ln: Int, col: Int): Board = {
    val updatedBoard = board.updated(ln,board(ln).updated(col,player))
    new Board(updatedBoard, player.complement)
  }

  def next: List[Board] = {
    for {
      ln <- board.indices.toList
      col <- board(ln).indices
      if isFree(ln, col)
    } yield update(ln, col)
  }

  def sequences: Map[Int,Int] = ???

  override def toString: String = {
    board.map(row => row.map {
      case One => 'X'
      case Two => '0'
      case Empty => '.'
    }.mkString).mkString("\n")
  }
}

object Board {

  def profileID:Int = 791066

  def apply(s: String, p: Player): Board = {

    def toPos(c: Char): Player =
      c match {
        case 'X' => One
        case '0' => Two
        case _ => Empty
      }

    val matrix = s.split('\n').toList.map(row => row.toList.map(toPos))
    new Board(matrix, p)
  }

  def apply(s: String): Board = {

    def toPos(c: Char): Player =
      c match {
        case 'X' => One
        case '0' => Two
        case _ => Empty
      }

    val matrix = s.split('\n').toList.map(row => row.toList.map(toPos))
    new Board(matrix, One)

  }
}
