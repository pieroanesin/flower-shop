package it.anesin

class DefaultCashRegister: CashRegister {

  override fun invoice(packs: List<Pack>): String {
    var totalFlowers = 0
    var totalPrice = 0.0

    packs.forEach { pack ->
      totalFlowers += pack.bundleSize * pack.bundleQuantity
      totalPrice += priceOf(pack)
    }

    var receipt = "$totalFlowers ${packs.first().flowerType} $${String.format("%.2f", totalPrice)}"

    packs.forEach { pack ->
      receipt += "\n\t ${pack.bundleQuantity} x ${pack.bundleSize} $${bundlePrice(pack)}"
    }

    return receipt
  }

  private fun priceOf(pack: Pack): Double = (bundlePrice(pack).times(pack.bundleQuantity))
  private fun bundlePrice(pack: Pack) = pack.flowerType.bundleSizePrice[pack.bundleSize] ?: throw Exception("Price for bundle ${pack.flowerType} with size ${pack.bundleSize} not exist")
}
