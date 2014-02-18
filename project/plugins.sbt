// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "scala-tools-releases" at "https://oss.sonatype.org/content/groups/scala-tools/"

resolvers += "Sonatype Repository" at "https://oss.sonatype.org/content/groups/public"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.1")

addSbtPlugin("com.sqality.scct" % "sbt-scct" % "0.3")

// github project : https://github.com/ebiznext/sbt-sonar
addSbtPlugin("com.ebiznext.sbt.plugins" % "sbt-sonar" % "0.1.1")
