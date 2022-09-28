package it.anesin

import it.anesin.FlowerType.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DefaultCashRegisterTest {
  private var cashRegister = DefaultCashRegister()

  @Test
  internal fun `should invoice a list of packages full of Roses`() {
    val packages = listOf(Package(1, Bundle(10, 12.99, R12)))

    val invoice = cashRegister.invoice(packages)

    assertThat(invoice).contains("10 R12 $12.99")
    assertThat(invoice).contains("1 x 10 $12.99")
  }

  @Test
  internal fun `should invoice a list of packages full of Lilies`() {
    val packages = listOf(Package(1, Bundle(9, 24.95, L09)), Package(1, Bundle(6, 16.95, L09)))

    val invoice = cashRegister.invoice(packages)

    assertThat(invoice).contains("15 L09 $41.90")
    assertThat(invoice).contains("1 x 9 $24.95")
    assertThat(invoice).contains("1 x 6 $16.95")
  }

  @Test
  internal fun `should invoice a list of packages full of Tulips`() {
    val packages = listOf(Package(2, Bundle(5, 9.95, T58)), Package(1, Bundle(3, 5.95, T58)))

    val invoice = cashRegister.invoice(packages)

    assertThat(invoice).contains("13 T58 $25.85")
    assertThat(invoice).contains("2 x 5 $9.95")
    assertThat(invoice).contains("1 x 3 $5.95")
  }

  @Test
  internal fun `should invoice a list of packages full of flowers`() {
    val packages = listOf(
      Package(1, Bundle(10, 12.99, R12)),
      Package(1, Bundle(9, 24.95, L09)),
      Package(1, Bundle(6, 16.95, L09)),
      Package(2, Bundle(5, 9.95, T58)),
      Package(1, Bundle(3, 5.95, T58))
    )

    val invoice = cashRegister.invoice(packages)

    assertThat(invoice).contains("10 R12 $12.99")
    assertThat(invoice).contains("1 x 10 $12.99")
    assertThat(invoice).contains("15 L09 $41.90")
    assertThat(invoice).contains("1 x 9 $24.95")
    assertThat(invoice).contains("1 x 6 $16.95")
    assertThat(invoice).contains("13 T58 $25.85")
    assertThat(invoice).contains("2 x 5 $9.95")
    assertThat(invoice).contains("1 x 3 $5.95")
  }
}
