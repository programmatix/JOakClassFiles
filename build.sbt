libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

exportJars := true


lazy val JOakClassFiles = (project in file("."))
  .settings(
    name := "JOakClassFiles",
    scalaVersion := "2.12.4"
  )

