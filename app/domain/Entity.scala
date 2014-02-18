package domain

import org.squeryl.KeyedEntity
import scala.util.Random

trait Entity extends KeyedEntity[Long] {

  var id:Long = 0
}
