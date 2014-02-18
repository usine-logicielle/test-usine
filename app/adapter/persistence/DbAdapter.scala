package adapter.persistence

import domain.Entity
import org.squeryl.{Session, SessionFactory}
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.PrimitiveTypeMode._

object DbAdapter {

  Class.forName("com.mysql.jdbc.Driver")
  SessionFactory.concreteFactory = Some(() =>
    Session.create(
      java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/scala", "root", "password"),
      new MySQLAdapter))

  def find[T <: Entity](clazz:Class[T])(id:Long): Option[T] = {
    inTransaction {
      Library.getTable[T](clazz).find(x => x.id == id)
    }
  }

  def put[T <: Entity](entity: T) = {
    inTransaction {
      if (entity.id > 0) {
        Library.getTable[T](entity.getClass).update(entity)
      } else {
        Library.getTable[T](entity.getClass).insert(entity)
      }
    }
  }
}
