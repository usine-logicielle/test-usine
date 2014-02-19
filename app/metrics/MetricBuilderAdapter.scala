package metrics

import nl.grons.metrics.scala.{Timer, MetricBuilder}
import com.codahale.metrics.MetricRegistry

class MetricBuilderAdapter(override val owner: Class[_], override val registry: MetricRegistry) extends MetricBuilder(owner, registry) {
  override def timer(name: String, scope: String): Timer = {
    val timer = super.timer(name, scope)
    val newName = MetricBuilder.metricName(owner, Seq(name))
    MetricsRegistry.timers += (newName -> timer)
    timer
  }
}
