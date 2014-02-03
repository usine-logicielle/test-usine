package db

import domain.{Entity, Person}
import org.squeryl.{Session, SessionFactory}
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.PrimitiveTypeMode._
import com.mysql.jdbc.Driver
import java.util.Random

/**
 * Created by rauricoste on 03/02/14.
 */
object DbAdapter {

  Class.forName("com.mysql.jdbc.Driver")
  SessionFactory.concreteFactory = Some(() =>
    Session.create(
      java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/scala", "root", "password"),
      new MySQLAdapter))

  def find[T <: Entity](id:Long): Option[Person] = {
    inTransaction {
      Library.persons.find(x => x.id == id)
    }
  }

  def putPerson(person: Person) = {
    inTransaction {
      Library.persons.insertOrUpdate(person)
    }
  }
}
