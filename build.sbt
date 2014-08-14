name := "scala-fundamentals"

version := "1.0"

organization := "com.asynchrony"

scalaVersion := "2.10.4"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
 
libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % "2.3.5",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.5" % "test",
  "org.mockito" % "mockito-core" % "1.9.5" % "test",
	"org.scalatest" % "scalatest_2.10" % "2.2.1" % "test",
  "org.scalactic" % "scalactic" % "2.2.0" % "test"
)


