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
    every { catalogue.bundlesOf(R12) } returns listOf(Bundle(5, 6.99, R12), Bundle(10, 12.99, R12))
    every { catalogue.bundlesOf(L09) } returns listOf(Bundle(3, 9.95, L09), Bundle(6, 16.95, L09), Bundle(9, 24.95, L09))
    every { catalogue.bundlesOf(T58) } returns listOf(Bundle(3, 5.95, T58), Bundle(5, 9.95, T58), Bundle(9, 16.99, T58))
  }

  @Test
  internal fun `should wrap some roses`() {
    val bundleFive = Bundle(5, 6.99, R12)
    val bundleTen = Bundle(10, 12.99, R12)

    assertThat(packaging.wrapFlowers(5, R12)).isEqualTo(listOf(Package(1, bundleFive)))
    assertThat(packaging.wrapFlowers(10, R12)).isEqualTo(listOf(Package(1, bundleTen)))
    assertThat(packaging.wrapFlowers(15, R12)).isEqualTo(listOf(Package(1, bundleTen), Package(1, bundleFive)))
    assertThat(packaging.wrapFlowers(55, R12)).isEqualTo(listOf(Package(5, bundleTen), Package(1, bundleFive)))
  }

  @Test
  internal fun `should throw an exception if quantity of roses can't be wrapped`() {
    assertThatThrownBy { packaging.wrapFlowers(0, R12) }.hasMessageContaining("The quantity of Roses is less than the smallest bundle")
    assertThatThrownBy { packaging.wrapFlowers(-5, R12) }.hasMessageContaining("The quantity of Roses is less than the smallest bundle")
    assertThatThrownBy { packaging.wrapFlowers(2, R12) }.hasMessageContaining("The quantity of Roses is less than the smallest bundle")
    assertThatThrownBy { packaging.wrapFlowers(7, R12) }.hasMessageContaining("Roses can't be wrapped")
  }

  @Test
  internal fun `should wrap some lilies`() {
    val bundleThree = Bundle(3, 9.95, L09)
    val bundleSix = Bundle(6, 16.95, L09)
    val bundleNine = Bundle(9, 24.95, L09)

    assertThat(packaging.wrapFlowers(3, L09)).isEqualTo(listOf(Package(1, bundleThree)))
    assertThat(packaging.wrapFlowers(6, L09)).isEqualTo(listOf(Package(1, bundleSix)))
    assertThat(packaging.wrapFlowers(9, L09)).isEqualTo(listOf(Package(1, bundleNine)))
    assertThat(packaging.wrapFlowers(12, L09)).isEqualTo(listOf(Package(2, bundleSix)))
    assertThat(packaging.wrapFlowers(15, L09)).isEqualTo(listOf(Package(1, bundleNine), Package(1, bundleSix)))
    assertThat(packaging.wrapFlowers(18, L09)).isEqualTo(listOf(Package(2, bundleNine)))
  }

  @Test
  internal fun `should throw an exception if quantity of lilies can't be wrapped`() {
    assertThatThrownBy { packaging.wrapFlowers(0, L09) }.hasMessageContaining("The quantity of Lilies is less than the smallest bundle")
    assertThatThrownBy { packaging.wrapFlowers(-5, L09) }.hasMessageContaining("The quantity of Lilies is less than the smallest bundle")
    assertThatThrownBy { packaging.wrapFlowers(2, L09) }.hasMessageContaining("The quantity of Lilies is less than the smallest bundle")
    assertThatThrownBy { packaging.wrapFlowers(7, L09) }.hasMessageContaining("Lilies can't be wrapped")
  }

  @Test
  internal fun `should wrap some tulips`() {
    val bundleThree = Bundle(3, 5.95, T58)
    val bundleFive = Bundle(5, 9.95, T58)
    val bundleNine = Bundle(9, 16.99, T58)

    assertThat(packaging.wrapFlowers(3, T58)).isEqualTo(listOf(Package(1, bundleThree)))
    assertThat(packaging.wrapFlowers(5, T58)).isEqualTo(listOf(Package(1, bundleFive)))
    assertThat(packaging.wrapFlowers(6, T58)).isEqualTo(listOf(Package(2, bundleThree)))
    assertThat(packaging.wrapFlowers(9, T58)).isEqualTo(listOf(Package(1, bundleNine)))
    assertThat(packaging.wrapFlowers(10, T58)).isEqualTo(listOf(Package(2, bundleFive)))
    assertThat(packaging.wrapFlowers(13, T58)).isEqualTo(listOf(Package(2, bundleFive), Package(1, bundleThree)))
    assertThat(packaging.wrapFlowers(18, T58)).isEqualTo(listOf(Package(2, bundleNine)))
    assertThat(packaging.wrapFlowers(43, T58)).isEqualTo(listOf(Package(3, bundleNine), Package(2, bundleFive), Package(2, bundleThree)))
    assertThat(packaging.wrapFlowers(46, T58)).isEqualTo(listOf(Package(4, bundleNine), Package(2, bundleFive)))
    assertThat(packaging.wrapFlowers(48, T58)).isEqualTo(listOf(Package(5, bundleNine), Package(1, bundleThree)))
    assertThat(packaging.wrapFlowers(50, T58)).isEqualTo(listOf(Package(5, bundleNine), Package(1, bundleFive)))
  }

  @Test
  internal fun `should throw an exception if quantity of tulips can't be wrapped`() {
    assertThatThrownBy { packaging.wrapFlowers(0, T58) }.hasMessageContaining("The quantity of Tulips is less than the smallest bundle")
    assertThatThrownBy { packaging.wrapFlowers(-5, T58) }.hasMessageContaining("The quantity of Tulips is less than the smallest bundle")
    assertThatThrownBy { packaging.wrapFlowers(2, T58) }.hasMessageContaining("The quantity of Tulips is less than the smallest bundle")
    assertThatThrownBy { packaging.wrapFlowers(7, T58) }.hasMessageContaining("Tulips can't be wrapped")
  }
}
