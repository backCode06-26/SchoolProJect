// main.js

import { Map } from './map.js';
import { Player } from './player.js';

document.addEventListener("DOMContentLoaded", function() {
    const map = new Map();
    map.create();

    const player = new Player(); // Player 클래스 인스턴스 생성

    document.addEventListener('keydown', function(event) {
        player.move(event)
    });

    document.addEventListener('click', function(event){
        player.clickBlock(event);
    });
});
