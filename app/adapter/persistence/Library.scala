package adapter.persistence

import org.squeryl.{Table, Schema}
import domain.Person

/**
 * Created by rauricoste on 03/02/14.
 */
object Library extends Schema {

  def getTable[T](clazz:Class[_ <: T]): Table[T] = {
    val result = schemas.get(clazz)
    result match {
      case Some(a: Table[T]) => a
      case _ => throw new ClassCastException("impossible to find table definition for " + manifest)
    }
  }

  var schemas: Map[Any, Table[_]] = Map()

  private def addClass[T](implicit manifest:Manifest[T]) = {
    schemas = schemas + (manifest.runtimeClass -> table[T]())
  }

  addClass[Person]


  /*
  on(persons)(person => declare(
    person.firstName is(indexed),
    person.lastName is(indexed),
    columns(person.firstName, person.lastName) are(unique)
  ))
  */
}
