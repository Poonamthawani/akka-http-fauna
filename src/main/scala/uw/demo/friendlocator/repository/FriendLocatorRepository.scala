package uw.demo.friendlocator.repository

import faunadb.FaunaClient
import faunadb.query.{Index, Match, Paginate}
import faunadb.FaunaClient
import faunadb.query._
import faunadb.values._

import scala.concurrent.{ExecutionContext, Future}
import faunadb.values.{Field, Value}
import uw.demo.friendlocator.friendlocator.Friend1
import uw.demo.friendlocator.friendlocator.FriendLocatorRestModels.Friend
import faunadb.{query => q}

/**
  * Created by pthawani on 7/16/17.
  */
trait FriendLocatorRepository {

  def storeFriend(friend : Friend) : Future[Value]
  def readFriendByName(name : String) : Future[Value]
  def readFriendByLocation(location : String) : Future[Value]
  def updateFriendLocation(friend : Friend) : Future[Value]

}

class FaunaFriendLocatorRepository(client : FaunaClient) extends FriendLocatorRepository{
  import ExecutionContext.Implicits._
  override def storeFriend(friend: Friend): Future[Value] = {
    client.query(
      Create(
        Class("friends11"),
        Obj(
          "data" -> Obj("name" -> friend.name, "city" -> friend.city ,"State" -> friend.State , "location" -> friend.location)
        )))
  }

  override def readFriendByLocation(location : String): Future[Value] = {
  /* client.query(
      Get(
        Match(Index("friends11_by_location_with_name"), location)))
*/
  val pages = q.Paginate(q.Match(q.Index("friends11_by_location_with_name"), location))
    val expr = q.Map(pages, q.Lambda { (_, _, ref) => q.Get(ref) })
    client.query(expr)

  }

  override def readFriendByName(name : String) : Future[Value] = {
    /*client.query(
     Paginate(
      Match(Index("friends11_by_name"), name)))
*/
    val pages = q.Paginate(q.Match(q.Index("friends11_by_name"), name))
    val expr = q.Map(pages, q.Lambda { (_, _, ref) => q.Get(ref) })
    client.query(expr)
    //  .map { value => value("data").collect(Field("data").to[Friend1]) }
  }

  override def updateFriendLocation(friend : Friend): Future[Value] = {
    client.query(
      Update(
        Select(
          StringV("ref"),
          Get(
            Match(
              Index("friends11_by_name"),
              friend.name))),
        Obj("data" -> Obj("location" -> friend.location))))
  }

}