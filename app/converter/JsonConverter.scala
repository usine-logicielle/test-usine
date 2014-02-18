package converter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import domain.Entity

object JsonConverter {

  private val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def toJson(value: Any): String = {
    mapper.writeValueAsString(value)
  }

  def fromJson[T](clazz:Class[T])(value: String) : T = {
    mapper.readValue[T](value, clazz)
  }

}
