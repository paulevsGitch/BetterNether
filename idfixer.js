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
    Map.entry("betternether:striped_log_rubeus", "betternether:rubeus_stripped_log"),
				Map.entry("betternether:striped_bark_rubeus", "betternether:rubeus_stripped_bark"),
				Map.entry("betternether:crafting_table_rubeus", "betternether:rubeus_crafting_table"),
				Map.entry("betternether:sign_rubeus", "betternether:rubeus_sign"),
				Map.entry("betternether:chest_rubeus", "betternether:rubeus_chest"),
				Map.entry("betternether:barrel_rubeus", "betternether:rubeus_barrel"),
				Map.entry("betternether:taburet_rubeus", "betternether:rubeus_taburet"),
				Map.entry("betternether:chair_rubeus", "betternether:rubeus_chair"),
				Map.entry("betternether:bar_stool_rubeus", "betternether:rubeus_bar_stool")
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
                        // const search = what.old  
                        // const replacer = new RegExp(search, 'g')
                        // jsonnew = jsonnew.replace(replacer, what.new)
                        jsonnew = jsonnew.split(what.old).join( what.new)
                        
                    })
                    if (json!=jsonnew){
                        console.log(`${indent}  |   - changed ${name}${ext}`)
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
                        console.log(`${indent}  |   - moved ${name}${ext} -> ${newName}${ext}`)
                      })
                }
            })
            //console.log(`${indent}  |   - ${name} ${ext}`)
        }
    })
}

const startPath = path.join(".", "src", "main", "resources")
findFiles(startPath)