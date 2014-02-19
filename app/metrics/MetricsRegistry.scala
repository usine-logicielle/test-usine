package metrics

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.health.HealthCheckRegistry

object MetricsRegistry {

  val registry = new MetricRegistry()
  val healthCheck = new HealthCheckRegistry()
}
