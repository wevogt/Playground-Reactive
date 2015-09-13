package de.cogent.reactive.project.domain

import akka.actor.{FSM, Actor}
import akka.actor.Actor.Receive


object ProjectProtocol {
  case object start
  case object pause
  case object restart
  case object cancel
  case object budget
}

object ProjectMachine {
  sealed trait State
  case object InceptionState extends State
  case object PlanningState extends State
  case object BuildState extends State
  case object TransitionState extends State
  case object StalledState extends State
  case object PausedState extends State
}

class ProjectMachine extends Actor with FSM[ProjectMachine.State, Double] {

  import ProjectProtocol._
  import ProjectMachine._

  startWith(InceptionState, _)

  when(InceptionState, budget) {
    case Event(build) if budget > 0.0 => goto(PlanningState)
  }

  when(PlanningState, _) {
    case Event(build, _) => goto(BuildState)
    case Event(pause, _) => goto(PausedState)
    case Event(cancel, _) => goto(StalledState)
  }

  when(PausedState, _) {
    case Event(build, _) => goto(BuildState)

    case Event(cancel, _) => goto(StalledState)
  }
  whenUnhandled {
    //case Event(GumballCount, gumballCount) => sender() ! gumballCount; stay()
    case x => println(x); stay()
  }

  initialize()

}

/**
 * Created by werner on 13.09.15.
 */
case class Project extends Actor {

  var budget :Double = 0

  override def receive: Receive = ???
}
