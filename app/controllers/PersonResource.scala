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

  def put = Action(parse.tolerantJson) { request =>
    val person:Person = JsonConverter.fromJson(request.body.toString(), classOf[Person])
    DbAdapter.putPerson(person)
    Ok(JsonConverter.toJson(person))
  }

}
