
package slickApp

import java.util.Date
import scala.slick.driver.PostgresDriver.simple.ddlToDDLInvoker
import scala.slick.driver.PostgresDriver.simple.tableQueryToTableQueryExtensionMethods
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import com.knol.core.Knol
import com.knol.core.KnolRepo
import com.knol.core.Knolx
import com.knol.core.KnolxRepo
import com.knol.db.connection.DBConnection
import scala.slick.lifted.TableQuery

class KnolRepoTest extends FunSuite with BeforeAndAfter with DBConnection with KnolRepo {

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * before is creating Knol table and inserting a single row in the 
   * table
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
  val KnolTable = TableQuery[KnolTable]
  val con = getDB().get

  before {
    con.withSession { implicit session =>
      (knolTable.ddl).create
      insert(Knol(0, "Mike", "mike@gmail.com", "9812367423"))
    }
  }

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * after is droping the table after each test case
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  after {
    con.withSession { implicit session =>
      (knolTable.ddl).drop
    }
  }

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * This test case is used to tested to check whether the insert operation over
   * Knoltable relation in the test databse is performing as required with the sample
   * entry as given in the Knol object
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  test("Test to check insert operation") {
    //pending
    val insertObj = Knol(1, "Ronny", "ronny@gmail.com", "23452")
    val output = insert(insertObj)
    assert(output === Some(1))
  }

  test("Test to check the update operation over Knol") {
    pending
    var updateObj = Knol(1, "John", "mike@gmail.com", "9999")
    assert(update(updateObj) === Some(1))
  }

  /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   * Here the test case is created to call the catch section of the code
   * so that the logger could invoked and related email can be sent over
   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
  test("Testing to check delete operation over Knol") {
    //pending
    assert(delete(999) === None)
  }

  test("Testing to check the fetching a Knol info") {
    pending
    val knol = Knol(1, "Mike", "mike@gmail.com", "3454")
    assert(getknol(1) === Some(knol))
  }
}

