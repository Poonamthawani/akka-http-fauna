package uw.demo.friendlocator

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives
import akka.stream.Materializer
import faunadb.values.Field
import uw.demo.friendlocator.friendlocator.{Friend1, FriendLocation}
import uw.demo.friendlocator.friendlocator.FriendLocatorRestModels.Friend
import uw.demo.friendlocator.service.FriendLocatorService

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by pthawani on 7/16/17.
  */
trait FriendLocatorHttpRoute extends Directives{

  implicit def system: ActorSystem
  implicit def materializer: Materializer
  implicit def ec: ExecutionContext
  implicit def friendLocatorService: FriendLocatorService


  val route = {

    import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

    import friendlocator.CirceCompanions._

    path("hello") {
      get {
        complete(Future(List("New World","Poonam")))
      }
    } ~
      path("myfriends") {
        get {
          // will marshal Item to JSON
          parameters('name.as[String]) { frndName =>
            complete(friendLocatorService.retriveFriendsByName(frndName).map(res => res.toString))
          }
        }
      } ~
      path("findfriendbyname") {
        get {
          // will marshal Item to JSON
          parameters('name.as[String]) { frndName =>
            complete(friendLocatorService.retriveFriendsByName(frndName).map(value => value("data").collect(Field("data").to[Friend1]).toString))
           //complete(friendLocatorService.retriveFriendsByName(frndName).map(value => value("data").collect(Field("data").to[Friend1])).toString)
           // complete(friendLocatorService.retriveFriendsByName(frndName).map(res => res("data").to[Seq[Friend1]].map(x =>x).toString))
          }
        }
      } ~
      path("findfriendbylocation" / Segment) { location =>
        get {
          // will marshal Item to JSON
          //complete(friendLocatorService.retreiveFriendsByLocation(location).map(res => res("data").toString))
          complete(friendLocatorService.retreiveFriendsByLocation(location).map(value => value("data").collect(Field("data").to[Friend1]).toString))

          //complete(friendLocatorService.retreiveFriendsByLocation(location).map(res => res("data").to[Seq[FriendLocation]].map(x => x).toString))
          //complete(friendLocatorService.retreiveFriendsByLocation(location).map(res => res("data").collect(Field("data").to[FriendLocation].toString)))
        }
      } ~
      path("addfriend") {
        post {
          entity(as[Friend]) { friend =>
            // will marshal Item to JSON

            complete(friendLocatorService.addFriend(friend).toString)
          }
        }
      } ~
      path("checkinlocation") {
        post {
          entity(as[Friend]) {friend =>
            // will marshal Item to JSON
            complete(friendLocatorService.updateLocation(friend).toString)
          }
        }
      }
  }
}


