package vigenere

fun main(args: Array<String>) {
    val key = KeyFinder().key(sequence)
    println("KEY $key")
    Decryptor().decryptAndPrint(key = key, text = sequence)
}