package it.anesin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DefaultPackagingTest {
  private var packaging = DefaultPackaging()

  @Test
  internal fun `should wrap some roses`() {
    assertThat(packaging.wrapRoses(5)).isEqualTo(listOf(Pair(1, 5)))
    assertThat(packaging.wrapRoses(10)).isEqualTo(listOf(Pair(1, 10)))
    assertThat(packaging.wrapRoses(15)).isEqualTo(listOf(Pair(1, 10),Pair(1, 5)))
    assertThat(packaging.wrapRoses(55)).isEqualTo(listOf(Pair(5, 10),Pair(1, 5)))
  }
}
