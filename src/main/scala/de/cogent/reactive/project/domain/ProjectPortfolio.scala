package de.cogent.reactive.project.domain

import de.cogent.reactive.project.domain._
import scala.collection.immutable._

/**
 * Created by werner on 13.09.15.
 */
case class ProjectPortfolio() {

  var portfolio :Set[Project] = Set()

  // ToDo wie sieht es mit dem RETURN-Wert aus, sollte es ein neues Set sein ?, wg.Concurrency
  def addProject(project :Project) :Set[Project] = {
    //portfolio = portfolio + project
    portfolio + project
  }

  def removeProject(project :Project) :Set[Project] = {
    //portfolio = portfolio - project
    portfolio - project
  }

}
