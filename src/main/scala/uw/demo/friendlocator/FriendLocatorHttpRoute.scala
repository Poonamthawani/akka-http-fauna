package uw.demo.friendlocator

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.{Directives, Route}

/**
  * Created by pthawani on 7/16/17.
  */
trait FriendLocatorHttpRoute {

  val route : Route = ???

}
