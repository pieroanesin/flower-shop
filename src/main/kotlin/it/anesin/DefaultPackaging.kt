package it.anesin

class DefaultPackaging(private val catalogue: Catalogue) : Packaging {

  override fun wrapFlowers(quantity: Int, type: FlowerType): List<Package> {
    if (quantityTooLow(quantity, type)) throw Exception("$quantity ${type.description} is less than the smallest bundle")
    var (packages, remainingFlowers) = wrap(quantity, bundlesOf(type))
    val combinations = if (isGoodCombination(remainingFlowers)) mutableListOf(combinationWith(packages)) else mutableListOf()

    while (tryOtherCombinations(packages)) {
      changePackages(packagesToSave(packages), quantity, bundlesOf(type)).let { (newPackages, newRemainingFlowers) ->
        packages = newPackages
        remainingFlowers = newRemainingFlowers
      }
      if (isGoodCombination(remainingFlowers)) combinations.add(combinationWith(packages))
    }
    if (combinations.isEmpty()) throw Exception("$quantity ${type.description} can't be wrapped")

    return combinationWithMinimalBundlesAndPrice(combinations).filter { it.bundleQuantity > 0 }
  }

  private fun quantityTooLow(quantity: Int, type: FlowerType) = quantity < smallestBundle(bundlesOf(type)).size
  private fun bundlesOf(type: FlowerType) = catalogue.bundlesOf(type).sortedByDescending { it.size }
  private fun isGoodCombination(remainingFlowers: Int) = remainingFlowers == 0
  private fun smallestBundle(bundles: List<Bundle>) = bundles.minBy { it.size }
  private fun tryOtherCombinations(packages: List<Package>) = packagesToChange(packages) > 0
  private fun packagesToSave(packages: List<Package>) = packages.dropLast(packagesToChange(packages))
  private fun packagesToChange(packages: List<Package>) = packages.dropLast(1).sortedBy { it.bundle.size }.indexOfFirst { it.bundleQuantity > 0 } + 1
  private fun combinationWith(packages: List<Package>) = Triple(packages.sumOf { it.bundleQuantity }, packages.sumOf { it.bundle.price * it.bundleQuantity }, packages)

  private fun combinationWithMinimalBundlesAndPrice(combinations: MutableList<Triple<Int, Double, List<Package>>>): List<Package> {
    val lowestQuantityBundle = combinations.minOf { it.first }
    val combinationsWithLessBundles = combinations.filter { it.first == lowestQuantityBundle }
    val lowestPrice = combinationsWithLessBundles.minOf { it.second }
    return combinationsWithLessBundles.first { it.second == lowestPrice }.third
  }

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
