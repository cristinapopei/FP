abstract class WTree extends WTreeInterface {
  override def filter(pred: Token => Boolean): WTree = filterAux(pred,Empty)
  def filterAux(pred: Token => Boolean, acc: WTree): WTree

}

case object Empty extends WTree {
  override def balance: Int = 0
  override def height: Int = 0
  override def rotateLeft: WTree = this
  override def rotateRight: WTree = this
  override def rotateRightLeft: WTree = this
  override def rotateLeftRight: WTree = this
  override def rebalance: WTree = this

  override def isEmpty = true
  override def ins(w: Token): WTree = Node(w, Empty, Empty)
  override def filterAux(pred: Token => Boolean, acc: WTree): WTree = acc
  override def size: Int = 0
  override def contains(s: String): Boolean = false

}

case class Node(word: Token, left: WTree, right: WTree) extends WTree {

  override def balance: Int = right.height - left.height
  override def height: Int = 1 + (left.height max right.height)
  override def rotateLeft: WTree =
    right match {
      // the tree is unbalanced, hence the right sub-tree is nonempty
      case Node(w, l, r) => Node(w, Node(word, left, l), r)
    }
  override def rotateRight: WTree =
    left match {
      case Node(w, l, r) => Node(w, l, Node(word, r, right))
    }
  override def rotateRightLeft: WTree =
    Node(word, left, right.rotateRight).rotateLeft
  override def rotateLeftRight: WTree =
    Node(word, left.rotateLeft, right).rotateRight
  override def rebalance: WTree = {
    if (balance < -1 && left.balance == -1) this.rotateRight
    else if (balance > 1 && right.balance == 1) this.rotateLeft
    else if (balance < -1 && left.balance == 1) this.rotateLeftRight
    else if (balance > 1 && right.balance == -1) this.rotateRightLeft
    else this

  }

  override def isEmpty = false

  override def ins(w: Token): WTree =
    if (w.freq > word.freq) Node(word, left, right.ins(w))
    else Node(word, left.ins(w), right)

  override def contains(s: String): Boolean = {
    def traverse(tree: WTree): Boolean ={
      tree match{
        case Empty => false
        case Node(word,left,right) =>
          if (word.word==s) true
          else {traverse(left) || traverse(right)}
      }
    }
    traverse(this)
  }

  override def size: Int= 1+left.size + right.size

  def filterAux(pred: Token => Boolean, acc: WTree): WTree = {
    val newAcc= if(pred(word)) acc.ins(word) else acc
    val nextLeft = left.filterAux(pred, newAcc)
    right.filterAux(pred, nextLeft)
  }
}


object Main {

  val scalaDescription: String = "Scala is a strong statically typed general-purpose programming language which supports both object-oriented programming and functional programming designed to be concise many of Scala s design decisions are aimed to address criticisms of Java Scala source code can be compiled to Java bytecode and run on a Java virtual machine. Scala provides language interoperability with Java so that libraries written in either language may be referenced directly in Scala or Java code like Java, Scala is object-oriented, and uses a syntax termed curly-brace which is similar to the language C since Scala 3 there is also an option to use the off-side rule to structure blocks and its use is advised martin odersky has said that this turned out to be the most productive change introduced in Scala 3 unlike Java, Scala has many features of functional programming languages like Scheme, Standard ML, and Haskell, including currying, immutability, lazy evaluation, and pattern matching it also has an advanced type system supporting algebraic data types, covariance and contravariance, higher-order types (but not higher-rank types), and anonymous types other features of Scala not present in Java include operator overloading optional parameters named parameters and raw strings conversely a feature of Java not in Scala is checked exceptions which has proved controversial"

  /* Split the text into chunks */
  def split(text: List[Char]): List[List[Char]] = {
    def aux(chars: List[Char], crt: List[Char], res:List[List[Char]]): List[List[Char]] = {
      chars match {
        case Nil =>
         if(crt.isEmpty) res
         else res :+crt

        case head :: tail =>
          if(head.isWhitespace){
            if(crt.isEmpty) aux(tail,crt,res) //multiple whitespaces case
            else aux(tail,Nil,res :+ crt) //end current word
          }
          else
            aux(tail, crt :+ head, res) //build crt word

    }
    }
    // if we have only void chunks, we return the empty list
    val l = aux(text, Nil, Nil)
    if (l == List(Nil)) Nil
    else l
  }



  /* compute the frequency of each chunk */
  def computeTokens(words: List[String]): List[Token] = {
    /* insert a new string in a list of tokens */
    def insWord(s: String, acc: List[Token]): List[Token] = {
      acc.find(_.word ==s) match {
        case Some(existingToken) =>
          acc.map(t=> if (t.word ==s) Token(s,t.freq+1) else t)
        case None =>
          acc :+ Token(s,1)
      }
    }
    /* tail-recursive implementation of the list of tokens */
    def aux(rest: List[String], acc: List[Token]): List[Token] = {
      rest match {
        case Nil => acc
        case head :: tail =>
          aux(tail, insWord(head,acc))
      }
    }
    aux(words,Nil)

  }

  def tokensToTree(tokens: List[Token]): WTree = {
    val newTree: WTree = Empty
    tokens.foldLeft(newTree) { (tree, token) =>
      tree.ins(token)
    }
  }

  /* Using the previous function, which builds a tree from a list of tokens,
  *  write a function which takes a string,
  *  splits it into chunks, computes frequencies and constructs a tree.
  *  Use the function _.toList to construct a list of characters from a String.
  *
  *  A much cleaner implementation can be achieved by "sequencing" functions using
  *  andThen.
  * */

  def makeTree(s:String): WTree = {
    val st1=(s:String)=>s.toList
    val st2=(chars: List[Char])=> split(chars)
    val st3=(charLists: List[List[Char]]) => charLists.map(_.mkString)
    val st4=(words: List[String]) => computeTokens(words)
    val st5=(tokens: List[Token]) => tokensToTree(tokens)

  val seq = st1
    .andThen(st2)
    .andThen(st3)
    .andThen(st4)
    .andThen(st5)

    seq(s)
  }

  /* build a tree with the words and frequencies from the text in the scalaDescription text */
  def wordSet: WTree = makeTree(scalaDescription)

  /* find the number of occurrences of the keyword "Scala" in the scalaDescription text */
  def scalaFreq: Int = {
    val tree=wordSet
    def countToken(tree: WTree, target:String): Option[Token]={
      tree match{
        case Empty => None
        case Node(token,left,right) =>
          if(token.word==target) Some(token)
          else{
            val leftres= countToken(left,target)
            val rightres= countToken(right,target)
            if(leftres!= None) leftres
            else if(rightres!=None) rightres
            else None
          }
      }
    }
    countToken(tree,"Scala") match{
      case Some(token)=> token.freq
      case None => 0
    }
  }

  /* find how many programming languages are referenced in the text.
     A PL is a keyword which starts with an uppercase
     You can reference a character from a string using (0) and you can
     also use the function isUpper

  */

  def progLang: Int = {
   val tree=wordSet
    def countPL(tree:WTree): Int={
      tree match{
        case Empty =>0
        case Node(token, left,right) =>
          val isPl=token.word.headOption.exists(_.isUpper)

          val leftCount=countPL(left)
          val rightCount=countPL(right)

          if (isPl) 1+leftCount+rightCount
          else leftCount+rightCount
      }
    }
    countPL(tree)
  }

  /* find how many words which are not prepositions or conjunctions appear in the text (any word whose size is larger than 3). */

  def wordCount : Int = {
   val words=split(scalaDescription.toList).map(_.mkString)

    val longWords=words.filter(_.length >3)

    longWords.size

  }

}

