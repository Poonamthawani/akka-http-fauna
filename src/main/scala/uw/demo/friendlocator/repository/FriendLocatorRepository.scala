package uw.demo.friendlocator.repository

import faunadb.FaunaClient
import faunadb.query.{Index, Match, Paginate}
import faunadb.FaunaClient
import faunadb.query._
import faunadb.values._

import scala.concurrent.{ExecutionContext, Future}
import faunadb.values.{Field, Value}

/**
  * Created by pthawani on 7/16/17.
  */
trait FriendLocatorRepository {

  def storeFriend : Future[Value]
  def readFriendByName : Future[Value]
  def readFriendByLocation : Future[Value]
  def updateFriendLocation : Future[Value]

}

class FaunaFriendLocatorRepository(client : FaunaClient) extends FriendLocatorRepository{
  import ExecutionContext.Implicits._
  override def storeFriend: Future[Value] = {
    client.query(
      Create(
        Class("friends11"),
        Obj(
          "data" -> Obj("name" -> "Hrehaan", "city" -> "Issaquah" ,"State" -> "WA" , "location" -> "Disney")
        )))
  }

  override def readFriendByLocation: Future[Value] = {
    client.query(
      Paginate(
        Match(Index("friends11_by_location_with_name"), "Disney")))
  }

  override def readFriendByName : Future[Value] = {
   client.query(
     Get(
      Match(Index("friends11_by_name"), "Hrehaan")))
  }

  override def updateFriendLocation: Future[Value] = {
    client.query(
      Update(
        Select(
          StringV("ref"),
          Get(
            Match(
              Index("friends11_by_name"),
              "Ariana"))),
        Obj("data" -> Obj("location" -> "Disney"))))
  }

}