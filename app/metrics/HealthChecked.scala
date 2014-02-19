package metrics

import nl.grons.metrics.scala.CheckedBuilder

trait HealthChecked extends CheckedBuilder {
  val registry = MetricsRegistry.healthCheck
}
