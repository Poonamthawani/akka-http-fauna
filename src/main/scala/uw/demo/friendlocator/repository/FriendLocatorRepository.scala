package uw.demo.friendlocator.repository

import faunadb.FaunaClient
import faunadb.query.{Index, Match, Paginate}
import faunadb.FaunaClient
import faunadb.query._
import faunadb.values._

import scala.concurrent.{ExecutionContext, Future}
import faunadb.values.{Field, Value}
import uw.demo.friendlocator.friendlocator.FriendLocatorRestModels.Friend


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
    client.query(
      Paginate(
        Match(Index("friends11_by_location_with_name"), location)))
  }

  override def readFriendByName(name : String) : Future[Value] = {
   client.query(
     Get(
      Match(Index("friends11_by_name"), name)))
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