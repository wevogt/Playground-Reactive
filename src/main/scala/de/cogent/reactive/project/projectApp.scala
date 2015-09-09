package de.cogent.reactive.project

import akka.actor._
/**
 * Created by werner on 09.09.15.
 */
object projectApp extends App {

  val system = ActorSystem("ProjectEnterprise")


  Thread.sleep(5000)
  system.shutdown()

}
