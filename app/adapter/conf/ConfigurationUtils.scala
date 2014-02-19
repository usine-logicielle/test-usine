package adapter.conf

import play.api.Play

object ConfigurationUtils {

  implicit val app = Play.current

  def getString(name: String): String = {


    Play.application.configuration.getString(name) match {
      case Some(x: String) => x
      case _ => throw new IllegalStateException(name + " property is not defined")
    }
  }
}
