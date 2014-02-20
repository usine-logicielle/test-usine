package controllers

import metrics.{MetricsRegistry, HealthChecked}
import java.util.NoSuchElementException
import play.api.mvc.{Controller, Action}

object HealthCheckResource extends Controller with HealthChecked {

  final val healthCheckName = "rest"

  healthCheck(healthCheckName) {
    true
  }

  def get(name: String):Action[_] = Action {
    try {
      Ok(MetricsRegistry.healthCheck.runHealthCheck(name).isHealthy.toString)
    } catch {
      case e: NoSuchElementException => NotFound("404 : Not found")
    }
  }

  def getDefault():Action[_] = {
    get(getClass.getName.split("\\$").last + "." + healthCheckName)
  }

}
