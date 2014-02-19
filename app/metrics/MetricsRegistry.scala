package metrics

import com.codahale.metrics.health.HealthCheckRegistry
import com.codahale.metrics.MetricRegistry
import nl.grons.metrics.scala.Timer

object MetricsRegistry {

  val registry = new MetricRegistry()
  val healthCheck = new HealthCheckRegistry()

  var timers = Map[String, Timer]()
}
