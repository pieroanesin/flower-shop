package it.anesin

class DefaultPackaging(private val catalogue: Catalogue) : Packaging {

  override fun wrapFlowers(quantity: Int, type: FlowerType): List<Package> {
    val bundles = catalogue.bundlesOf(type).sortedByDescending { it.size }

    if (quantity < smallestBundle(bundles).size) throw Exception("The quantity of ${type.description} is less than the smallest bundle")

    var (packages, remainingFlowers) = wrap(quantity, bundles)

    val solutions = if (isGoodCombination(remainingFlowers)) mutableListOf(solutionWith(packages)) else mutableListOf()

    while (tryOtherCombinations(packages)) {
      changePackages(packages.dropLast(packagesToChange(packages)), quantity, bundles)
        .let { (newPackages, newRemainingFlowers) ->
          packages = newPackages
          remainingFlowers = newRemainingFlowers
        }
      if (isGoodCombination(remainingFlowers)) solutions.add(solutionWith(packages))
    }

    if (solutions.isEmpty()) throw Exception("${type.description} can't be wrapped")

    val lowestQuantityBundle = solutions.minOf { it.first }
    val solutionsWithLessBundles = solutions.filter { it.first == lowestQuantityBundle }

    val lowestPrice = solutionsWithLessBundles.minOf { it.second }
    val solutionWithLowestBundleAndPrice = solutionsWithLessBundles.first { it.second == lowestPrice }.third

    return solutionWithLowestBundleAndPrice.filter { it.bundleQuantity > 0 }
  }

  private fun isGoodCombination(remainingFlowers: Int) = remainingFlowers == 0
  private fun smallestBundle(bundles: List<Bundle>) = bundles.minBy { it.size }
  private fun tryOtherCombinations(packages: List<Package>) = packagesToChange(packages) > 0
  private fun packagesToChange(packages: List<Package>) = packages.dropLast(1).sortedBy { it.bundle.size }.indexOfFirst { it.bundleQuantity > 0 } + 1
  private fun solutionWith(packages: List<Package>) = Triple(packages.sumOf { it.bundleQuantity }, packages.sumOf { it.bundle.price * it.bundleQuantity }, packages)

  private fun changePackages(packages: List<Package>, totalFlowers: Int, bundles: List<Bundle>): Pair<List<Package>, Int> {
    val lastPackage = Package(packages.last().bundleQuantity - 1, packages.last().bundle)
    val otherPackages = packages.dropLast(1)
    val flowersAlreadyUsed = lastPackage.totalFlowers() + otherPackages.sumOf { it.totalFlowers() }
    val flowersToUse = totalFlowers - flowersAlreadyUsed
    val bundlesToUse = bundles.drop(packages.size)

    val (newPackages, remainingFlowers) = wrap(flowersToUse, bundlesToUse)

    return Pair(otherPackages + lastPackage + newPackages, remainingFlowers)
  }

  private fun wrap(flowers: Int, bundles: List<Bundle>): Pair<List<Package>, Int> {
    val packages = mutableListOf<Package>()
    var remainingFlowers = flowers

    bundles.forEach { bundle ->
      val (bundleQuantity, restFlowers) = maxBundleQuantity(remainingFlowers, bundle)
      packages.add(Package(bundleQuantity, bundle))
      remainingFlowers = restFlowers
    }

    return Pair(packages, remainingFlowers)
  }

  private fun maxBundleQuantity(remainingFlowers: Int, bundle: Bundle) = Pair(remainingFlowers / bundle.size, remainingFlowers % bundle.size)
}
