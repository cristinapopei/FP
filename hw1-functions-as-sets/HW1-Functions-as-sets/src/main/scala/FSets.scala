object FSets {

  type Set = Int => Boolean

  def member(e: Int)(s: Set): Boolean = s(e)

  def singleton(x: Int): Set = (e: Int) => e == x

  def ins(x: Int)(s: Set): Set = (e: Int) => e == x || s(e)

  def fromBounds(start: Int, stop: Int): Set = (e: Int) => e >= start && e <= stop

  def union(s1: Set, s2: Set): Set = (e: Int) => s1(e) || s2(e)

  def complement(s1: Set): Set = (e: Int) => !s1(e)

  def sumSet(b: Int)(start: Int, stop: Int)(s: Set): Int = {
    def auxSum(crt: Int, acc: Int): Int =
      if (crt > stop) acc
      else if (s(crt)) auxSum(crt + 1, acc + crt)
      else auxSum(crt + 1, acc)

    auxSum(start, b)
  }

  def foldLeftSet(b: Int)(op: (Int, Int) => Int)(start: Int, stop: Int)(s: Set): Int = {
    def aux(crt: Int, acc: Int): Int =
      if (crt > stop) acc
      else if (s(crt)) aux(crt + 1, op(acc, crt))
      else aux(crt + 1, acc)

    aux(start, b)
  }

  def foldRightSet(b: Int)(op: (Int, Int) => Int)(start: Int, stop: Int)(s: Set): Int ={
    def auxR(crt: Int): Int =
      if (crt < start) b
      else if (s(crt)) op(crt, auxR(crt-1))
      else auxR(crt - 1)

    auxR(stop)
  }

  def filter(p: Int => Boolean)(s: Set): Set = (e:Int) => s(e) && p(e)

  def partition(p: Int => Boolean)(s: Set): (Set,Set) = {
    val leftMost = filter(p)(s)
    val rightMost = filter((e:Int) => !p(e))(s)
    (leftMost,rightMost)
  }

  def forall(cond: Int => Boolean)(start: Int, stop: Int)(s: Set): Boolean = {
       def auxAll(crt:Int): Boolean =
         if(crt> stop) true
         else if(s(crt) && !cond(crt)) false
         else auxAll(crt+1)

       auxAll(start)
  }

  def exists(cond: Int => Boolean)(start: Int, stop: Int)(s: Set): Boolean =
    !forall((e:Int)=> !cond(e))(start,stop)(s)

  def setOfDivByK(k: Int): Set = (e:Int) => e%k==0

  def moreDivs(k: Int)(start: Int, stop:Int)(s1: Set, s2: Set): Boolean = {
    val ds1= foldLeftSet(0)((acc,e) => if (e%k==0) acc+1 else acc)(start, stop)(s1)
    val ds2= foldLeftSet(0)((acc,e) => if (e%k==0) acc+1 else acc)(start, stop)(s2)
    ds1>ds2
  }

}
