package it.anesin

import it.anesin.FlowerType.*

class DefaultCatalogue: Catalogue {
  override fun bundlesOf(type: FlowerType): List<Bundle> {
    return when (type) {
      R12 -> listOf(Bundle(5, 6.99), Bundle(10, 12.99))
      L09 -> listOf(Bundle(3, 9.95), Bundle(6, 16.95), Bundle(9, 24.95))
      T58 -> listOf(Bundle(3, 5.95), Bundle(5, 9.95), Bundle(9, 16.99))
    }
  }

  override fun bundleSizesOf(type: FlowerType): List<Int> {
    return bundlesOf(type).map { it.size }
  }
}
