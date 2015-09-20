package de.cogent.reactive.project.domain

import akka.actor.{ Actor, ActorSystem, Props, FSM }


object ProjectMachineProtocol {
  case object init
  case object start
  case object plan
  case object build
  case object pause
  case object continue
  case object restart
  case object cancel
  case object test
  case object bugfix
  case object rollout
  case object budget
  case object done
}

object ProjectMachine {
  sealed trait State
  case object InceptionState extends State
  case object PlanningState extends State
  case object BuildState extends State
  case object TestState extends State
  case object BugfixState extends State
  case object TransitionState extends State
  case object PausedState extends State
  case object StalledState extends State
  case object ClosedState extends State
}

class ProjectMachine extends Actor with FSM[ProjectMachine.State, Project] {

  import ProjectMachine._

  startWith(InceptionState, Project("myProject"))

  when(InceptionState) {
    case Event(ProjectMachineProtocol.start, _) => goto(PlanningState)
    case Event(ProjectMachineProtocol.plan, _)  => goto(PlanningState)
  }

  when(PlanningState) {
    case Event(ProjectMachineProtocol.build, _) => goto(BuildState)
    case Event(ProjectMachineProtocol.done, _) => goto(BuildState)
    case Event(ProjectMachineProtocol.pause, _) => goto(PausedState)
    case Event(ProjectMachineProtocol.cancel, _) => goto(StalledState)
  }

  when(BuildState) {
    case Event(ProjectMachineProtocol.test, _) => goto(TestState)
    case Event(ProjectMachineProtocol.done, _) => goto(TestState)
    case Event(ProjectMachineProtocol.pause, _) => goto(PausedState)
    case Event(ProjectMachineProtocol.done, _) => goto(TestState)
  }

  when(TestState) {
    case Event(ProjectMachineProtocol.bugfix, _ ) => goto(BugfixState)
    case Event(ProjectMachineProtocol.rollout, _) => goto(TransitionState)
    case Event(ProjectMachineProtocol.done, _) => goto(TransitionState)
    case Event(ProjectMachineProtocol.pause, _) => goto(PausedState)
  }

  when (BugfixState) {
    case Event(ProjectMachineProtocol.test, _) => goto(TestState)
    case Event(ProjectMachineProtocol.done, _) => goto(TestState)
    case Event(ProjectMachineProtocol.pause, _) => goto(PausedState)
    case Event(ProjectMachineProtocol.continue, _) => goto(BugfixState)
  }

  when(TransitionState) {
    case Event(ProjectMachineProtocol.done, _) => goto(ClosedState)
  }

  when(ClosedState) {
    case Event(ProjectMachineProtocol.restart, _) => goto(InceptionState)
  }

  when(PausedState) {
    case Event(ProjectMachineProtocol.build, _) => goto(BuildState)
    case Event(ProjectMachineProtocol.rollout, _) => goto(TransitionState)
    case Event(ProjectMachineProtocol.cancel, _) => goto(StalledState)
    //case Event(continue, _) => goto(pre)
  }

  when(StalledState) {
    case Event(ProjectMachineProtocol.restart, _) => goto(InceptionState)
    case Event(ProjectMachineProtocol.cancel, _) => goto(StalledState)
  }

  whenUnhandled {
    //case Event(GumballCount, gumballCount) => sender() ! gumballCount; stay()
    case x => println(x); stay()
  }

  onTransition {
    case InceptionState -> PlanningState =>
      println("from \tinit \t\tto \tplanning")
      stateData.budget = 100
    case PlanningState -> BuildState => println("from \tplanning \tto \tbuilding")
    case BuildState -> TestState => println("from \tbuilding \tto \ttesting")
    case TestState -> TransitionState => println("from \ttesting \tto \trollout")
    case TestState -> PausedState => println("from \ttesting \tto \tpaused")
    case TestState -> BugfixState => println("from \ttesting \tto \tbugfixing")
    case BugfixState -> TestState => println("from \tbugfixing \tto \ttesting")
    case TransitionState -> PausedState => println("from \trollout \t\tto \tpausing")
    case TransitionState -> ClosedState => println("from \trollout \tto \tclosed")
    case PausedState ->  TransitionState => println("from \tpausing \tto \trollout\n")
    case PausedState ->  ClosedState => println("from \tpausing \tto \tclosed\n")
  }

  initialize()

}

/**
 * Created by werner on 13.09.15.
 */
//case class Project() extends Actor {
case class Project(name :String)  {

  //val name :String
  var budget :Double = 0

  //override def receive: Receive = ???
}
