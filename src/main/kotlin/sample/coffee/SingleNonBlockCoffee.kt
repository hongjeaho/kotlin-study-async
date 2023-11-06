package sample.coffee

import mu.KotlinLogging
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers
import java.time.Duration
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger { }
val single: Scheduler = Schedulers.newSingle("worker")

fun main() {
  measureTimeMillis {
    Flux.range(1, 10)
      .flatMap {
        createCoffee()
      }.subscribeOn(single)
      .blockLast()
    single.dispose()
  }.let { logger.debug { "경과 시간: $it mis" } }
}

private fun createCoffee(): Mono<Unit> {
  return Mono.zip(
    grindCoffee().then(brewCoffee()),
    boilMilk().then(fromMilk()),
  ).then(mixCoffeeAndMilk())
}

private fun grindCoffee(): Mono<Unit> {
  return Mono.fromCallable { logger.debug { "커피 원두를 글라인드에 간다" } }
    .delayElement(Duration.ofSeconds(1))
    .publishOn(single)
    .doOnNext { logger.debug { ">> 커피 가루." } }
}

private fun brewCoffee(): Mono<Unit> {
  return Mono.fromCallable { logger.debug { "커피를 내기기 시작한다." } }
    .delayElement(Duration.ofSeconds(1))
    .publishOn(single)
    .doOnNext { logger.debug { ">> 커피 원액." } }
}

private fun boilMilk(): Mono<Unit> {
  return Mono.fromCallable { logger.debug { "우유를 데운다." } }
    .delayElement(Duration.ofSeconds(1))
    .publishOn(single)
    .doOnNext { logger.debug { ">> 따뜩한 우유." } }
}

private fun fromMilk(): Mono<Unit> {
  return Mono.fromCallable { logger.debug { "우유를 거품을 만든다." } }
    .delayElement(Duration.ofSeconds(1))
    .publishOn(single)
    .doOnNext { logger.debug { ">> 우유 거품." } }
}

private fun mixCoffeeAndMilk(): Mono<Unit> {
  return Mono.fromCallable { logger.debug { "커피와 우유를 섞는다.." } }
    .delayElement(Duration.ofSeconds(1))
    .publishOn(single)
    .doOnNext { logger.debug { ">> 까페 라떼." } }
}
