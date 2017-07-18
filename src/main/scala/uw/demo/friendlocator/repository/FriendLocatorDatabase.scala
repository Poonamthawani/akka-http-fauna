package uw.demo.friendlocator.repository

import scala.concurrent.Future
import faunadb.FaunaClient
import faunadb.query._
import faunadb.values._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import faunadb.{ query => q }
import faunadb.values.{
Field,
Value
}

/**
  * Created by pthawani on 7/16/17.
  */
class FriendLocatorDatabase(client: FaunaClient) (implicit ec:ExecutionContext){

  val createLocationIdIdx =
    q.CreateIndex(
      q.Obj(
        "name" -> "friends11_by_location_with_name",
        "source" -> q.Class("friends11"),
        "terms" -> q.Arr(
          q.Obj("field" -> q.Arr("data", "location"))
        ),
        "values" -> q.Arr(
          q.Obj("field" -> q.Arr("data", "name")),
          q.Obj("field" -> q.Arr("data", "location")),
          q.Obj("field" -> q.Arr("ref"))
        )
      )
    )

  val createNameIdIdx =
    q.CreateIndex(
      q.Obj(
        "name" -> "friends11_by_name",
        "source" -> q.Class("friends11"),
        "terms" -> q.Arr(
          q.Obj("field" -> q.Arr("data", "name"))
        ),
        "values" -> q.Arr(
          q.Obj("field" -> q.Arr("data", "name")),
          q.Obj("field" -> q.Arr("data", "location")),
          q.Obj("field" -> q.Arr("ref"))
        )
      )
    )

  val createAllIdIdx =
    q.CreateIndex(
      q.Obj(
        "name" -> "friends11_all",
        "source" -> q.Class("friends11"),
        "values" -> q.Arr(
          q.Obj("field" -> q.Arr("data", "name"), "reverse" -> true),
          q.Obj("field" -> q.Arr("data", "location")),
          q.Obj("field" -> q.Arr("ref"))
        )
      )
    )

  val createFolllowerIdIdx =
    q.CreateIndex(
      q.Obj(
        "name" -> "followers_by_friend",
        "source" -> q.Class("Friends11Followers"),
        "terms" -> q.Arr(
          q.Obj("field" -> q.Arr("data", "friend"))
        ),
        "values" -> q.Arr(
          q.Obj("field" -> q.Arr("data", "followers")),
          q.Obj("field" -> q.Arr("data", "friend")),
          q.Obj("field" -> q.Arr("ref"))
        )
      )
    )

  def init : Future[Unit] = {

    for {
      _ <- client.query(q.CreateClass(q.Obj("name" -> "friends11"))).recoverWith {
        case _ => client.query(q.Get(q.Class("friends11")))
      }
      _ <- client.query(createLocationIdIdx).recoverWith {
        case _ => client.query(q.Get(q.Index("friends11_by_location_with_name")))
      }
      _ <- client.query(createNameIdIdx).recoverWith {
        case _ => client.query(q.Get(q.Index("friends11_by_name")))
      }
      _ <- client.query(createAllIdIdx).recoverWith {
        case _ => client.query(q.Get(q.Index("friends11_all")))
      }
      _ <- client.query(q.CreateClass(q.Obj("name" -> "Friends11Followers"))).recoverWith {
        case _ => client.query(q.Get(q.Class("Friends11Followers")))
      }
      _ <- client.query(createFolllowerIdIdx).recoverWith {
        case _ => client.query(q.Get(q.Index("followers_by_friend")))
      }
    } yield ()
  }



}

object FriendLocatorDatabase{
  def apply(client: FaunaClient)(implicit ec: ExecutionContext) : FriendLocatorDatabase = new FriendLocatorDatabase()
}
