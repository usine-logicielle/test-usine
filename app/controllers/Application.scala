package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    request =>
      val result = request.session.get("login") match {
        case None => "no login"
        case Some(x) => "login "+x
      }
    Ok(result).withSession("login" -> "remy")

    //Ok(views.html.index("Your new application is ready.")).withSession("login" -> "remy")
  }

}