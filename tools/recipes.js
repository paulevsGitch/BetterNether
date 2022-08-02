const   fs = require("fs"),
        path = require("path");

const PATH = "/Users/sifrbaue/Documents/Minecraft/BetterNether/src/main/resources/data/betternether/recipes";
function tagRef(value){
    let parts = value.tag.split(':')
    if (parts.length==2){
        if (parts[0] == 'minecraft') return "ItemTags." + parts[1].toUpperCase();
        if (parts[0] == 'c') return "CommonItemTags." + parts[1].toUpperCase();
        if (parts[0] == 'betterend') return "NetherTags." + parts[1].toUpperCase();
    }
    return value.tag;
}
function itemRef(value){
    if (value.item==undefined) return tagRef(value)
    let parts = value.item.split(':')
    if (parts.length==2){
        if (parts[0] == 'minecraft') return "Items." + parts[1].toUpperCase();
        if (parts[0] == 'betternether') return "NetherItems." + parts[1].toUpperCase();
        if (parts[0] == 'betterend') return "EndItems." + parts[1].toUpperCase();
    }
    return value.item;
}
function blockRef(value){
    if (value.item==undefined) return tagRef(value)
    let parts = value.item.split(':')
    if (parts.length==2){
        if (parts[0] == 'minecraft') return "Blocks." + parts[1].toUpperCase();
        if (parts[0] == 'betternether') return "NetherBlocks." + parts[1].toUpperCase();
        if (parts[0] == 'betterend') return "EndBlocks." + parts[1].toUpperCase();
    }
    return value.item;
}
function printShaped(name, json){
    let res = `GridRecipe.make(BetterNether.makeID("${name}"), ${itemRef(json.result)})\n`+
    ".checkConfig(Configs.RECIPE_CONFIG)\n"+
    '.setShape('+json.pattern.map(p=>`"${p}"`).join(",")+')\n'+
    Object.keys(json.key).map(k=>`.addMaterial('${k}', ${itemRef(json.key[k])})\n`).join("")+
    '.setGroup("nether_'+name+'")\n';

    if (+json.result.count>1){
        res += `.setOutputCount(${json.result.count})\n`
    }
    res += ".build();";

    console.log(res);
}
const letters = "#ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
function printShapeless(name, json){
    let shape =""
    let res = `GridRecipe.make(BetterNether.makeID("${name}"), ${itemRef(json.result)})\n`+
    ".checkConfig(Configs.RECIPE_CONFIG)\n"+
    json.ingredients.map((k, nr)=>`.addMaterial('${letters[nr]}', ${itemRef(k)})\n`).join("")+    
    '.setList("'+json.ingredients.map((k, nr)=>letters[nr]).join("")+'")\n'+
    '.setGroup("nether_'+name+'")\n';
    
    if (+json.result.count>1){
        res += `.setOutputCount(${json.result.count})\n`
    }
    res += ".build();";

    console.log(res);
}
function printStoneCutting(name, json){    
    let res = `StoneCutterRecipe\n.make(BetterNether.makeID("${name}"), ${blockRef({item:json.result})})\n`+
    ".checkConfig(Configs.RECIPE_CONFIG)\n"+
    ".setInput("+blockRef(json.ingredient)+")\n"+
    '.setGroup("nether_'+name+'")\n';

    if (+json.count>1){
        res += `.setOutputCount(${json.count})\n`
    }
    res += ".build();";

    console.log(res);
}

function printBlasting(name, json){    
    let res = `BlastFurnaceRecipe\n.make(BetterNether.makeID("${name}"), ${blockRef({item:json.result})})\n`+
    ".checkConfig(Configs.RECIPE_CONFIG)\n"+
    ".setInput("+blockRef(json.ingredient)+")\n"+
    '.setGroup("nether_'+name+'")\n'+    
    `.setCookingTime(${+json.cookingtime})\n`;

    if (+json.experience>0){
        res += `.setExperience(${+json.experience})\n`;
    }
    if (+json.count>1){
        res += `.setOutputCount(${json.count})\n`
    }
    res += ".build();";

    console.log(res);
}

function findFiles(dir, indent=""){
    console.log(`${indent}|-- ${dir}`)
    files = fs.readdirSync(dir)

    files.forEach(function(file) {
        if (fs.statSync(path.join(dir, file)).isDirectory()) {
            findFiles(path.join(dir, file), indent+"  ")
        } else {
            const ext = path.extname(file);
            const name = path.basename(file, ext);

            if (ext === '.json'){
                try {
                    const json = JSON.parse(fs.readFileSync(path.join(dir, file)).toString());
                    //console.log(`${indent}  |   - ${name}${ext}`)   
                    if (json.type == "minecraft:crafting_shaped" || json.type == "crafting_shaped"){
                        //console.log(`${indent}  |       SHAPED`)   
                        //printShaped(name, json)
                        //fs.unlinkSync(path.join(dir, file));
                    } else if (json.type == "minecraft:crafting_shapeless" || json.type == "crafting_shapeless"){
                        //console.log(`${indent}  |       SHAPELESS`)   
                        //printShapeless(name, json)
                        //fs.unlinkSync(path.join(dir, file));
                    } else if (json.type == "minecraft:stonecutting" || json.type == "stonecutting"){
                        //console.log(`${indent}  |       STONECUTTING`)   
                        //printStoneCutting(name, json)
                        //fs.unlinkSync(path.join(dir, file));
                    }  else if (json.type == "minecraft:blasting" || json.type == "blasting"){
                        //console.log(`${indent}  |       BLASTING`)   
                        //printBlasting(name, json)
                        fs.unlinkSync(path.join(dir, file));
                    } else{
                        //console.log(`${indent}  |       Unknown Type`, json.type)   
                    }                       
                } catch (e) {
                    console.error(e.message)
                    console.error(`${indent}  |   - FAILED ${name}${ext}`)
                }
            }
        }
    })
}

findFiles(PATH);