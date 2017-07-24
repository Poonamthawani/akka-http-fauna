package uw.demo.friendlocator

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import uw.demo.friendlocator.friendlocator.FriendLocatorRestModels.Friend


/**
  * Created by pthawani on 7/24/17.
  */
package object friendlocator {
  object FriendLocatorRestModels {

    case class Friend(name: String, city: String, State: String, location: String)

  }

  object CirceCompanions {

    import io.circe.{Decoder, Encoder}
    import io.circe.generic.semiauto._
    implicit val decodeFriend: Decoder[Friend] = deriveDecoder[Friend]
    implicit val encodeFriend: Encoder[Friend] = deriveEncoder[Friend]
  }
}
