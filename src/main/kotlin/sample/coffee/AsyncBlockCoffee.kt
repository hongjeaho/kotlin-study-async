package sample.coffee

import mu.KotlinLogging
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger {  }
private val employees = Executors.newFixedThreadPool(2)

fun main() {
  measureTimeMillis {
    repeat(2) {
      createCoffee()
    }
    employees.shutdown()
  }.let { logger.debug { "경과 시간: $it mis" } }
}

private fun createCoffee(){

  val coffee = employees.submit {         //async
    grindCoffee()
    brewCoffee()
  }

  val milk = employees.submit {           //async
    boilMilk()
    fromMilk()
  }

  while (!coffee.isDone && !milk.isDone)  // block

  mixCoffeeAndMilk()                      // block
}

private fun grindCoffee() {
  logger.debug { "커피 원두를 글라인드에 간다" }
  Thread.sleep(1000)
  logger.debug { ">> 커피 가루." }
}

private fun brewCoffee() {
  logger.debug { "커피를 내기기 시작한다." }
  Thread.sleep(1000)
  logger.debug { ">> 커피 원액." }
}

private fun boilMilk() {
  logger.debug { "우유를 데운다." }
  Thread.sleep(1000)
  logger.debug { ">> 따뜩한 우유." }
}

private fun fromMilk() {
  logger.debug { "우유를 거품을 만든다." }
  Thread.sleep(1000)
  logger.debug { ">> 우유 거품." }
}


private fun mixCoffeeAndMilk() {
  logger.debug { "커피와 우유를 섞는다.." }
  Thread.sleep(1000)
  logger.debug { ">> 까페 라떼." }
}