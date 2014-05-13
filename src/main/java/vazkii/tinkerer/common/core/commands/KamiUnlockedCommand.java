package vazkii.tinkerer.common.core.commands;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import org.apache.commons.lang3.StringUtils;
import thaumcraft.common.entities.monster.EntityFireBat;
import thaumcraft.common.entities.monster.EntityGiantBrainyZombie;
import thaumcraft.common.lib.research.ResearchManager;
import vazkii.tinkerer.common.research.ModResearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Katrina on 27/02/14.
 */
public class KamiUnlockedCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "iskamiunlocked";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "iskamiunlocked";
    }
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return true;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {

        List<String> parents= Arrays.asList(ModResearch.kamiResearch.parentsHidden);
        List<String> unlocked= ResearchManager.getResearchForPlayer(icommandsender.getCommandSenderName());
        if(unlocked.containsAll(parents))
            ((EntityPlayer)icommandsender).addChatComponentMessage(new ChatComponentText("Yes"));
        else
        {
            ((EntityPlayer)icommandsender).addChatComponentMessage(new ChatComponentText("No"));
            List<String> leftover=new ArrayList<String>();
            List<String> list = new ArrayList<String> (parents);
            list.removeAll(unlocked);
            ((EntityPlayer)icommandsender).addChatComponentMessage(new ChatComponentText("Remaining: " + StringUtils.join(list, ',')));
            //EntityPlayer player=(EntityPlayer)icommandsender;
            //EntityGiantBrainyZombie bat=new EntityGiantBrainyZombie(player.worldObj);
            //bat.setPositionAndRotation(player.posX,player.posY,player.posZ,1,1);
            //player.worldObj.spawnEntityInWorld(bat);
        }
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p/>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p/>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p/>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p/>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p/>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
