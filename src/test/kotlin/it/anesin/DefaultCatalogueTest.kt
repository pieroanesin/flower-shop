package it.anesin

import it.anesin.FlowerType.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DefaultCatalogueTest {
  private val catalogue = DefaultCatalogue()

  @Test
  internal fun `should return the bundles of roses in catalog`() {
    val bundles = catalogue.bundlesOf(R12)

    assertThat(bundles).isEqualTo(listOf(Bundle(5, 6.99, R12), Bundle(10, 12.99, R12)))
  }

  @Test
  internal fun `should return the bundles of lilies in catalog`() {
    val bundles = catalogue.bundlesOf(L09)

    assertThat(bundles).isEqualTo(listOf(Bundle(3, 9.95, L09), Bundle(6, 16.95, L09), Bundle(9, 24.95, L09)))
  }

  @Test
  internal fun `should return the bundles of tulips in catalog`() {
    val bundles = catalogue.bundlesOf(T58)

    assertThat(bundles).isEqualTo(listOf(Bundle(3, 5.95, T58), Bundle(5, 9.95, T58), Bundle(9, 16.99, T58)))
  }
}
