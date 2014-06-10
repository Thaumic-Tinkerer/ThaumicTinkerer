package vazkii.tinkerer.common.registry;

import com.google.common.reflect.ClassPath;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class RegistryItems {

	private ArrayList<Class> itemClasses = new ArrayList<Class>();
	private HashMap<Class, ArrayList<Item>> itemRegistry = new HashMap<Class, ArrayList<Item>>();

	public void registerClasses() {
		try {
			ClassPath classPath = ClassPath.from(this.getClass().getClassLoader());
			for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive("vazkii.tinkerer")) {
				if (ITTinkererItem.class.isAssignableFrom(classInfo.getClass())) {
					itemClasses.add(classInfo.getClass());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void registerResearch(ITTinkererItem nextItem) {
		IRegisterableResearch registerableResearch = nextItem.getResearchItem();
		if (registerableResearch != null) {
			registerableResearch.registerResearch();
		}
	}

	public void preInit() {
		registerClasses();

		for (Class clazz : itemClasses) {
			try {
				Item newItem = (Item) clazz.newInstance();
				if (((ITTinkererItem) newItem).shouldRegister()) {
					newItem.setUnlocalizedName(((ITTinkererItem) newItem).getItemName());
					ArrayList<Item> itemList = new ArrayList<Item>();
					itemList.add(newItem);
					registerResearch((ITTinkererItem) newItem);
					for (Object param : ((ITTinkererItem) newItem).getSpecialParameters()) {
						Item nextItem = (Item) clazz.getConstructor(param.getClass()).newInstance(param);
						nextItem.setUnlocalizedName(((ITTinkererItem) nextItem).getItemName());
						itemList.add(nextItem);
						registerResearch((ITTinkererItem) nextItem);
					}
					itemRegistry.put(clazz, itemList);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Item> getItemFromClass(Class clazz) {
		return itemRegistry.get(clazz);
	}

	public Item getFirstItemFromClass(Class clazz) {
		return itemRegistry.get(clazz) != null ? itemRegistry.get(clazz).get(0) : null;
	}

	public void init() {

		for (ArrayList<Item> itemArrayList : itemRegistry.values()) {
			for (Item item : itemArrayList) {
				GameRegistry.registerItem(item, ((ITTinkererItem) item).getItemName());
			}
		}
	}

	public void postInit() {

	}

}
