import converter.JsonConverter
import domain.Person

/**
 * Created by rauricoste on 03/02/14.
 */
object Launcher {

  final def main(args: Array[String]) = {
    val person = Person("remy", "A")
    val json = JsonConverter.toJson(person)
    System.out.println(json)
  }
}
