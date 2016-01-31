package de.cogent.reactive.project.domain

import akka.actor.FSM.State
import akka.actor.{ Actor, ActorSystem, Props, FSM }
import de.cogent.reactive.project.domain.ProjectMachine.Data


object ProjectMachineEvent {
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

  sealed trait Data
  case object Uninitialized extends Data
  //final class Project(tile : String, budget :Option[BigDecimal]) extends Data
}

class ProjectMachine extends Actor with FSM[ProjectMachine.State, Project] {

  import ProjectMachine._

  //startWith(InceptionState, myProject)
  startWith(InceptionState, Project("STFP", None))

  when(InceptionState) {
    case Event(ProjectMachineEvent.start, _) => goto(PlanningState)
    case Event(ProjectMachineEvent.plan, _)  => goto(PlanningState)
  }

  when(PlanningState) {
    case Event(ProjectMachineEvent.build, _) => goto(BuildState)
    case Event(ProjectMachineEvent.done, _) => goto(BuildState)
    case Event(ProjectMachineEvent.pause, _) => goto(PausedState)
    case Event(ProjectMachineEvent.cancel, _) => goto(StalledState)
  }

  when(BuildState) {
    case Event(ProjectMachineEvent.test, _) => goto(TestState)
    case Event(ProjectMachineEvent.done, _) => goto(TestState)
    case Event(ProjectMachineEvent.pause, _) => goto(PausedState)
    case Event(ProjectMachineEvent.done, _) => goto(TestState)
  }

  when(TestState) {
    case Event(ProjectMachineEvent.bugfix, _ ) => goto(BugfixState)
    case Event(ProjectMachineEvent.rollout, _) => goto(TransitionState)
    case Event(ProjectMachineEvent.done, _) => goto(TransitionState)
    case Event(ProjectMachineEvent.pause, _) => goto(PausedState)
  }

  when (BugfixState) {
    case Event(ProjectMachineEvent.test, _) => goto(TestState)
    case Event(ProjectMachineEvent.done, _) => goto(TestState)
    case Event(ProjectMachineEvent.pause, _) => goto(PausedState)
    case Event(ProjectMachineEvent.continue, _) => goto(BugfixState)
  }

  when(TransitionState) {
    case Event(ProjectMachineEvent.done, _) => goto(ClosedState)
  }

  when(ClosedState) {
    case Event(ProjectMachineEvent.restart, _) => goto(InceptionState)
  }

  when(PausedState) {
    case Event(ProjectMachineEvent.build, _) => goto(BuildState)
    case Event(ProjectMachineEvent.rollout, _) => goto(TransitionState)
    case Event(ProjectMachineEvent.cancel, _) => goto(StalledState)
    //case Event(continue, _) => goto(pre)
  }

  when(StalledState) {
    case Event(ProjectMachineEvent.restart, _) => goto(InceptionState)
    case Event(ProjectMachineEvent.cancel, _) => goto(StalledState)
  }

  whenUnhandled {
    //case Event(GumballCount, gumballCount) => sender() ! gumballCount; stay()
    case x => println(x); stay()
  }

  onTransition {
    case InceptionState -> PlanningState =>
      println("from \tinit \t\tto \tplanning")
      stateData.budget = 100
      println(stateData)

    case PlanningState -> BuildState => {
      println("from \tplanning \tto \tbuilding")
      stateData.budget=200000
      stateData.manager=Some("John Impossible")
      println(stateData)
    }

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
case class Project(title :String = "-", var manager :Option[String])  {

  var budget :BigDecimal = 0


  override def toString(): String = "\t\t\t--> Project " + title + ", current Manager is " + manager.getOrElse(""""has to be found"""") +", planned bugdet = " + {budget} + " !"

  //override def receive: Receive = ???
}
