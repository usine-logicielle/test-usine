package adapter.persistence

import domain.Entity
import org.squeryl.{Session, SessionFactory}
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.PrimitiveTypeMode._
import adapter.conf.ConfigurationUtils
import org.squeryl.internals.DatabaseAdapter
import java.sql.DriverManager

object DbAdapter {

  private class Datasource(val dsName: String) {
    def initConcreteFactory(adapter: DatabaseAdapter) = {
      val driverName = ConfigurationUtils.getString("db." + dsName + ".driver")
      val url = ConfigurationUtils.getString("db." + dsName + ".url")
      val login = ConfigurationUtils.getString("db." + dsName + ".user")
      val password = ConfigurationUtils.getString("db." + dsName + ".password")
      Class.forName(driverName)

      SessionFactory.concreteFactory = Some(() => Session.create(DriverManager.getConnection(url, login, password), adapter))
    }
  }

  new Datasource("default").initConcreteFactory(new MySQLAdapter)

  def find[T <: Entity](clazz: Class[T])(id: Long): Option[T] = {
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
