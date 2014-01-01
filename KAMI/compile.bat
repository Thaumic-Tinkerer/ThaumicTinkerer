mkdir ./vazkii/tinkerer/common/
javac -source 1.6 -d . ThaumicTinkererKami.java
jar cf ThaumicTinkererKAMI.jar ./vazkii/
rd vazkii /s/q
del .\cpw\mods\fml\common\Mod.class,.\cpw\mods\fml\common\network\NetworkMod.class