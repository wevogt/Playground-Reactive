package de.cogent.reactive.project.domain

import akka.actor.FSM.{CurrentState, SubscribeTransitionCallBack}
import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import de.cogent.reactive.project.domain.ProjectMachine.BuildState
import de.cogent.reactive.project.domain.ProjectMachineEvent.{build, plan, start}
import org.scalatest.{MustMatchers, WordSpecLike}


/**
 * Created by werner on 14.09.15.
 */
class ProjectSpec extends TestKit(ActorSystem("test-system"))
with MustMatchers
with WordSpecLike
//with StopSystemAfterAll
with ImplicitSender {


  "A project in PlanningState" must {}
    "transition to BuildState" in {

      val projectActor = system.actorOf(Props[ProjectMachine])

      projectActor ! start
      projectActor ! plan
      projectActor ! build
      projectActor ! SubscribeTransitionCallBack(testActor)

      expectMsgPF() {
        case CurrentState(_, BuildState) => true
        //case CurrentState(_, BuildState) => { true
        case _ => fail("must transition do BuildState")
      }
    }


/*
    "A gumball machine in NoQuarterState" must {
      "transition to HasQuarterState" in {
        val gumballMachine = system.actorOf(Props[GumballMachine])

        gumballMachine ! InsertQuarter
        gumballMachine ! SubscribeTransitionCallBack(testActor)

        expectMsgPF() {
          case CurrentState(_, HasQuarterState) => true
          case _ => fail("must transition to HasQuarterState")
        }
      }
    }

    "A gumball machine in HasQuarterState" must {
      "return to NoQuarterState if customer ejects quarter" in {
        val gumballMachine = system.actorOf(Props[GumballMachine])

        gumballMachine ! InsertQuarter
        gumballMachine ! EjectQuarter
        gumballMachine ! SubscribeTransitionCallBack(testActor)

        expectMsgPF() {
          case CurrentState(_, NoQuarterState) => true
          case _ => fail("must transition to NoQuarterState")
        }
      }
    }

    "A gumball machine in HasQuarterState" must {
      "transition to SoldState" in {
        val gumballMachine = system.actorOf(Props[GumballMachine])

        gumballMachine ! InsertQuarter
        gumballMachine ! TurnCrank
        gumballMachine ! SubscribeTransitionCallBack(testActor)

        expectMsgPF() {
          case CurrentState(_, SoldState) => true
          case _ => fail("must transition to SoldState")
        }
      }
    }

    "A gumball machine" must {
      "have some gumballs" in {
        implicit val timeout = Timeout(5 seconds)

        val gumballMachine = system.actorOf(Props[GumballMachine])

        val futureCount = ask(gumballMachine, GumballCount).mapTo[Int]

        val y = Await.result(futureCount, timeout.duration)

        y must be > 0
      }
    }
*/

}
