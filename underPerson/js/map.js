export class Map {
    constructor() {
        this.size = 10;
    }

    create() {
        const materials = ["Iron", "Silver", "Gold", "Diamond", "Wood", "Stone"];

        const container = document.querySelector('.container')
        for(let i = 0; i < this.size; i++) {
            for(let j = 0; j < this.size; j++) {
                const random = Math.floor(Math.random() * 10) + 1;

                const div = document.createElement('div');
                div.classList.add('cell');
                div.id = `x${j}y${i}`;
                if(random < materials.length) {
                    div.classList.add(materials[random]);
                    div.classList.add('block');
                }
                container.appendChild(div);
            }
        }
    }
}
