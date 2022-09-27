package it.anesin

import io.mockk.every
import io.mockk.mockk
import it.anesin.FlowerType.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DefaultPackagingTest {
  private val catalogue = mockk<Catalogue>()
  private var packaging = DefaultPackaging(catalogue)

  @BeforeEach
  internal fun setUp() {
    every { catalogue.bundleSizesOf(R12) } returns listOf(5, 10)
    every { catalogue.bundleSizesOf(L09) } returns listOf(3, 6, 9)
    every { catalogue.bundleSizesOf(T58) } returns listOf(3, 5, 9)
  }

  @Test
  internal fun `should wrap some roses`() {
    assertThat(packaging.wrapFlowers(5, R12)).isEqualTo(listOf(Package(1, 5, R12)))
    assertThat(packaging.wrapFlowers(10, R12)).isEqualTo(listOf(Package(1, 10, R12)))
    assertThat(packaging.wrapFlowers(15, R12)).isEqualTo(listOf(Package(1, 10, R12), Package(1, 5, R12)))
    assertThat(packaging.wrapFlowers(55, R12)).isEqualTo(listOf(Package(5, 10, R12), Package(1, 5, R12)))
  }

  @Test
  internal fun `should throw an exception if quantity of roses can't be wrapped`() {
    assertThatThrownBy { packaging.wrapFlowers(0, R12) }.hasMessageContaining("Roses can't be wrapped")
    assertThatThrownBy { packaging.wrapFlowers(-5, R12) }.hasMessageContaining("Roses can't be wrapped")
    assertThatThrownBy { packaging.wrapFlowers(2, R12) }.hasMessageContaining("Roses can't be wrapped")
    assertThatThrownBy { packaging.wrapFlowers(7, R12) }.hasMessageContaining("Roses can't be wrapped")
  }

  @Test
  internal fun `should wrap some lilies`() {
    assertThat(packaging.wrapFlowers(3, L09)).isEqualTo(listOf(Package(1, 3, L09)))
    assertThat(packaging.wrapFlowers(6, L09)).isEqualTo(listOf(Package(1, 6, L09)))
    assertThat(packaging.wrapFlowers(9, L09)).isEqualTo(listOf(Package(1, 9, L09)))
    assertThat(packaging.wrapFlowers(12, L09)).isEqualTo(listOf(Package(1, 9, L09), Package(1, 3, L09)))
    assertThat(packaging.wrapFlowers(15, L09)).isEqualTo(listOf(Package(1, 9, L09), Package(1, 6, L09)))
    assertThat(packaging.wrapFlowers(18, L09)).isEqualTo(listOf(Package(2, 9, L09)))
  }

  @Test
  internal fun `should throw an exception if quantity of lilies can't be wrapped`() {
    assertThatThrownBy { packaging.wrapFlowers(0, L09) }.hasMessageContaining("Lilies can't be wrapped")
    assertThatThrownBy { packaging.wrapFlowers(-5, L09) }.hasMessageContaining("Lilies can't be wrapped")
    assertThatThrownBy { packaging.wrapFlowers(2, L09) }.hasMessageContaining("Lilies can't be wrapped")
    assertThatThrownBy { packaging.wrapFlowers(7, L09) }.hasMessageContaining("Lilies can't be wrapped")
  }

  @Test
  internal fun `should wrap some tulips`() {
    assertThat(packaging.wrapFlowers(3, T58)).isEqualTo(listOf(Package(1, 3, T58)))
    assertThat(packaging.wrapFlowers(5, T58)).isEqualTo(listOf(Package(1, 5, T58)))
    assertThat(packaging.wrapFlowers(6, T58)).isEqualTo(listOf(Package(2, 3, T58)))
    assertThat(packaging.wrapFlowers(9, T58)).isEqualTo(listOf(Package(1, 9, T58)))
    assertThat(packaging.wrapFlowers(10, T58)).isEqualTo(listOf(Package(2, 5, T58)))
    assertThat(packaging.wrapFlowers(13, T58)).isEqualTo(listOf(Package(2, 5, T58), Package(1, 3, T58)))
    assertThat(packaging.wrapFlowers(18, T58)).isEqualTo(listOf(Package(2, 9, T58)))
    assertThat(packaging.wrapFlowers(43, T58)).isEqualTo(listOf(Package(3, 9, T58), Package(2, 5, T58), Package(2, 3, T58)))
    assertThat(packaging.wrapFlowers(46, T58)).isEqualTo(listOf(Package(4, 9, T58), Package(2, 5, T58)))
    assertThat(packaging.wrapFlowers(48, T58)).isEqualTo(listOf(Package(5, 9, T58), Package(1, 3, T58)))
    assertThat(packaging.wrapFlowers(50, T58)).isEqualTo(listOf(Package(5, 9, T58), Package(1, 5, T58)))
  }

  @Test
  internal fun `should throw an exception if quantity of tulips can't be wrapped`() {
    assertThatThrownBy { packaging.wrapFlowers(0, T58) }.hasMessageContaining("Tulips can't be wrapped")
    assertThatThrownBy { packaging.wrapFlowers(-5, T58) }.hasMessageContaining("Tulips can't be wrapped")
    assertThatThrownBy { packaging.wrapFlowers(2, T58) }.hasMessageContaining("Tulips can't be wrapped")
    assertThatThrownBy { packaging.wrapFlowers(7, T58) }.hasMessageContaining("Tulips can't be wrapped")
  }
}
