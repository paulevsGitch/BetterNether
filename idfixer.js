const   fs = require("fs"),
        path = require("path")

const Map = {
    entry: function (s1, s2) {
        return {
            old:s1.split(":")[1],
            new:s2.split(":")[1]
        }
    }
}

const replacments = [
            Map.entry("betternether:chest", "bclib:chest"),
            Map.entry("betternether:striped_log_stalagnate", "betternether:stalagnate_stripped_log"),
            Map.entry("betternether:striped_bark_stalagnate", "betternether:stalagnate_stripped_bark"),
            Map.entry("betternether:stalagnate_planks_stairs", "betternether:stalagnate_stairs"),
            Map.entry("betternether:stalagnate_planks_slab", "betternether:stalagnate_slab"),
            Map.entry("betternether:stalagnate_planks_fence", "betternether:stalagnate_fence"),
            Map.entry("betternether:stalagnate_planks_gate", "betternether:stalagnate_gate"),
            Map.entry("betternether:stalagnate_planks_button", "betternether:stalagnate_button"),
            Map.entry("betternether:stalagnate_planks_plate", "betternether:stalagnate_plate"),
            Map.entry("betternether:stalagnate_planks_trapdoor", "betternether:stalagnate_trapdoor"),
            Map.entry("betternether:stalagnate_planks_door", "betternether:stalagnate_door"),
            Map.entry("betternether:crafting_table_stalagnate", "betternether:stalagnate_crafting_table"),
            Map.entry("betternether:sign_stalagnate", "betternether:stalagnate_sign"),
            Map.entry("betternether:chest_stalagnate", "betternether:stalagnate_chest"),
            Map.entry("betternether:barrel_stalagnate", "betternether:stalagnate_barrel")
        ]

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
                    const json = fs.readFileSync(path.join(dir, file)).toString();
                    let jsonnew = json;

                    replacments.forEach(what => {
                        const search = what.old  
                        const replacer = new RegExp(search, 'g')
                        jsonnew = jsonnew.replace(search, what.new)
                    })
                    if (json!=jsonnew){
                        console.error(`${indent}  |   - changed ${name}${ext}`)
                        fs.writeFileSync(path.join(dir, file), jsonnew);
                    }
                } catch (e) {
                    console.error(e.message)
                    console.error(`${indent}  |   - FAILED ${name}${ext}`)
                }
            }

            replacments.forEach(what => {
                if (name.indexOf(what.old)>=0) {
                    const newName = name.replace(what.old, what.new);
                    fs.renameSync(path.join(dir, name+ext), path.join(dir, newName+ext), function (err) {
                        if (err) throw err
                        console.error(`${indent}  |   - moved ${name}${ext} -> ${newName}${ext}`)
                      })
                }
            })
            //console.log(`${indent}  |   - ${name} ${ext}`)
        }
    })
}

const startPath = path.join(".", "src", "main", "resources")
findFiles(startPath)