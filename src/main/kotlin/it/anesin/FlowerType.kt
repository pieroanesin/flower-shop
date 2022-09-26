package it.anesin

enum class FlowerType(val description: String, val bundleSizePrice: Map<Int, Double>) {
  R12("Roses", mapOf(5 to 6.99, 10 to 12.99)),
  L09("Lilies", mapOf(3 to 9.95, 6 to 16.95, 9 to 24.95)),
  T58("Tulips", mapOf(3 to 5.95, 5 to 9.95, 9 to 16.99))
}
