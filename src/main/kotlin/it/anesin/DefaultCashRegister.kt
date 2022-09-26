package it.anesin

class DefaultCashRegister: CashRegister {

  override fun invoice(packages: List<Package>): String {
    var totalFlowers = 0
    var totalPrice = 0.0

    packages.forEach { pack ->
      totalFlowers += pack.bundleSize * pack.bundleQuantity
      totalPrice += priceOf(pack)
    }

    var receipt = "$totalFlowers ${packages.first().flowerType} $${String.format("%.2f", totalPrice)}"

    packages.forEach { pack ->
      receipt += "\n\t ${pack.bundleQuantity} x ${pack.bundleSize} $${bundlePrice(pack)}"
    }

    return receipt
  }

  private fun priceOf(pack: Package): Double = (bundlePrice(pack).times(pack.bundleQuantity))
  private fun bundlePrice(pack: Package) = pack.flowerType.bundleSizePrice[pack.bundleSize] ?: throw Exception("Price for bundle ${pack.flowerType} with size ${pack.bundleSize} not exist")
}
