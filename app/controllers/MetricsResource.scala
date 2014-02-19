package controllers

import play.api.mvc.{Action, Controller}
import metrics.{MetricsRegistry, Instrumented}
import scala.util.parsing.json.JSONObject
import nl.grons.metrics.scala.Timer

object MetricsResource extends Controller with Instrumented {

  def getTimer(name: String) = Action {
    MetricsRegistry.timers.get(name) match {
      case Some(timer:Timer) => Ok(convert(timer))
      case _ => NotFound("404 : Not found")
    }
  }

  def convert(timer: Timer): String = {
    new JSONObject(Map(
      "mean" -> Math.round(timer.mean),
      "min" -> Math.round(timer.min),
      "max" -> Math.round(timer.max),
      "1min" -> timer.oneMinuteRate,
      "5min" -> timer.fiveMinuteRate,
      "15min" -> timer.fifteenMinuteRate
    )).toString()
  }
}
