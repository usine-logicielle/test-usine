import com.ebiznext.sbt.plugins.SonarPlugin.sonar

name := "playApp"

version := "1.0-SNAPSHOT"

val h2 = "com.h2database" % "h2" % "1.2.127"
val mysqlDriver = "mysql" % "mysql-connector-java" % "5.1.10"
val posgresDriver = "postgresql" % "postgresql" % "8.4-701.jdbc4"
val msSqlDriver = "net.sourceforge.jtds" % "jtds" % "1.2.4"
val derbyDriver = "org.apache.derby" % "derby" % "10.7.1.1"

libraryDependencies ++= Seq(
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.1.3",
  "org.squeryl" %% "squeryl" % "0.9.5-6",
  mysqlDriver,
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test"
)

play.Project.playScalaSettings

// launch scct with command "sbt scct:test"
ScctPlugin.instrumentSettings

sonar.settings

// launch sonar with "sbt sonarPublish"
sonar.sonarProperties ++= Seq(
  "sonar.language" -> "scala",
  "sonar.host.url" -> "http://localhost:9081",
  "sonar.jdbc.url" -> "jdbc:mysql://localhost:9998/sonar?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true",
  "sonar.jdbc.driverClassName" -> "com.mysql.jdbc.Driver",
  "sonar.jdbc.username" -> "sonar",
  "sonar.jdbc.password" -> "sonar",
  "sonar.core.codeCoveragePlugin" -> "cobertura",
  "sonar.java.coveragePlugin" -> "cobertura",
  "sonar.dynamicAnalysis" -> "reuseReports",
  "sonar.surefire.reportsPath" -> "target/test-reports",
  "sonar.cobertura.reportPath" -> "target/scala-2.10/coverage-report/cobertura.xml",
  "sonar.sources" -> "app",
  "sonar.tests" -> "test",
  "sonar.binaries" -> "target/scala-2.10/scct-classes",
  "sonar.verbose" -> "false"
)
