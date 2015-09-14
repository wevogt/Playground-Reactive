package de.cogent.reactive.project

import akka.actor._
import de.cogent.reactive.project.domain.ProjectMachine
import de.cogent.reactive.project.domain.ProjectProtocol._

/**
 * Created by werner on 09.09.15.
 */
object projectApp extends App {

  val system = ActorSystem("ProjectEnterprise")

  var projectActor = system.actorOf(Props[ProjectMachine])

  projectActor ! start
  projectActor ! plan
  projectActor ! build

  projectActor ! pause
  projectActor ! restart

  projectActor ! rollout

  Thread.sleep(5000)
  //system.terminate();
  system.shutdown()

}
