package com.knol.db.connection

import scala.slick.driver.H2Driver.simple._
import com.sun.org.apache.xerces.internal.impl.XMLDocumentFragmentScannerImpl.Driver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.spi.LoggerFactoryBinder

/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * DBConnection trait is implemented here with the required details for making
 * the connection with the postgresql and test database 
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

trait DBConnection {
val logr: Logger = LoggerFactory.getLogger(this.getClass())
    

  val url = "jdbc:postgresql://localhost:5432/test"
  val user = "postgres"
  val password = "postgres"
  val driver = "org.postgresql.Driver"

  def getDB():Option[Database] = {
    try{
  //    logr.error("--------------------------`````DB try`````------------------------------------")
   println("---------------------------------------------------------------------------------")
      Some(Database.forURL(url = url, user = user, password = password, driver = driver))
   
  }
  catch{
    case ex:Exception=>
      logr.error("--------------------------`````DB ERROR`````------------------------------------")
    None
  }
  }
  
}