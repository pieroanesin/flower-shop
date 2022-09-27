package it.anesin

class DefaultCashRegister : CashRegister {

  override fun invoice(packages: List<Package>): String {
    var totalFlowers = 0
    var totalPrice = 0.0

    packages.forEach { pack ->
      totalFlowers += pack.totalFlowers()
      totalPrice += pack.price()
    }

    var receipt = "$totalFlowers ${packages.first().flowerType} $${String.format("%.2f", totalPrice)}"

    packages.forEach { pack ->
      receipt += "\n\t ${pack.bundleQuantity} x ${pack.bundle.size} $${pack.bundle.price}"
    }

    return receipt
  }
}
