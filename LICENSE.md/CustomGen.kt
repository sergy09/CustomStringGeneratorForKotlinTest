import io.kotlintest.properties.Gen
import java.util.*

enum class StringType(val charRange: CharRange) {
    LOWER_CASE_CHARS(CharRange('a', 'z')),
    UPPER_CASE_CHARS(CharRange('A', 'Z')),
    NUMERIC_CASE_CHARS(CharRange('0', '9')),
    SPECIAL_CASE_CHARS(CharRange('!', '/'))
}

object CustomGen {
    private var result = ""

    fun generate(randomOrder: Boolean = false): String {
        return when {
            !randomOrder -> {
                val finalResult = result
                result = ""
                finalResult
            }
            else -> {
                val finalResult = result.toMutableList().shuffled().joinToString("")
                result = ""
                finalResult
            }
        }
    }

    fun cutomRangeStr(stringType: StringType, minSize: Int = 0, maxSize: Int = Random().nextInt(100)): CustomGen {
        if (minSize == maxSize) {
            assert(minSize < maxSize) { "minSize & maxSize must be > 0" }
            for (i in 1..maxSize) {
                result += Gen.oneOf(stringType.charRange.toList()).generate().toString()
            }
        } else {
            assert(minSize < maxSize) { "minSize must be < maxSize" }
            if (minSize < maxSize) {
                val sizeOfSinglePartGenerator = Gen.choose(minSize, maxSize).generate()
                for (i in 0..sizeOfSinglePartGenerator) {
                    result += Gen.oneOf(stringType.charRange.toList()).generate().toString()
                }
            } else {
                result += Gen.oneOf(stringType.charRange.toList()).generate().toString()
            }
        }
        return this
    }

    fun cutomRangeStr(charRange: CharRange, minSize: Int = 0, maxSize: Int = Random().nextInt(100)): CustomGen {
        if (minSize == maxSize) {
            assert(minSize < maxSize) { "minSize & maxSize must be > 0" }
            for (i in 1..maxSize) {
                result += Gen.oneOf(charRange.toList()).generate().toString()
            }
        } else {
            assert(minSize < maxSize) { "minSize must be < maxSize" }
            if (minSize < maxSize) {
                val sizeOfSinglePartGenerator = Gen.choose(minSize, maxSize).generate()
                for (i in 0..sizeOfSinglePartGenerator) {
                    result += Gen.oneOf(charRange.toList()).generate().toString()
                }
            } else {
                result += Gen.oneOf(charRange.toList()).generate().toString()
            }
        }
        return this
    }
}
