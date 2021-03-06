import aether.Aether._
import com.ebiznext.sbt.plugins.SonarPlugin.sonar
import sbtrelease.ReleaseStep
import sbtrelease.ReleasePlugin.ReleaseKeys._
import sbtrelease.ReleaseStateTransformations._

organization := "fr.figarocms"

name := "test-usine"

scalacOptions ++= Seq("-feature", "-deprecation")

val h2            = "com.h2database"          % "h2"                   % "1.2.127"
val mysqlDriver   = "mysql"                   % "mysql-connector-java" %  "5.1.10"
val posgresDriver = "postgresql"              % "postgresql"           %  "8.4-701.jdbc4"
val msSqlDriver   = "net.sourceforge.jtds"    % "jtds"                 %  "1.2.4"
val derbyDriver   = "org.apache.derby"        % "derby"                %  "10.7.1.1"

libraryDependencies ++= Seq(
   mysqlDriver,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.1.3",
  "nl.grons"                     %% "metrics-scala"        % "3.0.4",
  "org.squeryl"                  %% "squeryl"              % "0.9.5-6",
  "org.scalatest"                % "scalatest_2.10"        % "2.0"      % "test"
)

play.Project.playScalaSettings

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishMavenStyle := true

pomIncludeRepository := { x => false }

crossPaths := false

initialize <<= (version) { v =>
  System.setProperty("VERSION", v)
}

lazy val dist = com.typesafe.sbt.SbtNativePackager.NativePackagerKeys.dist

lazy val publishDist = TaskKey[sbt.File]("publish-dist", "publish the dist artifact")

publish <<= (publish) dependsOn dist

publishLocal <<= (publishLocal) dependsOn dist

artifact in publishDist ~= {
    (art: Artifact) => art.copy(`type` = "zip", extension = "zip")
}

publishArtifact in (Compile, packageBin) := false

publishArtifact in (Compile, packageDoc) := false

publishArtifact in (Compile, packageSrc) := false

publishDist <<= (target in Universal, normalizedName, version) map { (targetDir, id, version) =>
  val packageName = "%s-%s" format(id, version)
  targetDir / (packageName + ".zip")
}

releaseSettings ++ addArtifact(artifact in publishDist, publishDist)

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
  sbtrelease.releaseTask(dist),
  publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)

seq(aetherSettings: _*)

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
  "sonar.cobertura.reportPath" -> "target/coverage-report/cobertura.xml",
  "sonar.sources" -> "app",
  "sonar.tests" -> "test",
  "sonar.binaries" -> "target/scct-classes",
  "sonar.verbose" -> "false"
)

org.scalastyle.sbt.ScalastylePlugin.Settings
