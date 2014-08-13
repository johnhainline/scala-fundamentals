name := "scala-fundamentals"

version := "1.0"

organization := "com.asynchrony"

scalaVersion := "2.10.4"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
 
libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % "2.4-SNAPSHOT",
	"org.scalatest" % "scalatest_2.10" % "2.0" % "test",
  "org.mockito" % "mockito-core" % "1.9.5" % "test"
)


