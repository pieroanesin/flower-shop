package it.anesin

interface Packaging {
  fun wrapFlowers(quantity: Int, type: FlowerType): List<Package>
}
