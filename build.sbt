import aether.Aether._
import com.ebiznext.sbt.plugins.SonarPlugin.sonar
import sbtrelease.ReleaseStep
import sbtrelease.ReleasePlugin.ReleaseKeys._
import sbtrelease.ReleaseStateTransformations._
import com.typesafe.sbt.SbtNativePackager.NativePackagerKeys.dist

organization := "fr.figarocms"

name := "playApp"

scalacOptions ++= Seq("-feature", "-deprecation")

val h2            = "com.h2database"          % "h2"                   % "1.2.127"
val mysqlDriver   = "mysql"                   % "mysql-connector-java" %  "5.1.10"
val posgresDriver = "postgresql"              % "postgresql"           %  "8.4-701.jdbc4"
val msSqlDriver   = "net.sourceforge.jtds"    % "jtds"                 %  "1.2.4"
val derbyDriver   = "org.apache.derby"        % "derby"                %  "10.7.1.1"

libraryDependencies ++= Seq(
   mysqlDriver,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.1.3",
  "org.squeryl"                  %% "squeryl"              % "0.9.5-6",
  "org.scalatest"                % "scalatest_2.10"        % "2.0"      % "test"
)

releaseSettings

play.Project.playScalaSettings

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "http://mvnrepository.adencf.local/nexus/content/repositories/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "adenclassifieds-snapshots")
  else
    Some("releases"  at nexus + "adenclassifieds")
}

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
  publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
  sbtrelease.releaseTask(dist),
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)

seq(aetherSettings: _*)

aetherPublishSettings

ScctPlugin.instrumentSettings

sonar.settings

sonar.sonarProperties ++= Seq(
  "sonar.language" -> "scala",
  "sonar.host.url" -> "http://consumption-udd:9000",
  "sonar.jdbc.url" -> "jdbc:mysql://consumption-udd:3306/sonar?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true",
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

org.scalastyle.sbt.ScalastylePlugin.Settings
