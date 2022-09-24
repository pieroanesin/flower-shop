package it.anesin

class DefaultPackaging: Packaging {

  override fun wrapRoses(quantity: Int): List<Pair<Int, Int>> {
    val bigBundle = 10
    val smallBundle = 5

    val totalBigBundles = quantity / bigBundle
    var remainingFlowers = quantity % bigBundle

    val totalSmallBundle = remainingFlowers / smallBundle
    remainingFlowers %= smallBundle

    if (quantity < smallBundle || remainingFlowers > 0) throw Exception("Roses can't be wrapped")

    return listOf(Pair(totalBigBundles, bigBundle), Pair(totalSmallBundle, smallBundle))
      .filter { it.first > 0 }
  }

  override fun wrapLilies(quantity: Int): List<Pair<Int, Int>> {
    val bigBundle = 9
    val mediumBundle = 6
    val smallBundle = 3

    val totalBigBundles = quantity / bigBundle
    var remainingFlowers = quantity % bigBundle

    val totalMediumBundle = remainingFlowers / mediumBundle
    remainingFlowers %= mediumBundle

    val totalSmallBundle = remainingFlowers / smallBundle
    remainingFlowers %= smallBundle

    return listOf(Pair(totalBigBundles, bigBundle), Pair(totalMediumBundle, mediumBundle), Pair(totalSmallBundle, smallBundle))
      .filter { it.first > 0 }
  }
}
