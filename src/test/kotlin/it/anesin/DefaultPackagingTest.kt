package it.anesin

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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

  @Test
  internal fun `should throw an exception if quantity of roses can't be wrapped`() {
    assertThatThrownBy { packaging.wrapRoses(0) }.hasMessageContaining("Roses can't be wrapped")
    assertThatThrownBy { packaging.wrapRoses(-5) }.hasMessageContaining("Roses can't be wrapped")
    assertThatThrownBy { packaging.wrapRoses(2) }.hasMessageContaining("Roses can't be wrapped")
    assertThatThrownBy { packaging.wrapRoses(7) }.hasMessageContaining("Roses can't be wrapped")
  }

  @Test
  internal fun `should wrap some lilies`() {
    assertThat(packaging.wrapLilies(3)).isEqualTo(listOf(Pair(1, 3)))
    assertThat(packaging.wrapLilies(6)).isEqualTo(listOf(Pair(1, 6)))
    assertThat(packaging.wrapLilies(9)).isEqualTo(listOf(Pair(1, 9)))
    assertThat(packaging.wrapLilies(12)).isEqualTo(listOf(Pair(1, 9),Pair(1, 3)))
    assertThat(packaging.wrapLilies(15)).isEqualTo(listOf(Pair(1, 9),Pair(1, 6)))
    assertThat(packaging.wrapLilies(18)).isEqualTo(listOf(Pair(2, 9)))
  }

  @Test
  internal fun `should throw an exception if quantity of lilies can't be wrapped`() {
    assertThatThrownBy { packaging.wrapLilies(0) }.hasMessageContaining("Lilies can't be wrapped")
    assertThatThrownBy { packaging.wrapLilies(-5) }.hasMessageContaining("Lilies can't be wrapped")
    assertThatThrownBy { packaging.wrapLilies(2) }.hasMessageContaining("Lilies can't be wrapped")
    assertThatThrownBy { packaging.wrapLilies(7) }.hasMessageContaining("Lilies can't be wrapped")
  }

  @Test
  internal fun `should wrap some tulips`() {
    assertThat(packaging.wrapTulips(3)).isEqualTo(listOf(Pair(1, 3)))
    assertThat(packaging.wrapTulips(5)).isEqualTo(listOf(Pair(1, 5)))
    assertThat(packaging.wrapTulips(6)).isEqualTo(listOf(Pair(2, 3)))
    assertThat(packaging.wrapTulips(9)).isEqualTo(listOf(Pair(1, 9)))
    assertThat(packaging.wrapTulips(10)).isEqualTo(listOf(Pair(2, 5)))
    assertThat(packaging.wrapTulips(13)).isEqualTo(listOf(Pair(2, 5), Pair(1, 3)))
    assertThat(packaging.wrapTulips(18)).isEqualTo(listOf(Pair(2, 9)))
    assertThat(packaging.wrapTulips(48)).isEqualTo(listOf(Pair(5, 9), Pair(1, 3)))
  }
}
