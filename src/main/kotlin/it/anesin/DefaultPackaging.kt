package it.anesin

import it.anesin.FlowerType.*

class DefaultPackaging : Packaging {

  override fun wrapFlowers(quantity: Int, type: FlowerType): List<Package> {
    val bundleSizes = when(type) {
      R12 -> listOf(10, 5)
      L09 -> listOf(9, 6, 3)
      T58 -> listOf(9, 5, 3)
    }
    var (packages, remainingFlowers) = wrap(quantity, bundleSizes, type)

    while (isWrongCombination(remainingFlowers) && (packages[0].bundleQuantity > 0 || packages[1].bundleQuantity > 0)) {
      while (isWrongCombination(remainingFlowers) && packages[1].bundleQuantity > 0) {
        changeMediumPackQuantity(packages, quantity, listOf(bundleSizes.last())).let { result ->
          packages = result.first
          remainingFlowers = result.second
        }
      }
      if (isWrongCombination(remainingFlowers) && packages[0].bundleQuantity > 0) {
        changeBigPackQuantity(packages, quantity, bundleSizes.drop(1)).let { result ->
          packages = result.first
          remainingFlowers = result.second
        }
      }
    }

    if (quantity < bundleSizes.min() || remainingFlowers > 0) throw Exception("${type.description} can't be wrapped")

    return packages.filter { it.bundleQuantity > 0 }
  }

  private fun changeMediumPackQuantity(packages: List<Package>, totalFlowers: Int, bundleSizes: List<Int>): Pair<List<Package>, Int> {
    val bigPack = packages[0]
    var mediumPack = packages[1]

    mediumPack = Package(mediumPack.bundleQuantity - 1, mediumPack.bundleSize, mediumPack.flowerType)
    val flowers = totalFlowers - ((bigPack.bundleQuantity * bigPack.bundleSize) + (mediumPack.bundleQuantity * mediumPack.bundleSize))
    val (packages, remainingFlowers) = wrap(flowers, bundleSizes, mediumPack.flowerType)
    val smallPack = packages.first()

    return Pair(listOf(bigPack, mediumPack, smallPack), remainingFlowers)
  }

  private fun changeBigPackQuantity(packages: List<Package>, totalFlowers: Int, bundleSizes: List<Int>): Pair<List<Package>, Int> {
    var bigPack = packages[0]
    var mediumPack = packages[1]

    bigPack = Package(bigPack.bundleQuantity - 1, bigPack.bundleSize, bigPack.flowerType)
    val flowers = totalFlowers - ((bigPack.bundleQuantity * bigPack.bundleSize) + (mediumPack.bundleQuantity * mediumPack.bundleSize))
    val (packages, remainingFlowers) = wrap(flowers, bundleSizes, bigPack.flowerType)
    mediumPack = packages.first()
    val smallPack = packages.last()

    return Pair(listOf(bigPack, mediumPack, smallPack), remainingFlowers)
  }

  private fun isWrongCombination(remainingFlowers: Int) = remainingFlowers != 0

  private fun wrap(flowers: Int, bundleSizes: List<Int>, type: FlowerType): Pair<List<Package>, Int> {
    val packages = mutableListOf<Package>()
    var remainingFlowers = flowers

    bundleSizes.forEach { bundleSize ->
      packages.add(packageWith(remainingFlowers, bundleSize, type))
      remainingFlowers %= bundleSize
    }
    return Pair(packages, remainingFlowers)
  }

  private fun packageWith(flowersQuantity: Int, bundleSize: Int, type: FlowerType) = Package(bundleQuantity = flowersQuantity / bundleSize, bundleSize, type)
}
