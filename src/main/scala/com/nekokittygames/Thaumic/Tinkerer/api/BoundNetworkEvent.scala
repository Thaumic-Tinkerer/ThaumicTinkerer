package com.nekokittygames.Thaumic.Tinkerer.api

import net.minecraftforge.fml.common.eventhandler.Event


/**
  * Created by katsw on 08/08/2017.
  */
class BoundNetworkEvent(id:String) extends Event{
    var network=id
}
class BoundNetworkAddedEvent(id:String) extends BoundNetworkEvent(id)
{

}
class BoundNetworkChangedEvent(id:String) extends BoundNetworkEvent(id)
{

}