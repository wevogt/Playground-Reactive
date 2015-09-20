package de.cogent.reactive.project

import akka.actor._
import de.cogent.reactive.project.domain.ProjectMachine
import de.cogent.reactive.project.domain.ProjectMachineProtocol._
import de.cogent.reactive.project.domain.Project

/**
 * Created by werner on 09.09.15.
 */
object projectApp extends App {

  val system = ActorSystem("ProjectEnterprise")

  var projectActor = system.actorOf(Props[ProjectMachine])

  val myProject = Project

  //projectActor ! start
  projectActor ! plan
  projectActor ! build
  projectActor ! test
  projectActor ! bugfix
  projectActor ! test
  projectActor ! pause
  projectActor ! rollout
  projectActor ! done

//  projectActor ! pause
//  projectActor ! restart


  Thread.sleep(5000)
  system.terminate()
  //system.shutdown()

}
