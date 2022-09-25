package it.anesin

class DefaultPackaging : Packaging {

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
    val bundleSizes = listOf(9, 5, 3)

    var (packs, remainingFlowers) = wrap(quantity, bundleSizes)

    while (isWrongCombination(remainingFlowers) && (packs[0].bundleQuantity > 0 || packs[1].bundleQuantity > 0)) {
      while (isWrongCombination(remainingFlowers) && packs[1].bundleQuantity > 0) {
        changeMediumCombination(packs, quantity, listOf(bundleSizes.last())).let { result ->
          packs = result.first
          remainingFlowers = result.second
        }
      }
      if (isWrongCombination(remainingFlowers) && packs[0].bundleQuantity > 0) {
        changeBigCombination(packs, quantity, bundleSizes.drop(1)).let { result ->
          packs = result.first
          remainingFlowers = result.second
        }
      }
    }

    if (quantity < bundleSizes.min() || remainingFlowers > 0) throw Exception("Tulips can't be wrapped")

    return packs.filter { it.bundleQuantity > 0 }
  }

  private fun changeMediumCombination(packs: List<Pack>, totalFlowers: Int, bundleSizes: List<Int>): Pair<List<Pack>, Int> {
    val bigPack = packs[0]
    var mediumPack = packs[1]

    mediumPack = Pack(mediumPack.bundleQuantity - 1, mediumPack.bundleSize)
    val flowers = totalFlowers - ((bigPack.bundleQuantity * bigPack.bundleSize) + (mediumPack.bundleQuantity * mediumPack.bundleSize))
    val (packs, remainingFlowers) = wrap(flowers, bundleSizes)
    val smallPack = packs.first()

    return Pair(listOf(bigPack, mediumPack, smallPack), remainingFlowers)
  }

  private fun changeBigCombination(packs: List<Pack>, totalFlowers: Int, bundleSizes: List<Int>): Pair<List<Pack>, Int> {
    var bigPack = packs[0]
    var mediumPack = packs[1]

    bigPack = Pack(bigPack.bundleQuantity - 1, bigPack.bundleSize)
    val flowers = totalFlowers - ((bigPack.bundleQuantity * bigPack.bundleSize) + (mediumPack.bundleQuantity * mediumPack.bundleSize))
    val (packs, remainingFlowers) = wrap(flowers, bundleSizes)
    mediumPack = packs.first()
    val smallPack = packs.last()

    return Pair(listOf(bigPack, mediumPack, smallPack), remainingFlowers)
  }

  private fun isWrongCombination(remainingFlowers: Int) = remainingFlowers != 0

  private fun wrap(flowers: Int, bundleSizes: List<Int>): Pair<List<Pack>, Int> {
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
