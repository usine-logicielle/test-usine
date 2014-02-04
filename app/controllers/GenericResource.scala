package controllers

import play.api.mvc.{Action, Controller}
import db.DbAdapter
import domain.{Person, Entity}
import converter.JsonConverter

object GenericResource extends Controller {

  def get(className: String, id: Long) = Action {
    try {
      val clazz:Class[_ <: Entity] = getClazz(className)
      Ok({
        val result = DbAdapter.find(clazz)(id)
        result match {
          case Some(x) => JsonConverter.toJson(x)
          case None => "None"
        }
      })
    } catch {
      case e: ClassNotFoundException => NotFound("class " + className + " is not supported")
    }
  }

  def put(className: String) = Action(parse.tolerantJson) {
    request =>
      val clazz:Class[_ <: Entity] = getClazz(className)
      val entity:Entity = JsonConverter.fromJson(clazz)(request.body.toString())
      DbAdapter.put(entity)
      Ok(JsonConverter.toJson(entity))
  }

  private def getClazz[T <: Entity](className: String): Class[T] = {
    val formattedClass = className.substring(0, 1).toUpperCase + className.substring(1)
    val result = Class.forName("domain." + formattedClass)
    result match {
      case a: Class[T] => a
      case _ => throw new ClassNotFoundException("class " + className + " not supported")
    }
  }

}
