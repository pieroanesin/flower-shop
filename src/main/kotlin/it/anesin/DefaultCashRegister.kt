package it.anesin

class DefaultCashRegister : CashRegister {

  override fun invoice(packages: List<Package>) = FlowerType.values()
    .map { flowerType -> packagesOf(flowerType, packages) }
    .filter { filteredPackages -> filteredPackages.isNotEmpty() }
    .map { filteredPackages -> sort(filteredPackages) }
    .map { sortedPackages -> invoiceByType(sortedPackages) }
    .joinToString("\n")

  private fun packagesOf(flowerType: FlowerType, packages: List<Package>) = packages.filter { it.bundle.type == flowerType }
  private fun sort(packagesSingleType: List<Package>) = packagesSingleType.sortedByDescending { pack -> pack.bundle.size }

  private fun invoiceByType(packages: List<Package>): String? {
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
