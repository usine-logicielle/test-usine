package adapter.rest

import org.scalatest._

/**
 * Created by rauricoste on 14/02/14.
 */
class EmptyEntityTest extends FlatSpec with Matchers {

  "plop" should "save id" in {
    val entity = new EmptyEntity(3)
    entity.id should be (3)
  }

}
