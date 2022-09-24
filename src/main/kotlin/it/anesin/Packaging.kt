package it.anesin

interface Packaging {
  fun wrapRoses(quantity: Int): List<Pair<Int, Int>>
  fun wrapLilies(quantity: Int): List<Pair<Int, Int>>
  fun wrapTulips(quantity: Int): List<Pair<Int, Int>>
}
