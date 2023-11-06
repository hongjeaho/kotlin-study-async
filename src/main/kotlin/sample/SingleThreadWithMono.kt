package sample

import mu.KotlinLogging
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers
import java.time.Duration
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger {}

val single: Scheduler = Schedulers.newSingle("worker")

fun main() {
  measureTimeMillis {
    val mono = Mono.zip(
      subA(),
      subB(),
    ).subscribeOn(single).block()
    single.dispose()
  }.let {
    logger.debug { ">>>> exit : $it ms" }
  }
}

private fun subA(): Mono<Unit> {
  return Mono.fromCallable { logger.debug { "Start A" } }
    .delayElement(Duration.ofSeconds(1))
    .publishOn(single)
    .doOnNext { logger.debug { "End A" } }
}

private fun subB(): Mono<Unit> {
  return Mono.fromCallable { logger.debug { "Start B" } }
    .delayElement(Duration.ofSeconds(1))
    .publishOn(single)
    .doOnNext { logger.debug { "End B" } }
}
