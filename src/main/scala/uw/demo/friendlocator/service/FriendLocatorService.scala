package uw.demo.friendlocator.service

import scala.concurrent.{ExecutionContext, Future}
import faunadb.values.{Field, Value}
import uw.demo.friendlocator.repository.FriendLocatorRepository

/**
  * Created by pthawani on 7/16/17.
  */
trait FriendLocatorService {

  def addFriend : Future[Value]
  def retreiveFriends : Future[Value]
  def retriveFriendsByName : Future[Value]
  def retreiveFriendsByLocation : Future[Value]
  def updateLocation : Future[Value]

}

class FriendLocatorServiceImpl(repo : FriendLocatorRepository) (implicit executionContext: ExecutionContext)
  extends  FriendLocatorService {
  override def addFriend : Future[Value] = {
    repo.storeFriend
  }

  override def retreiveFriends : Future[Value]  = {
    repo.readFriendByLocation
  }

  override def retriveFriendsByName : Future[Value]  = {
    repo.readFriendByName
  }

  override def retreiveFriendsByLocation : Future[Value]  = {
    repo.readFriendByLocation
  }

  override def updateLocation: Future[Value] = {
    repo.updateFriendLocation
  }

}