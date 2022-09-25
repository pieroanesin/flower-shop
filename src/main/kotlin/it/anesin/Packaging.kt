package it.anesin

interface Packaging {
  fun wrapRoses(quantity: Int): List<Pack>
  fun wrapLilies(quantity: Int): List<Pack>
  fun wrapTulips(quantity: Int): List<Pack>
}
