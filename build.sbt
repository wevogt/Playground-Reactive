import sbt.Keys._

name := "Playground-Reactive"

version := "0.1"

scalaVersion := "2.11.7"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.scala-lang" % "scala-library" % "2.11.7"
//libraryDependencies += "org.scala-lang" % "scala-library" % "2.12.0-M2"

//libraryDependencies += "com.typesafe.akka" % "akka-actor_2.10" % "2.3.13"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.1"

//libraryDependencies += "com.typesafe.akka" % "akka-testkit_2.10" % "2.3.14"
libraryDependencies += "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.1"

libraryDependencies += "com.typesafe" % "config" % "1.3.0"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.0-M14"



