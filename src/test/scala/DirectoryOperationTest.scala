import java.io.FileNotFoundException

import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._
/**
  * Created by jatin on 3/2/17.
  */

class DirectoryOperationTest extends FunSuite{
  test("should return empty list") {
    val res = DirectoryOperation.getAllFileRecursively("/home/knoldus/Desktop/TestSuit/Test1")
    val actual = Await.result(res, 10.seconds).length
    assert(actual == 0)
  }
  test("should throw file not found exception") {

      intercept[FileNotFoundException]{
        val res1 = DirectoryOperation.getAllFileRecursively("/home/knoldus/Desktop/notADirectory")
      }
  }
  test("should have only files no directory") {
    val res2 = DirectoryOperation.getAllFileRecursively("/home/knoldus/Desktop/TestSuit/Test2")
    val actual2 = Await.result(res2, 10.seconds).length
    assert(actual2 == 1)
  }
  test("should return multiple files") {
    val res3 = DirectoryOperation.getAllFileRecursively("/home/knoldus/Desktop/TestSuit/Test3")
    val actual3 = Await.result(res3, 10.seconds).length
    assert(actual3 == 2)
  }

}
