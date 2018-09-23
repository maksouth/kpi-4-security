import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.stream.Stream

class Launcher {}

fun main(args: Array<String>) {
    getFileStream()
}

fun getFileStream(): Stream<String> {
    val classLoader = Launcher::class.java.classLoader
    val file = File(classLoader.getResource("encrypted.txt").file)
    val reader = BufferedReader(FileReader(file))
    return reader.lines()
}