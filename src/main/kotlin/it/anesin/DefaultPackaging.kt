package it.anesin

class DefaultPackaging(private val catalogue: Catalogue) : Packaging {

  override fun wrapFlowers(quantity: Int, type: FlowerType): List<Package> {
    val bundles = catalogue.bundlesOf(type).sortedBy { it.size }
    val bundleCounter = initBundleCounter(quantity, bundles)

    if (quantity < smallestBundle(bundles).size) throw Exception("$quantity ${type.description} is less than the smallest bundle")

    for (bundle in bundles) {
      for (numFlowers in 1..quantity) {
        if (numFlowers >= bundle.size) {
          val remainingFlowers = numFlowers - bundle.size
          if (isRemainingFlowersCompositionValid(bundleCounter, remainingFlowers)) {
            when {
              isNewFlowerCompositionSmaller(bundleCounter, numFlowers, remainingFlowers) ->
                bundleCounter[numFlowers] = newFlowerComposition(bundleCounter, remainingFlowers, bundle)
              isNewFlowerCompositionEqual(bundleCounter, numFlowers, remainingFlowers) && isNewFlowerCompositionCheaper(bundleCounter, numFlowers, remainingFlowers, bundle) ->
                bundleCounter[numFlowers] = newFlowerComposition(bundleCounter, remainingFlowers, bundle)
            }
          }
        }
      }
    }

    if (bundleCounter[quantity]!!.sumOf { it.bundleQuantity } >= Int.MAX_VALUE) throw Exception("$quantity ${type.description} can't be wrapped")

    return bundleCounter[quantity]!!.sortedByDescending { it.bundle.size }.filter { it.bundleQuantity > 0 }
  }

  private fun smallestBundle(bundles: List<Bundle>) = bundles.minBy { it.size }

  private fun initBundleCounter(quantity: Int, bundles: List<Bundle>): MutableMap<Int, List<Package>> {
    val bundleCounter = mutableMapOf<Int, List<Package>>()
    bundleCounter[0] = listOf(Package(0, smallestBundle(bundles)))
    for (i in 1..quantity) bundleCounter[i] = listOf(Package(Int.MAX_VALUE, smallestBundle(bundles)))
    return bundleCounter
  }

  private fun isRemainingFlowersCompositionValid(bundleCounter: MutableMap<Int, List<Package>>, remainingFlowers: Int) =
    bundleCounter[remainingFlowers]!!.sumOf { it.bundleQuantity } != Int.MAX_VALUE

  private fun isNewFlowerCompositionSmaller(bundleCounter: MutableMap<Int, List<Package>>, actualFlowers: Int, remainingFlowers: Int) =
    bundleCounter[actualFlowers]!!.sumOf { it.bundleQuantity } > bundleCounter[remainingFlowers]!!.sumOf { it.bundleQuantity } + 1

  private fun isNewFlowerCompositionEqual(bundleCounter: MutableMap<Int, List<Package>>, actualFlowers: Int, remainingFlowers: Int) =
    bundleCounter[actualFlowers]!!.sumOf { it.bundleQuantity } == bundleCounter[remainingFlowers]!!.sumOf { it.bundleQuantity } + 1

  private fun isNewFlowerCompositionCheaper(bundleCounter: MutableMap<Int, List<Package>>, numFlowers: Int, remainingFlowers: Int, bundle: Bundle) =
    bundleCounter[numFlowers]!!.sumOf { it.price() } > bundleCounter[remainingFlowers]!!.sumOf { it.price() } + bundle.price

  private fun newFlowerComposition(bundleCounter: MutableMap<Int, List<Package>>, remainingFlowers: Int, bundle: Bundle) =
    bundleCounter[remainingFlowers]!!
      .filterNot { it.bundle == bundle }
      .plus(Package((bundleCounter[remainingFlowers]!!.singleOrNull { it.bundle == bundle }?.bundleQuantity ?: 0) + 1, bundle))
}
