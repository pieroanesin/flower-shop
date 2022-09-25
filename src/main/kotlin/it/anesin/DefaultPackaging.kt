package it.anesin

class DefaultPackaging: Packaging {

  override fun wrapRoses(quantity: Int): List<Pack> {
    val bundleSizes = listOf(10, 5)

    val (result, remainingFlowers) = wrap(quantity, bundleSizes)
    if (quantity < bundleSizes.min() || remainingFlowers > 0) throw Exception("Roses can't be wrapped")

    return result.filter { it.bundleQuantity > 0 }
  }

  private fun wrap(flowers: Int, bundleSizes: List<Int>): Pair<List<Pack>,Int> {
    val result = mutableListOf<Pack>()
    var remainingFlowers = flowers

    bundleSizes.forEach { bundleSize ->
      result.add(packWith(remainingFlowers, bundleSize))
      remainingFlowers %= bundleSize
    }
    return Pair(result, remainingFlowers)
  }

  private fun packWith(flowersQuantity: Int, bundleSize: Int) = Pack(bundleQuantity = flowersQuantity / bundleSize, bundleSize)

  override fun wrapLilies(quantity: Int): List<Pack> {
    val result = mutableListOf<Pack>()
    val bundleSizes = listOf(9, 6, 3)
    var remainingFlowers = quantity

    bundleSizes.forEach { bundleSize ->
      result.add(packWith(remainingFlowers, bundleSize))
      remainingFlowers %= bundleSize
    }

    if (quantity < bundleSizes.min() || remainingFlowers > 0) throw Exception("Lilies can't be wrapped")

    return result.filter { it.bundleQuantity > 0 }
  }

  override fun wrapTulips(quantity: Int): List<Pack> {
    var result = mutableListOf<Pack>()
    var bundleSizes = listOf(9, 5, 3)
    var remainingFlowers = quantity

    bundleSizes.forEach { bundleSize ->
      result.add(packWith(remainingFlowers, bundleSize))
      remainingFlowers %= bundleSize
    }

    if (remainingFlowers != 0) {
      if (result[0].bundleQuantity < bundleSizes[1]) {
        result = mutableListOf()
        remainingFlowers = quantity
      }
      else {
        result = mutableListOf(Pack(bundleQuantity = result[0].bundleQuantity - (result[0].bundleQuantity % bundleSizes[1]), result[0].bundleSize))
        remainingFlowers = quantity - (result[0].bundleQuantity * bundleSizes[0])
      }

      bundleSizes = bundleSizes.drop(1)

      bundleSizes.forEach { bundleSize ->
        result.add(packWith(remainingFlowers, bundleSize))
        remainingFlowers %= bundleSize
      }

      if (remainingFlowers != 0) {
        if (result[0].bundleQuantity < bundleSizes[1]) {
          result = mutableListOf()
          remainingFlowers = quantity
        }
        else {
          result = mutableListOf(Pack(bundleQuantity = result[0].bundleQuantity - (result[0].bundleQuantity % bundleSizes[1]), result[0].bundleSize))
          remainingFlowers = quantity - (result[0].bundleQuantity * bundleSizes[0])
        }

        bundleSizes = bundleSizes.drop(1)

        bundleSizes.forEach { bundleSize ->
          result.add(packWith(remainingFlowers, bundleSize))
          remainingFlowers %= bundleSize
        }
      }
    }

    if (quantity < bundleSizes.min() || remainingFlowers > 0) throw Exception("Tulips can't be wrapped")

    return result.filter { it.bundleQuantity > 0 }
  }
}
