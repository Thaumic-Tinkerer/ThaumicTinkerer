var temp=["ordo","perditio","vacuos","lux","motus","gelum","vitreus","victus","mortuus","potentia","permutatio","auram","alkimia","vitium","tenebrae","herba","instrumentum","fabrico","machina","vinculum","spiritus","sensus","praemunio","desiderium","exanimis","fluctus","sonus","exitium","caeles","draco","infernum","ventus","visum","imperium","cognitio"]

fs = require('fs');
for(aspect of temp) {
	const file = `
	{
  "parent": "minecraft:item/generated",
  "textures": {
    "layer0": "thaumictinkerer:items/aspects/${aspect}"
  }
}
	`
	fs.writeFile('aspectJsons/'+aspect+'.json',file,function(err) {
		if (err) return console.log(err);
			console.log(`written ${aspect}`);
	});
	console.log(file);
}