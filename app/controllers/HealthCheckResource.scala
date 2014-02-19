package controllers

import metrics.HealthChecked
import java.util.NoSuchElementException
import play.api.mvc.{Controller, Action}

object HealthCheckResource extends Controller with HealthChecked {

  healthCheck("rest") {
    true
  }

  def get(name: String) = Action {
    try {
      Ok(registry.runHealthCheck(name).isHealthy.toString)
    } catch {
      case e: NoSuchElementException => NotFound("404 : Not found")
    }
  }

}
