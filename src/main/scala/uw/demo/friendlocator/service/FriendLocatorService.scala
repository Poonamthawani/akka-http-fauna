package uw.demo.friendlocator.service

import scala.concurrent.{ExecutionContext, Future}
import faunadb.values.{Field, Result, Value}
import uw.demo.friendlocator.friendlocator.Friend1
import uw.demo.friendlocator.friendlocator.FriendLocatorRestModels.Friend
import uw.demo.friendlocator.repository.FriendLocatorRepository


/**
  * Created by pthawani on 7/16/17.
  */
trait FriendLocatorService {

  def addFriend(friend : Friend) : Future[Value]
  def retreiveFriends(name : String) : Future[Value]
  def retriveFriendsByName(name : String) : Future[Value]
  def retreiveFriendsByLocation(location : String) : Future[Value]
  def updateLocation(friend : Friend) : Future[Value]

}

class FriendLocatorServiceImpl(repo : FriendLocatorRepository) (implicit executionContext: ExecutionContext)
  extends  FriendLocatorService {
  override def addFriend(friend:Friend) : Future[Value] = {
    repo.storeFriend(friend)
  }

  override def retreiveFriends(name : String) : Future[Value] = {
    repo.readFriendByName(name)
  }

  override def retriveFriendsByName(name : String) : Future[Value]  = {
    repo.readFriendByName(name)
  }

  override def retreiveFriendsByLocation(location : String) : Future[Value]  = {
    repo.readFriendByLocation(location)
  }

  override def updateLocation(friend : Friend): Future[Value] = {
    repo.updateFriendLocation(friend)
  }

}