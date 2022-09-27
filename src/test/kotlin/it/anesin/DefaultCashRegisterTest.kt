package it.anesin

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import it.anesin.FlowerType.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DefaultCashRegisterTest {
  private val catalogue = mockk<Catalogue>()
  private var cashRegister = DefaultCashRegister(catalogue)

  @BeforeEach
  internal fun setUp() {
    every { catalogue.bundlesOf(R12) } returns listOf(Bundle(5, 6.99), Bundle(10, 12.99))
    every { catalogue.bundlesOf(L09) } returns listOf(Bundle(3, 9.95), Bundle(6, 16.95), Bundle(9, 24.95))
    every { catalogue.bundlesOf(T58) } returns listOf(Bundle(3, 5.95), Bundle(5, 9.95), Bundle(9, 16.99))
  }

  @Test
  internal fun `should invoice a list of packages full of Roses`() {
    val packages = listOf(Package(1, 10, R12))

    val invoice = cashRegister.invoice(packages)

    assertThat(invoice).contains("10 R12 $12.99")
    assertThat(invoice).contains("1 x 10 $12.99")
    verify { catalogue.bundlesOf(R12) }
  }

  @Test
  internal fun `should invoice a list of packages full of Lilies`() {
    val packages = listOf(Package(1, 9, L09), Package(1, 6, L09))

    val invoice = cashRegister.invoice(packages)

    assertThat(invoice).contains("15 L09 $41.90")
    assertThat(invoice).contains("1 x 9 $24.95")
    assertThat(invoice).contains("1 x 6 $16.95")
    verify { catalogue.bundlesOf(L09) }
  }

  @Test
  internal fun `should invoice a list of packages full of Tulips`() {
    val packages = listOf(Package(2, 5, T58), Package(1, 3, T58))

    val invoice = cashRegister.invoice(packages)

    assertThat(invoice).contains("13 T58 $25.85")
    assertThat(invoice).contains("2 x 5 $9.95")
    assertThat(invoice).contains("1 x 3 $5.95")
    verify { catalogue.bundlesOf(T58) }
  }

  @Test
  internal fun `should throw an exception if bundle not exist`() {
    val packages = listOf(Package(1, 20, R12))

    assertThatThrownBy { cashRegister.invoice(packages) }.hasMessageContaining("Price for bundle R12 with size 20 not exist")
  }
}
