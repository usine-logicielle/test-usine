package controllers

import play.api.mvc.{Action, Controller}
import domain.Entity
import converter.JsonConverter
import adapter.persistence.DbAdapter
import adapter.rest.EmptyEntity
import metrics.Instrumented

object GenericResource extends Controller with Instrumented {

  val getterMetrics = metrics.timer("getter")

  def get(className: String, id: Long) = getterMetrics.time(Action {
    try {
      val clazz: Class[_ <: Entity] = getClazz(className)
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
  })

  def put(className: String) = Action(parse.tolerantJson) {
    request =>
      val clazz: Class[_ <: Entity] = getClazz(className)
      val entity: Entity = JsonConverter.fromJson(clazz)(request.body.toString())
      DbAdapter.put(entity)
      val empty = new EmptyEntity(entity.id)
      Ok(JsonConverter.toJson(empty))
  }

  private[controllers] def getClazz[T <: Entity](className: String): Class[T] = {
    val formattedClass = className.substring(0, 1).toUpperCase + className.substring(1)
    val result = Class.forName("domain." + formattedClass)
    result match {
      case a: Class[T] => a
      case _ => throw new ClassNotFoundException("class " + className + " not supported")
    }
  }

}
