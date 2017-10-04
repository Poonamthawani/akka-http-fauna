package uw.demo.friendlocator

import java.time.DateTimeException

import akka.actor.ActorSystem
import akka.kafka.ConsumerSettings
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, Materializer, Supervision}
import com.typesafe.config.Config
import nl.grons.metrics.scala.DefaultInstrumented
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import uw.demo.friendlocator.service.{FriendLocatorService, FriendLocatorServiceImpl}
import uw.demo.friendlocator.repository.{FaunaFriendLocatorRepository, FriendLocatorDatabase}
import akka.http.scaladsl.Http
import faunadb.FaunaClient
import faunadb.query.{Get, Index, Match}
import faunadb.values.{Codec, FieldPath, Result, Value}
import io.circe.Decoder
import net.ceedubs.ficus.Ficus._
import nl.grons.metrics.scala.{DefaultInstrumented, MetricName}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Left, Right, Success}
import uw.demo.friendlocator.friendlocator
import uw.demo.friendlocator.friendlocator.Friend1

/**
  * Created by pthawani on 7/16/17.
  */
class FriendLocatorMain(config: Config)(
 implicit val system : ActorSystem,
 val materializer : Materializer,
 val friendLocatorService : FriendLocatorService,
 val ec : ExecutionContext

) extends FriendLocatorHttpRoute with DefaultInstrumented {

}





object FriendLocatorMain extends DefaultInstrumented {

  def main(args: Array[String]): Unit = {
    println("Welcome to Fauna")

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    implicit val ec = system.dispatcher

    val config = system.settings.config.as[Config]("uw.demo.friendlocatorservice")
    val host   = config.as[String]("host")
    val port   = config.as[Int]("port").toInt
    val serverKey      = config.as[String]("DBServerKey.serverKey")

    val client: FaunaClient = FaunaClient(secret = serverKey)
    Await.result(FriendLocatorDatabase(client).init, 10.seconds)

    val repo = new FaunaFriendLocatorRepository(client)




    val friend1 = client.query(
      Get(
        Match(Index("friends11_by_name"), "Hrehaan")))
      .map(value => value("data").to[Friend1])



    println(
      Await.result(friend1, Duration.Inf)
    )


    implicit val friendLocatorService = new FriendLocatorServiceImpl(repo)

    val service       = new FriendLocatorMain(config)
    val bindingFuture = Http().bindAndHandle(service.route, host, port)

    bindingFuture.onComplete {
      case Success(binding) =>
        val la = binding.localAddress
        system.log.info(
          s"Service 'Friend Locator Service' started - Listening for HTTP on /${la.getHostName}:${la.getPort}"
        )
      case Failure(ex) =>
        system.log.error(s"Can't bind to $host:$port", ex)
        system.terminate()
    }



  }

}
