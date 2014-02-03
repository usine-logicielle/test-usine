package domain

import org.squeryl.KeyedEntity
import scala.util.Random

/**
 * Created by rauricoste on 03/02/14.
 */
trait Entity extends KeyedEntity[Long] {

  var id:Long = 0
}
