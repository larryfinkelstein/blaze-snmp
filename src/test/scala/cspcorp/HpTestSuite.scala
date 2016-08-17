package cspcorp

import java.net.InetSocketAddress

import akka.actor.ActorSystem
import blazesnmp.{GetResponse, ObjectIdentifier, SnmpServiceImpl, Target}
import org.scalatest.{FunSuite, Matchers}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by larryf on 8/17/2016.
  */
class HpTestSuite extends FunSuite with Matchers {

  test("Test HP Get Page Count") {
    val system = ActorSystem("blaze")

    // An SNMPv2 target
    val address = new InetSocketAddress("10.40.99.127", 161)
    val community = "public"

    // Some SNMP object identifiers to request
    val sysPageCount = ObjectIdentifier(Seq(1,3,6,1,2,1,43,10,2,1,4,1,1))

    val service = new SnmpServiceImpl(system)
    val future: Future[GetResponse] = service.getRequest(Target(address, community),List(sysPageCount))

    Await.result(future, 10000 millis)

//    future.foreach(println)

    val response = future map {
      items => Some(items)
    }
    response.foreach(println)
  }
}
