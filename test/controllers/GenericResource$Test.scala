package controllers

import org.scalatest.{Matchers, FlatSpec}
import domain.Person

/**
 * Created by rauricoste on 18/02/14.
 */
class GenericResource$Test extends FlatSpec with Matchers  {

  "generic resource" should "get class" in {
    GenericResource.getClazz("person") should be (classOf[Person])
  }
}
