package db

import org.squeryl.Schema
import domain.Person
import org.squeryl.PrimitiveTypeMode._

/**
 * Created by rauricoste on 03/02/14.
 */
object Library extends Schema {
  val persons = table[Person]
  on(persons)(person => declare(
    person.firstName is(indexed),
    person.lastName is(indexed),
    columns(person.firstName, person.lastName) are(unique)
  ))
}
