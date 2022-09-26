package it.anesin

import it.anesin.FlowerType.T58
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DefaultCashRegisterTest {
  private var cashRegister = DefaultCashRegister()

  @Test
  internal fun `should invoice a list of packs full of Tulips`() {
    val packs = listOf(Pack(2, 5, T58), Pack(1, 3, T58))

    val invoice = cashRegister.invoice(packs)

    assertThat(invoice).contains("13 T58 $25.85")
    assertThat(invoice).contains("2 x 5 $9.95")
    assertThat(invoice).contains("1 x 3 $5.95")
  }
}
