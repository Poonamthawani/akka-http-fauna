package uw.demo.friendlocator


import faunadb.values._
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


  case class Friend1(name: String, city: String, State: String, location: String)


  object Friend1 {
    implicit object decoder extends Decoder[Friend1] {
      def decode(value: Value, path: FieldPath): Result[Friend1] = {
        for {

          name <- value("name").to[String]
          city <- value("city").to[String]
          state <- value("State").to[String]
          location <- value("location").to[String]
        } yield Friend1(name,city,state, location)
      }
    }
  }



}
