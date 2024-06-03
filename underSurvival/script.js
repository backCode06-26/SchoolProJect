let playerPosition = { x: 0, y: 0 };
let direction = 'none';

function movePlayer(x, y, dir) {
    direction = dir;
    if (isBlocked(x, y)) {
        return;
    }

    updatePlayerPosition(x, y);
}

function updatePlayerPosition(x, y) {
    
    document.getElementById(`x${playerPosition.x}y${playerPosition.y}`).classList.remove('player');
    
    playerPosition.x = x;
    playerPosition.y = y;
    
    document.getElementById(`x${playerPosition.x}y${playerPosition.y}`).classList.add('player');
}

movePlayer(0, 0, 'none');

document.addEventListener('keydown', handlePlayerMovement);

function handlePlayerMovement(event) {
    switch(event.key) {
        case 'ArrowUp':
            moveIfPossible(playerPosition.x, playerPosition.y - 1, 'y', 'up');
            break;
        case 'ArrowDown':
            moveIfPossible(playerPosition.x, playerPosition.y + 1, 'y', 'down');
            break;
        case 'ArrowLeft':
            moveIfPossible(playerPosition.x - 1, playerPosition.y, 'x', 'left');
            break;
        case 'ArrowRight':
            moveIfPossible(playerPosition.x + 1, playerPosition.y, 'x', 'right');
            break;
        case 'd':
            removeBlock(playerPosition.x, playerPosition.y, direction);
            break;
        case 'c':
            createPiece(playerPosition.x, playerPosition.y);
            break;
    }
    console.log(direction);
}

function moveIfPossible(newX, newY, axis, dir) {
    if (axis === 'x') {
        if (newX >= 0 && newX < 3) {
            movePlayer(newX, newY, dir);
        }
    } else if (axis === 'y') {
        if (newY >= 0 && newY < 5 && isLadder(newX, newY)) {
            movePlayer(newX, newY, dir);
        }
    }
}

function isBlocked(x, y) {
    const element = document.getElementById(`x${x}y${y}`);
    return element.classList.contains('block');
}

function isLadder(x, y) {
    const element = document.getElementById(`x${x}y${y}`);
    return element.classList.contains('ladder');
}

function removeBlock(x, y, direction) {
    let targetElement;
    switch (direction) {
        case 'up':
            targetElement = document.getElementById(`x${x}y${y-1}`);
            break;
        case 'down':
            targetElement = document.getElementById(`x${x}y${y+1}`);
            break;
        case 'left':
            targetElement = document.getElementById(`x${x-1}y${y}`);
            break;
        case 'right':
            targetElement = document.getElementById(`x${x+1}y${y}`);
            break;
    }
    if (targetElement && targetElement.classList.contains('block')) {
        targetElement.className = 'cell';
    }
}

function createPiece(x, y) {
    const element = document.getElementById(`x${x}y${y}`);
    if (!element.classList.contains('ladder')) {
        element.className = 'cell ladder';
    }
}