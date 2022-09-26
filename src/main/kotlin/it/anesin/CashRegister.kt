package it.anesin

interface CashRegister {
  fun invoice(packages: List<Package>): String
}
