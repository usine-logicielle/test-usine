package db

import domain.Person

/**
 * Created by rauricoste on 03/02/14.
 */
object DbAdapter {

  var person:Option[Person] = None;

  def getPerson():Option[Person] = {
    person
  }

  def putPerson(person:Person) = {
    this.person = Some(person);
  }
}
