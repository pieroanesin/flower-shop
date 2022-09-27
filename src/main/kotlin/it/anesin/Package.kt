package it.anesin

data class Package(val bundleQuantity: Int, val bundle: Bundle) {
  fun price() = bundle.price.times(bundleQuantity)
  fun totalFlowers() = bundleQuantity * bundle.size
}
