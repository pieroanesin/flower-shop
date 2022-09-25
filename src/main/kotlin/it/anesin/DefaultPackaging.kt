package it.anesin

class DefaultPackaging: Packaging {

  override fun wrapRoses(quantity: Int): List<Pack> {
    val bundleSizes = listOf(10, 5)

    val (packs, remainingFlowers) = wrap(quantity, bundleSizes)
    if (quantity < bundleSizes.min() || remainingFlowers > 0) throw Exception("Roses can't be wrapped")

    return packs.filter { it.bundleQuantity > 0 }
  }

  override fun wrapLilies(quantity: Int): List<Pack> {
    val bundleSizes = listOf(9, 6, 3)

    val (packs, remainingFlowers) = wrap(quantity, bundleSizes)
    if (quantity < bundleSizes.min() || remainingFlowers > 0) throw Exception("Lilies can't be wrapped")

    return packs.filter { it.bundleQuantity > 0 }
  }

  override fun wrapTulips(quantity: Int): List<Pack> {
    var bundleSizes = listOf(9, 5, 3)
    val totalPacks = mutableListOf<Pack>()

    var (packs, remainingFlowers) = wrap(quantity, bundleSizes)

    while (remainingFlowers != 0 && bundleSizes.size > 1) {
      if (packs[0].bundleQuantity >= bundleSizes[1]) {
        totalPacks += mutableListOf(Pack(bundleQuantity = packs[0].bundleQuantity - (packs[0].bundleQuantity % bundleSizes[1]), packs[0].bundleSize))
        remainingFlowers = quantity - (packs[0].bundleQuantity * bundleSizes[0])
      } else {
        remainingFlowers = quantity
      }

      bundleSizes = bundleSizes.drop(1)

      packs = wrap(remainingFlowers, bundleSizes).first
      remainingFlowers = wrap(remainingFlowers, bundleSizes).second
    }

    totalPacks += packs

    if (quantity < bundleSizes.min() || remainingFlowers > 0) throw Exception("Tulips can't be wrapped")

    return totalPacks.filter { it.bundleQuantity > 0 }
  }

  private fun wrap(flowers: Int, bundleSizes: List<Int>): Pair<List<Pack>,Int> {
    val packs = mutableListOf<Pack>()
    var remainingFlowers = flowers

    bundleSizes.forEach { bundleSize ->
      packs.add(packWith(remainingFlowers, bundleSize))
      remainingFlowers %= bundleSize
    }
    return Pair(packs, remainingFlowers)
  }

  private fun packWith(flowersQuantity: Int, bundleSize: Int) = Pack(bundleQuantity = flowersQuantity / bundleSize, bundleSize)
}
