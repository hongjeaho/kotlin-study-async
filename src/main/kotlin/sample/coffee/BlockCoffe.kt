package sample.coffee

import mu.KotlinLogging
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger {  }

fun main() {
  measureTimeMillis {
    createCoffee()
  }.let { logger.debug { "경과 시간: $it mis" } }
}

private fun createCoffee(){
  grindCoffee()
  brewCoffee()
  boilMilk()
  fromMilk()
  mixCoffeeAndMilk()
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