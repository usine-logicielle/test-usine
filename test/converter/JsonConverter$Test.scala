package converter

import org.scalatest.{Matchers, FlatSpec, FunSuite}
import adapter.rest.EmptyEntity

/**
 * Created by rauricoste on 18/02/14.
 */
class JsonConverter$Test extends FlatSpec with Matchers  {

  "json converter" should "convert json" in {
    val entity = new EmptyEntity(3)
    JsonConverter.fromJson(classOf[EmptyEntity])(JsonConverter.toJson(entity)) should be (entity)
  }
}
