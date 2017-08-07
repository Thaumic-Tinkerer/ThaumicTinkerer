package com.nekokittygames.Thaumic.Tinkerer.common.core.misc

import java.io.{BufferedReader, InputStreamReader}

import com.nekokittygames.Thaumic.Tinkerer.common.ThaumicTinkerer

import scala.io.Source
import scala.util.Random

/**
 * Created by katsw on 27/05/2015.
 */
object StringID {

  def getName():String=
  {
    val adjectivesFile = Source.fromInputStream(Source.getClass.getResourceAsStream("/assets/thaumictinkerer/misc/adjectives"),"UTF-8").getLines().toArray

    val animalsFile = Source.fromInputStream(Source.getClass().getResourceAsStream("/assets/thaumictinkerer/misc/animals"), "UTF-8").getLines().toArray

    val random=new Random()

    var num=random.nextInt(adjectivesFile.size-1)

    var output=adjectivesFile(num).substring(0,1).toUpperCase+adjectivesFile(num).substring(1)

    num=random.nextInt(adjectivesFile.size-1)

    output=output+adjectivesFile(num).substring(0,1).toUpperCase+adjectivesFile(num).substring(1)

    num=random.nextInt(animalsFile.size-1)
    output=output+animalsFile(num).substring(0,1).toUpperCase+animalsFile(num).substring(1)

    output
  }

}
