package uw.demo.friendlocator

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.{ActorMaterializer, Materializer}
import uw.demo.friendlocator.service.FriendLocatorService
import akka.http.scaladsl.Http

import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn
import scala.concurrent.ExecutionContext

/**
  * Created by pthawani on 7/16/17.
  */
trait FriendLocatorHttpRoute extends Directives{

  implicit def system: ActorSystem
  implicit def materializer: Materializer
  implicit def ec: ExecutionContext
  implicit def friendLocatorService: FriendLocatorService


  val route =
    path("hello") {
      get {
        complete("New World")
      }
    }

}
