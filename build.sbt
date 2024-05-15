name := "pcd-assignements"

version := "1.0"

scalaVersion := "3.3.3"

lazy val akkaVersion = "2.8.5"
lazy val akkaGroup = "com.typesafe.akka"
libraryDependencies ++= Seq(
  akkaGroup %% "akka-actor-typed" % akkaVersion,
  akkaGroup %% "akka-actor" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.5.6",
  akkaGroup %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.2.18" % Test,
  "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
)

