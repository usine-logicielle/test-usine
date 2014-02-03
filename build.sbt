name := "playApp"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.1.3",
  "org.squeryl" %% "squeryl" % "0.9.5-6",
  jdbc,
  anorm,
  cache
)

play.Project.playScalaSettings
