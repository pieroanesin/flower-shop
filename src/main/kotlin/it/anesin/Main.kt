package it.anesin

import it.anesin.FlowerType.*

fun main() {
  val packaging = DefaultPackaging()

  val order = listOf(Product(quantity = 10, R12), Product(quantity = 15, L09), Product(quantity = 13,T58))

  order
    .map { product -> packaging.wrapFlowers(product.quantity, product.type) }
    .map { packs ->  }
}
