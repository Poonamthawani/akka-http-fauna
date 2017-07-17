package uw.demo.friendlocator.repository

/**
  * Created by pthawani on 7/16/17.
  */
trait FriendLocatorRepository {

  def storeFriend = ???
  def readFriendByName = ???
  def readFriendByLocation = ???
  def updateFriendLocation = ???

}

class FaunaFriendLocatorRepository extends FriendLocatorRepository{
  override def storeFriend: Nothing = ???

  override def readFriendByName: Nothing = ???

  override def readFriendByLocation: Nothing = ???

  override def updateFriendLocation: Nothing = ???

}