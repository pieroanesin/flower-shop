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

    var (packs, remainingFlowers) = wrap(quantity, bundleSizes)
    var bigPack = packs[0]
    var mediumPack = packs[1]
    var smallPack = packs[2]

    if (isWrongCombination(remainingFlowers)) {

      while (isWrongCombination(remainingFlowers) && mediumPack.bundleQuantity > 0) {
        mediumPack = Pack(mediumPack.bundleQuantity - 1, mediumPack.bundleSize)
        remainingFlowers = quantity - ((bigPack.bundleQuantity * bigPack.bundleSize) + (mediumPack.bundleQuantity * mediumPack.bundleSize))
        packs = wrap(remainingFlowers, listOf(bundleSizes.last())).first
        remainingFlowers = wrap(remainingFlowers, listOf(bundleSizes.last())).second
        smallPack = packs.first()
      }

      while (isWrongCombination(remainingFlowers) && bigPack.bundleQuantity > 0) {
        bigPack = Pack(bigPack.bundleQuantity - 1, bigPack.bundleSize)
        remainingFlowers = quantity - ((bigPack.bundleQuantity * bigPack.bundleSize))
        packs = wrap(remainingFlowers, bundleSizes.drop(1)).first
        remainingFlowers = wrap(remainingFlowers, bundleSizes.drop(1)).second
        mediumPack = packs.first()
        smallPack = packs.last()

        while (isWrongCombination(remainingFlowers) && mediumPack.bundleQuantity > 0) {
          mediumPack = Pack(mediumPack.bundleQuantity - 1, mediumPack.bundleSize)
          remainingFlowers = quantity - ((bigPack.bundleQuantity * bigPack.bundleSize) + (mediumPack.bundleQuantity * mediumPack.bundleSize))
          packs = wrap(remainingFlowers, listOf(bundleSizes.last())).first
          remainingFlowers = wrap(remainingFlowers, listOf(bundleSizes.last())).second
          smallPack = packs.first()
        }
      }
    }

    if (quantity < bundleSizes.min() || remainingFlowers > 0) throw Exception("Tulips can't be wrapped")

    return listOf(bigPack, mediumPack, smallPack).filter { it.bundleQuantity > 0 }
  }

  private fun isWrongCombination(remainingFlowers: Int) = remainingFlowers != 0

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
