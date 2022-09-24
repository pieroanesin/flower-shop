package it.anesin

class DefaultPackaging: Packaging {

  override fun wrapRoses(quantity: Int): List<Pair<Int, Int>> {
    val bigBundle = 10
    val smallBundle = 5

    val totalBigBundles = quantity / bigBundle
    val remainingFlowers = quantity % bigBundle
    val totalSmallBundle = remainingFlowers / smallBundle

    return listOf(Pair(totalBigBundles, bigBundle), Pair(totalSmallBundle, smallBundle))
      .filter { it.first > 0 }
  }
}
