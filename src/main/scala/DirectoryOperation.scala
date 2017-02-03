import java.io.{File, FileNotFoundException, Serializable}
import java.nio.file.{Files, Paths}

import scala.annotation.tailrec
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by jatin on 2/2/17.
  */
@throws(classOf[FileNotFoundException])
object DirectoryOperation {
  private def dirExist(path:String):Boolean= if(Files.exists(Paths.get(path))) true else false
  private def getAllFiles(path:String): List[File] =
  {
      val filesHere = (new java.io.File(path)).listFiles
      val outputfile= for (file <- filesHere if file.getName.endsWith("")) yield(file)
      outputfile.toList
  }

  def getAllFileRecursively(path:String):Future[List[String]]={
    if(dirExist(path)) {
      Future {

        def filesInFolder(file: File): List[String] = {
          val filelist = getAllFiles(file.toString)
          val allfiles = filelist partition (_.isDirectory)
          val output = allfiles._2.map(_.toString) ::: allfiles._1.map(filesInFolder(_)).flatten
          output
        }
        filesInFolder(new File(path))
      }
    }
    else {
      Future.failed(throw new FileNotFoundException)
    }
  }
}
