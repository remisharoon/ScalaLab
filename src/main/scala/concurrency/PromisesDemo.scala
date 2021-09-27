package concurrency

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Random, Success}

object PromisesDemo extends App{

  val promise = Promise[Int]

  val fa = Future{
    1 to 2 foreach ({ _ =>
      Thread.sleep(1000)
      println("sleep for a sec")
    })
//    promise.success(42)
    42
  }

  fa.onComplete{case Success(res) => println("Future completed %d".format(res))}
//  promise.future.onComplete{case Success(res) => println("Promise completed %d".format(res))}

//  Thread.sleep(2000)
  println("Main thread waiting ... ")
  Await.result(fa,Duration.Inf)

  println(fa.value)
  Thread.sleep(20)

  //fulfil future immediately
  def fulfilFutureImmediately[T](value:T):Future[T]={
    Future(value)
  }

  //return first out of two futures
  def first[T](a:Future[T], b:Future[T]):Future[T] = {
    val promise = Promise[T]

    a.onComplete(promise.tryComplete)
    b.onComplete(promise.tryComplete)

    promise.future
  }

  // retry until
  def retryUntil[A](action: () => Future[A], condition: (A) => Boolean): Future[A] =
    action()
      .filter(condition)
      .recoverWith {
        case _ => retryUntil(action, condition)
      }

  val random = new Random()
  val action = () => Future {
    Thread.sleep(100)
    val nextValue = random.nextInt(100)
    println("generated " + nextValue)
    nextValue
  }

  retryUntil(action, (x: Int) =>  x < 10).foreach(result => println("settled at " + result))
  Thread.sleep(10000)

}
