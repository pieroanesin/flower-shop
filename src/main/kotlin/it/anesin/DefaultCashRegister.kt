package it.anesin

class DefaultCashRegister: CashRegister {
  override fun invoice(packs: List<Pack>): String {
    val tulipsPrice = mapOf(3 to 5.95, 5 to 9.95, 9 to 16.99)
    var totalFlowers = 0
    var totalPrice = 0.0

    packs.forEach { pack ->
      totalFlowers += (pack.bundleSize * pack.bundleQuantity)
      totalPrice += priceOf(pack, tulipsPrice)
    }

    var receipt = "$totalFlowers ${packs.first().type} $${String.format("%.2f", totalPrice)}"

    packs.forEach { pack ->
      receipt += "\n\t ${pack.bundleQuantity} x ${pack.bundleSize} $${tulipsPrice[pack.bundleSize]}"
    }

    return receipt
  }

  private fun priceOf(pack: Pack, prices: Map<Int, Double>): Double = (prices[pack.bundleSize]?.times(pack.bundleQuantity)) ?: 0.0
}
