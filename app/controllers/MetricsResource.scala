package controllers

import play.api.mvc.{Action, Controller}
import metrics.Instrumented
import scala.util.parsing.json.JSONObject
import nl.grons.metrics.scala.Timer

object MetricsResource extends Controller with Instrumented {

  def get(name: String) = Action {
    Ok(convert(GenericResource.getterMetrics).toString())
  }

  def convert(timer:Timer):JSONObject = {
    new JSONObject(Map(
      "mean" -> Math.round(timer.mean),
      "min" -> Math.round(timer.min),
      "max" -> Math.round(timer.max),
      "1min" -> timer.oneMinuteRate,
      "5min" -> timer.fiveMinuteRate,
      "15min" -> timer.fifteenMinuteRate
    ))
  }
}
