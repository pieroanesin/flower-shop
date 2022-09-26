package it.anesin

class DefaultCashRegister: CashRegister {

  override fun invoice(packs: List<Pack>): String {
    var totalFlowers = 0
    var totalPrice = 0.0

    packs.forEach { pack ->
      totalFlowers += (pack.bundleSize * pack.bundleQuantity)
      totalPrice += priceOf(pack)
    }

    var receipt = "$totalFlowers ${packs.first().flowerType} $${String.format("%.2f", totalPrice)}"

    packs.forEach { pack ->
      receipt += "\n\t ${pack.bundleQuantity} x ${pack.bundleSize} $${pack.flowerType.bundleSizePrice[pack.bundleSize]}"
    }

    return receipt
  }

  private fun priceOf(pack: Pack): Double = (pack.flowerType.bundleSizePrice[pack.bundleSize]?.times(pack.bundleQuantity)) ?: 0.0
}
