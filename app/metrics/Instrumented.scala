package metrics

import nl.grons.metrics.scala.{MetricBuilder, InstrumentedBuilder}

trait Instrumented extends InstrumentedBuilder {

  override def metrics: MetricBuilder = new MetricBuilderAdapter(getClass, metricRegistry)

  val metricRegistry = MetricsRegistry.registry
}
