package vazkii.tinkerer.common.core.helper;

import thaumcraft.api.aspects.Aspect;

import java.util.ArrayList;

public class NumericAspectHelper {

	public static void init(){

		new NumericAspectHelper(Aspect.WATER);
		new NumericAspectHelper(Aspect.MAN);
		new NumericAspectHelper(Aspect.AIR);
		new NumericAspectHelper(Aspect.FLIGHT);
		new NumericAspectHelper(Aspect.FIRE);
		new NumericAspectHelper(Aspect.MAGIC);
		new NumericAspectHelper(Aspect.UNDEAD);
		new NumericAspectHelper(Aspect.FLESH);
		new NumericAspectHelper(Aspect.BEAST);
		new NumericAspectHelper(Aspect.POISON);
		new NumericAspectHelper(Aspect.EARTH);
		new NumericAspectHelper(Aspect.ELDRITCH);
		new NumericAspectHelper(Aspect.TRAVEL);
		new NumericAspectHelper(Aspect.METAL);
		new NumericAspectHelper(Aspect.SLIME);


	}

	private Aspect aspect;
	public int num;

	private static int nextNum=0;

	public static ArrayList<NumericAspectHelper> values = new ArrayList<NumericAspectHelper>();

	public Aspect getAspect(){
		return aspect;
	}

	public static int getNumber(Aspect aspect){
		for(NumericAspectHelper e: NumericAspectHelper.values){
			if(e.getAspect().equals(aspect)){
				return e.num;
			}
		}

		return -1;
	}

	public static Aspect getAspect(int i){
		return NumericAspectHelper.values.get(i).getAspect();
	}

	NumericAspectHelper(Aspect aspect){
		this.aspect = aspect;
		this.num=nextNum;
		nextNum++;
		values.add(this);
	}

}
