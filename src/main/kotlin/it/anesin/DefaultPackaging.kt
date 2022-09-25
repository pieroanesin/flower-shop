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

  private fun totalBundles(flowers: Int, size: Int): Pair<Int, Int> {
    val quantity = flowers / size
    return Pair(quantity, size)
  }

  override fun wrapLilies(quantity: Int): List<Pair<Int, Int>> {
    val bigBundle = 9
    val mediumBundle = 6
    val smallBundle = 3

    val totalBigBundles = quantity / bigBundle
    var remainingFlowers = quantity % bigBundle

    val totalMediumBundles = remainingFlowers / mediumBundle
    remainingFlowers %= mediumBundle

    val totalSmallBundle = remainingFlowers / smallBundle
    remainingFlowers %= smallBundle

    if (quantity < smallBundle || remainingFlowers > 0) throw Exception("Lilies can't be wrapped")

    return listOf(Pair(totalBigBundles, bigBundle), Pair(totalMediumBundles, mediumBundle), Pair(totalSmallBundle, smallBundle))
      .filter { it.first > 0 }
  }

  override fun wrapTulips(quantity: Int): List<Pair<Int, Int>> {
    val bigBundle = 9
    val mediumBundle = 5
    val smallBundle = 3

    var totalBigBundles = quantity / bigBundle
    var remainingFlowers = quantity % bigBundle

    var totalMediumBundles = remainingFlowers / mediumBundle
    remainingFlowers %= mediumBundle

    var totalSmallBundles = remainingFlowers / smallBundle
    remainingFlowers %= smallBundle

    if (remainingFlowers != 0) {
      if (totalBigBundles < mediumBundle) {
        totalBigBundles = 0
        remainingFlowers = quantity
      }
      else {
        totalBigBundles -= (totalBigBundles % mediumBundle)
        remainingFlowers = quantity - (totalBigBundles * bigBundle)
      }

      totalMediumBundles = remainingFlowers / mediumBundle
      remainingFlowers %= mediumBundle

      totalSmallBundles = remainingFlowers / smallBundle
      remainingFlowers %= smallBundle

      if (remainingFlowers != 0) {
        if (totalMediumBundles < smallBundle) {
          totalMediumBundles = 0
          remainingFlowers = quantity
        }
        else {
          totalMediumBundles -= (totalMediumBundles % smallBundle)
          remainingFlowers = quantity - (totalMediumBundles * mediumBundle)
        }

        totalSmallBundles = remainingFlowers / smallBundle
        remainingFlowers %= smallBundle
      }
    }

    if (quantity < smallBundle || remainingFlowers > 0) throw Exception("Tulips can't be wrapped")

    return listOf(Pair(totalBigBundles, bigBundle), Pair(totalMediumBundles, mediumBundle), Pair(totalSmallBundles, smallBundle))
      .filter { it.first > 0 }
  }
}
