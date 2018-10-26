# Personally-defined string generator for Property-Based Testing Kotlin

## Requirements

This file uses the testing framework kotlintest, so it is necessary to add it to our gradle
```
testImplementation 'io.kotlintest:kotlintest:2.0.7'
```

## How to use
### The best way to explain it is with an example

In this example I'm going to create a password generator that:
* Contain between 6 and 8 characters included
* Have at least 1 uppercase character
* Have at least 1 lowercase character
* Have at least 1 numeric character
* Have at least 1 special character

```
class PasswordGenerator : Gen<String> {
        override fun generate(): String {
            return ensureItHasProperSize(CustomGen
                    .cutomRangeStr(LOWER_CASE_CHARS, 1, 2)
                    .cutomRangeStr(UPPER_CASE_CHARS, 1, 2)
                    .cutomRangeStr(NUMERIC_CASE_CHARS, 1, 2)
                    .cutomRangeStr(SPECIAL_CASE_CHARS, 1, 2)
                    .generate(true)
            )
        }

        //This method is responsible for preparing the result obtained by our generator 
        to return a final result between 6 and 8 characters
        
        private fun ensureItHasProperSize(firstCandidate: String): String = when {
            firstCandidate.length < 6 -> {
                val charactersToAdd = Gen.choose(6, 8).generate() - firstCandidate.length
                firstCandidate + Gen.string().nextPrintableString(charactersToAdd)
            }
            firstCandidate.length > 8 -> firstCandidate.substring(0, 8)
            else -> firstCandidate
        }
    }
```

Our Test:

```
class LoginTest : ShouldSpec() {

    init {
        "Login" {
            should("should be correct password") {
                forAll(Generators.PasswordGenerator()) { password ->
                    //This is the class that we are going to test
                    val login = Login()

                    //This is the method that check if the random password is correct
                    login.check(password)
                }
            }

        }
    }

}
```

### Notes
Taking into account the following example
```
CustomGen.cutomRangeStr(LOWER_CASE_CHARS,  1,  2).generate(true)
```                   
* customRangeSrt()  ---> Allows you to nest your random strings with your custom range
* LOWER_CASE_CHARS  ---> This parameter filters by the type of string, you can add your own too, apart from the ones that already exist
* 1                 ---> This parameter is for the minimum number of characters, by default is 0
* 2                 ---> This parameter is for the maximum number of characters, by default is a random number between 1 and 100
* generate()        ---> Returns the result of the string in the same order that it has been built
* generate(true)    ---> Returns the result of the string in the random order that it has been built 

                              
