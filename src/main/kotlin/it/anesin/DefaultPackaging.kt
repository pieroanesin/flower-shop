package it.anesin

class DefaultPackaging(private val catalogue: Catalogue) : Packaging {

  override fun wrapFlowers(quantity: Int, type: FlowerType): List<Package> {
    val bundles = catalogue.bundlesOf(type).sortedByDescending { it.size }
    var (packages, remainingFlowers) = wrap(quantity, bundles, type)

    while (isWrongCombination(remainingFlowers) && (packages[0].bundleQuantity > 0 || packages[1].bundleQuantity > 0)) {
      while (isWrongCombination(remainingFlowers) && packages[1].bundleQuantity > 0) {
        changeMediumPackQuantity(packages, quantity, bundles).let { result ->
          packages = result.first
          remainingFlowers = result.second
        }
      }
      if (isWrongCombination(remainingFlowers) && packages[0].bundleQuantity > 0) {
        changeBigPackQuantity(packages, quantity, bundles).let { result ->
          packages = result.first
          remainingFlowers = result.second
        }
      }
    }

    if (quantity < smallestBundle(bundles).size || remainingFlowers > 0) throw Exception("${type.description} can't be wrapped")

    return packages.filter { it.bundleQuantity > 0 }
  }

  private fun changeMediumPackQuantity(packages: List<Package>, totalFlowers: Int, bundles: List<Bundle>): Pair<List<Package>, Int> {
    val bigPack = packages[0]
    var mediumPack = packages[1]

    mediumPack = Package(mediumPack.flowerType, mediumPack.bundleQuantity - 1, mediumPack.bundle)
    val flowers = totalFlowers - (bigPack.totalFlowers() + mediumPack.totalFlowers())
    val (newPackages, remainingFlowers) = wrap(flowers, listOf(smallestBundle(bundles)), mediumPack.flowerType)
    val smallPack = newPackages.first()

    return Pair(listOf(bigPack, mediumPack, smallPack), remainingFlowers)
  }

  private fun changeBigPackQuantity(packages: List<Package>, totalFlowers: Int, bundles: List<Bundle>): Pair<List<Package>, Int> {
    var bigPack = packages[0]
    var mediumPack = packages[1]

    bigPack = Package(bigPack.flowerType, bigPack.bundleQuantity - 1, bigPack.bundle)
    val flowers = totalFlowers - (bigPack.totalFlowers() + mediumPack.totalFlowers())
    val (newPackages, remainingFlowers) = wrap(flowers, bundlesWithoutBigger(bundles), bigPack.flowerType)
    mediumPack = newPackages.first()
    val smallPack = newPackages.last()

    return Pair(listOf(bigPack, mediumPack, smallPack), remainingFlowers)
  }

  private fun wrap(flowers: Int, bundles: List<Bundle>, type: FlowerType): Pair<List<Package>, Int> {
    val packages = mutableListOf<Package>()
    var remainingFlowers = flowers

    bundles.forEach { bundle ->
      packages.add(Package(type, bundleQuantity = remainingFlowers / bundle.size, bundle))
      remainingFlowers %= bundle.size
    }
    return Pair(packages, remainingFlowers)
  }

  private fun isWrongCombination(remainingFlowers: Int) = remainingFlowers != 0
  private fun smallestBundle(bundles: List<Bundle>) = bundles.minBy { it.size }
  private fun bundlesWithoutBigger(bundles: List<Bundle>) = bundles.drop(1)
}
