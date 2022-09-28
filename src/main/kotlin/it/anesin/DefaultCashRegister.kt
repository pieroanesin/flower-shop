package it.anesin

class DefaultCashRegister : CashRegister {

  override fun invoice(packages: List<Package>): String {
    var finalReceipt = ""
    val flowerTypes = FlowerType.values()

    flowerTypes.map { flowerType ->
      packages.filter { it.bundle.type == flowerType }
    }
      .map {
        finalReceipt += pippo(it) + "\n"
      }

    println(finalReceipt)
    return finalReceipt
  }

  private fun pippo(packages: List<Package>): String {
    var totalFlowers = 0
    var totalPrice = 0.0

    packages.forEach { pack ->
      totalFlowers += pack.totalFlowers()
      totalPrice += pack.price()
    }

    var receipt = "$totalFlowers ${packages.first().bundle.type} $${String.format("%.2f", totalPrice)}"

    packages.forEach { pack ->
      receipt += "\n\t ${pack.bundleQuantity} x ${pack.bundle.size} $${pack.bundle.price}"
    }

    return receipt
  }
}
