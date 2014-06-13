package vazkii.tinkerer.common.registry;

import com.google.common.reflect.ClassPath;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class TTRegistry {

	private ArrayList<Class> itemClasses = new ArrayList<Class>();
	private HashMap<Class, ArrayList<Item>> itemRegistry = new HashMap<Class, ArrayList<Item>>();

	private ArrayList<Class> blockClasses = new ArrayList<Class>();
	private HashMap<Class, ArrayList<Block>> blockRegistry = new HashMap<Class, ArrayList<Block>>();

	public void registerClasses() {
		try {
			ClassPath classPath = ClassPath.from(this.getClass().getClassLoader());
			for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive("vazkii.tinkerer")) {
				if (ITTinkererItem.class.isAssignableFrom(classInfo.getClass())) {
					itemClasses.add(classInfo.getClass());
				}
				if (ITTinkererBlock.class.isAssignableFrom(classInfo.getClass())) {
					blockClasses.add(classInfo.getClass());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void registerResearch(ITTinkererRegisterable nextItem) {
		IRegisterableResearch registerableResearch = nextItem.getResearchItem();
		if (registerableResearch != null) {
			registerableResearch.registerResearch();
		}
	}

	public void registerRecipe(ITTinkererRegisterable nextItem) {
		ThaumicTinkererRecipe thaumicTinkererRecipe = nextItem.getRecipeItem();
		if (thaumicTinkererRecipe != null) {
			thaumicTinkererRecipe.registerRecipe();
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
						registerRecipe((ITTinkererItem) nextItem);
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
		for (Class clazz : blockClasses) {
			try {
				Block newBlock = (Block) clazz.newInstance();
				if (((ITTinkererBlock) newBlock).shouldRegister()) {
					newBlock.setBlockName(((ITTinkererBlock) newBlock).getBlockName());
					ArrayList<Block> blockList = new ArrayList<Block>();
					blockList.add(newBlock);
					if (((ITTinkererBlock) newBlock).getItemBlock() != null) {
						Item newItem = ((ITTinkererBlock) newBlock).getItemBlock().newInstance();
						newItem.setUnlocalizedName(((ITTinkererItem) newItem).getItemName());
						ArrayList<Item> itemList = new ArrayList<Item>();
						itemList.add(newItem);
					}

					registerRecipe((ITTinkererRegisterable) newBlock);
					registerResearch((ITTinkererRegisterable) newBlock);
					for (Object param : ((ITTinkererBlock) newBlock).getSpecialParameters()) {
						Block nextBlock = (Block) clazz.getConstructor(param.getClass()).newInstance(param);
						nextBlock.setBlockName(((ITTinkererBlock) nextBlock).getBlockName());
						blockList.add(nextBlock);
						registerRecipe((ITTinkererRegisterable) nextBlock);
						registerResearch((ITTinkererRegisterable) nextBlock);
					}
					blockRegistry.put(clazz, blockList);
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

	public ArrayList<Block> getBlockFromClass(Class clazz) {
		return blockRegistry.get(clazz);
	}

	public Block getFirstBlockFromClass(Class clazz) {
		return blockRegistry.get(clazz) != null ? blockRegistry.get(clazz).get(0) : null;
	}

	public void init() {

		for (ArrayList<Item> itemArrayList : itemRegistry.values()) {
			for (Item item : itemArrayList) {
				GameRegistry.registerItem(item, ((ITTinkererItem) item).getItemName());
			}
		}
		for (ArrayList<Block> blockArrayList : blockRegistry.values()) {
			for (Block block : blockArrayList) {
				if (((ITTinkererBlock) block).getItemBlock() != null) {
					GameRegistry.registerBlock(block, ((ITTinkererBlock) block).getItemBlock(), ((ITTinkererBlock) block).getBlockName());
				} else {
					GameRegistry.registerBlock(block, ((ITTinkererBlock) block).getBlockName());
				}
				if (((ITTinkererBlock) block).getTileEntity() != null) {
					GameRegistry.registerTileEntity(((ITTinkererBlock) block).getTileEntity(), LibResources.PREFIX_MOD + ((ITTinkererBlock) block).getBlockName());
				}
			}
		}
	}

	public void postInit() {

	}

}
