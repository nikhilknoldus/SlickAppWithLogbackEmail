/*
package slickApp

import java.util.Date
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.driver.PostgresDriver.simple.ddlToDDLInvoker
import scala.slick.driver.PostgresDriver.simple.tableQueryToTableQueryExtensionMethods
import org.scalatest.FunSuite
import com.knol.core.Knolx
import com.knol.core.KnolxRepo
import com.knol.db.connection.DBConnection
import org.scalatest.BeforeAndAfter
import com.knol.core.Knol
import scala.slick.lifted.TableQuery
import com.knol.core.KnolRepo
import com.knol.core.KnolJoiningKnolx


class KnolxRepoTest extends FunSuite with BeforeAndAfter with DBConnection with KnolxRepo with KnolRepo {

  
  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * before is creating Knolx table and inserting a single row in the 
   * table
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  
  
  before {
    getDB.withSession { implicit session =>
      val knoltable = TableQuery[KnolTable]
      val knolxtable = TableQuery[KnolxTable]
      (knoltable.ddl ++ knolxtable.ddl).create
      knoltable.insert(Knol(0, "Mike", "mike@gmail.com", "3454"))
      knolxtable.insert(Knolx(0, "paperjs", new Date("02/10/2015"), 1))    
    }
  }

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * after is droping the table after each test case
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  after {
    getDB.withSession { implicit session =>
      (knolxTable.ddl ++ knolTable.ddl).drop
    }
  }
  
  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * CRUD OPERATIONS OVER Knolx relation
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  test("Test to check insert operation in Knolx") {
    val insertObj = Knolx(0, "PaperJs", new Date(), 1)
    val output = insertx(insertObj)
    assert(output === Some(1))
  }
  
    test("Test to check the update operation over Knolx") {
    //pending
    var updateObj = Knolx(1, "Angular",new Date(), 1)
    assert(updatex(updateObj) === Some(1))
  }
    
    test("Testing to check delete operation over Knolx") {
    //pending
    assert(deletex(1) === Some(1))
  }
  
    test("Testing to check the fetching a Knolx info") {
    //pending
   val knol = Knolx(1, "paperjs", new Date("02/10/2015"), 1)
    assert(getknolx(1).get === knol)
  }
    

    test("testing the joining query"){
      
    val knol1 = KnolJoiningKnolx(1, "Mike", "mike@gmail.com", "3454",1, "paperjs", new Date("02/10/2015"))
    var list = List(knol1)
    assert(joinquery(1) === list)
      
    }
    
    
}

*/
