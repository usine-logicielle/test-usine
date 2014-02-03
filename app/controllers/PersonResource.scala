package controllers

import play.api.mvc.{Action, Controller}
import db.DbAdapter
import domain.Person
import converter.JsonConverter

/**
 * Created by rauricoste on 03/02/14.
 */
object PersonResource extends Controller {

  def get = Action {
    Ok(DbAdapter.getPerson() match {
      case None => "None"
      case Some(x) => JsonConverter.toJson(x)
    })
  }

  def put = Action {
    val person = new Person("a", "b")
    DbAdapter.putPerson(person)
    Ok("done")
  }

}
