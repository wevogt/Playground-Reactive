package de.cogent.reactive.project.domain

/**
 * Created by werner on 13.09.15.
 */
case class ProjectPortfolio() {

  val portfolio = Set[Project]

  def addProject(project :Project) :Unit => {
    portfolio  += project
  }

}
