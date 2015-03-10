package com.knol.core

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.ProvenShape
import scala.slick.lifted.ProvenShape
import scala.slick.lifted.ProvenShape

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.knol.db.connection.DBConnection

trait KnolRepo extends DBConnection {

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
   * getDB is a function that is defined in the DBConnection trait and
   * containing the connection details for connecting with the
   * postgresql RDBMS with Test database
   *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  val conKnol = getDB().get

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * knoltable is a table name in the test database that is created with
     * four fields id,name,email and mobile, the id is created with primary key
     * and auto increment, other fields are created with not null constraint.
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  class KnolTable(tag: Tag) extends Table[Knol](tag, "knoltable") {
    def id: Column[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name: Column[String] = column[String]("name", O.NotNull)
    def email: Column[String] = column[String]("email", O.NotNull)
    def mobile: Column[String] = column[String]("mobile", O.NotNull)
    def * = (id, name, email, mobile) <> (Knol.tupled, Knol.unapply)
  }

  val knolTable = TableQuery[KnolTable]
  val logger: Logger = LoggerFactory.getLogger(this.getClass())

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        *                           CRUD OPERATIONS FOR THE KNOL TABLE
        * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * insert function is used to insert the data in the knoltable relation  
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  def insert(knolvar: Knol): Option[Int] = {
    conKnol match {
      case conKnol => {
        try {
          conKnol.withSession { implicit session =>
            Some(knolTable.insert(knolvar))
          }
        } catch {
          case ex: Exception => {
            logger.error("Error in creating")
            None
          }
        }
      }
    }
  }

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * update operation is updating the knoltable data
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  def update(Knolvar: Knol): Option[Int] = {
    conKnol.withSession { implicit session =>
      Some(knolTable.filter(_.id === 1).update(Knolvar))
    }
  }

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *      Deleting a record from the table by filtering the id field
   *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
  def delete(knol: Int): Option[Int] = {
    conKnol match {
      case conKnol =>
        {
          try {
            conKnol.withSession {
              implicit session =>
                println("~~~~~~~coming in try block~~~~~~~~~~")
                val countDeletedRecordsVal = knolTable.filter(_.id === knol).delete
                if (countDeletedRecordsVal == 0)
                  throw new Exception
                else
                  Some(countDeletedRecordsVal)
            }
          } catch {
            case ex: Exception =>
              println("~~~~~~~could't delete the record, that id isn't exits in relation~~~~~~~~~~")
              println("~~~~~~~~~~~sending logger email~~~~~~~~~~~~~~~~")
              logger.error("The record you trying to delete isn't exists in the relation so it's error in deleting a knol")
              None
          }
        }
    }
  }

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * Selecting record from the Knoltable relation by
   * filtering the id field
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  def getknol(id: Int): Option[Knol] = {
    conKnol.withSession { implicit request =>
      val list = knolTable.filter(_.id === 1).list
      val knol = list.head
      Some(knol)
    }
  }
}
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * knol is case class having four fields
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
case class Knol(id: Int = 0, name: String, email: String, mobile: String)
