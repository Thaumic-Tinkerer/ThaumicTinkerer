package vazkii.tinkerer.common.core.helper;

import thaumcraft.api.aspects.Aspect;

import java.util.ArrayList;

public class NumericAspectHelper {

	public static void init(){
		for(Aspect a:Aspect.getCompoundAspects()){
			new NumericAspectHelper(a);
		}
		for(Aspect a:Aspect.getPrimalAspects()){
			new NumericAspectHelper(a);
		}
	}

	private Aspect aspect;
	private int num;

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
