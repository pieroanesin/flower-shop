package it.anesin

class DefaultPackaging: Packaging {

  override fun wrapRoses(quantity: Int): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    val bundleSizes = listOf(10, 5)
    var remainingFlowers = quantity

    bundleSizes.forEach { bundleSize ->
      result.add(totalBundles(remainingFlowers, bundleSize))
      remainingFlowers %= bundleSize
    }

    if (quantity < bundleSizes.min() || remainingFlowers > 0) throw Exception("Roses can't be wrapped")

    return result.filter { it.first > 0 }
  }

  override fun wrapLilies(quantity: Int): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    val bundleSizes = listOf(9, 6, 3)
    var remainingFlowers = quantity

    bundleSizes.forEach { bundleSize ->
      result.add(totalBundles(remainingFlowers, bundleSize))
      remainingFlowers %= bundleSize
    }

    if (quantity < bundleSizes.min() || remainingFlowers > 0) throw Exception("Lilies can't be wrapped")

    return result.filter { it.first > 0 }
  }

  private fun totalBundles(flowers: Int, size: Int): Pair<Int, Int> {
    val quantity = flowers / size
    return Pair(quantity, size)
  }

  override fun wrapTulips(quantity: Int): List<Pair<Int, Int>> {
    var result = mutableListOf<Pair<Int, Int>>()
    var bundleSizes = listOf(9, 5, 3)
    var remainingFlowers = quantity

    bundleSizes.forEach { bundleSize ->
      result.add(totalBundles(remainingFlowers, bundleSize))
      remainingFlowers %= bundleSize
    }

    if (remainingFlowers != 0) {
      if (result[0].first < bundleSizes[1]) {
        result = mutableListOf()
        remainingFlowers = quantity
      }
      else {
        result = mutableListOf(Pair(result[0].first - (result[0].first % bundleSizes[1]), result[0].second))
        remainingFlowers = quantity - (result[0].first * bundleSizes[0])
      }

      bundleSizes = bundleSizes.drop(1)

      bundleSizes.forEach { bundleSize ->
        result.add(totalBundles(remainingFlowers, bundleSize))
        remainingFlowers %= bundleSize
      }

      if (remainingFlowers != 0) {
        if (result[0].first < bundleSizes[1]) {
          result = mutableListOf()
          remainingFlowers = quantity
        }
        else {
          result = mutableListOf(Pair(result[0].first - (result[0].first % bundleSizes[1]), result[0].second))
          remainingFlowers = quantity - (result[0].first * bundleSizes[0])
        }

        bundleSizes = bundleSizes.drop(1)

        bundleSizes.forEach { bundleSize ->
          result.add(totalBundles(remainingFlowers, bundleSize))
          remainingFlowers %= bundleSize
        }
      }
    }

    if (quantity < bundleSizes.min() || remainingFlowers > 0) throw Exception("Tulips can't be wrapped")

    return result.filter { it.first > 0 }
  }
}
