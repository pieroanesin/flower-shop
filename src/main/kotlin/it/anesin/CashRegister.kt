package it.anesin

interface CashRegister {
  fun invoice(packs: List<Pack>): String
}
