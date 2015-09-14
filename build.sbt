import sbt.Keys._

name := "Playground-Reactive"

version := "0.1"

scalaVersion := "2.11.7"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.10" % "2.3.13"

libraryDependencies += "com.typesafe.akka" % "akka-testkit_2.10" % "2.3.13"

libraryDependencies += "com.typesafe" % "config" % "1.3.0"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.0-M8"



