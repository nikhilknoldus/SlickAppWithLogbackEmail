

package com.knol.core
import scala.slick.driver.PostgresDriver.simple._
import java.util.Date
import java.text.SimpleDateFormat
import scala.slick.lifted.ProvenShape
import com.knol.db.connection.DBConnection

trait KnolxRepo extends DBConnection with KnolRepo {

  val conX = getDB().get

  implicit val util2sqlDataMapper = MappedColumnType.base[java.util.Date, java.sql.Date](
    { utilDate => new java.sql.Date(utilDate.getTime()) },
    { sqlDate => new java.util.Date(sqlDate.getTime()) })

  class KnolxTable(tag: Tag) extends Table[Knolx](tag, "Knolx") {
    def id: Column[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def topic: Column[String] = column[String]("topic", O.NotNull)
    def date: Column[Date] = column[Date]("sdate", O.NotNull)
    def knolid: Column[Int] = column[Int]("knolid", O.NotNull)
    def * = (id, topic, date, knolid) <> (Knolx.tupled, Knolx.unapply)
    def fKey = foreignKey("knol_sess_fk", knolid, knolxTable)(_.id)
  }

  val knolxTable = TableQuery[KnolxTable]

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *                       CRUD OPERATIONS FOR THE KNOLX TABLE
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * insert function is used to insert the   
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  def insertx(knolvar: Knolx): Option[Int] = {
    conX.withSession { implicit session =>
      Some(knolxTable.insert(knolvar))
    }
  }
  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * update operation is updating the table data
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  def updatex(Knolvar: Knolx): Option[Int] = {
    conX.withSession { implicit session =>
      Some(knolxTable.filter(_.id === 1).update(Knolvar))
    }
  }

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * Deleting a record from the table
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  def deletex(knolx: Int): Option[Int] = {
    conX.withSession { implicit session =>
      Some(knolxTable.filter(_.id === 1).delete)
    }
  }

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * Selecting a knolx using id
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
  def getknolx(id: Int): Option[Knolx] = {
    conX.withSession { implicit request =>
      val list = knolxTable.filter(_.id === 1).list
      val knol = list.head
      Some(knol)
    }
  }

  def joinquery(bothids: Int): List[KnolJoiningKnolx] = {
    conX.withSession { implicit session =>

      val ans = knolTable innerJoin knolxTable on (_.id === _.knolid) list

      val output = ans.map {
        case (knolTable, knolxTable) => KnolJoiningKnolx(knolxTable.id, knolTable.name, knolTable.email, knolTable.mobile, knolxTable.id, knolxTable.topic, knolxTable.date)
      }
      output
    }
  }
}

case class Knolx(id: Int, topic: String, date: Date, knolid: Int)
case class KnolJoiningKnolx(id: Int, name: String, email: String, mobile: String, sid: Int, topic: String, date: Date)

