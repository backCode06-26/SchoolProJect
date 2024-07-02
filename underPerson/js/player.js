export class Player {
    constructor() {
        this.inventory = [{type : 'Stone', count : 10}];
        this.vector = { x: 0, y: 0 };
        this.level = 1;
        this.ladder = false;
        this.blocks = false;
        this.size = 100;
        this.container = document.querySelector('.container');
        this.text = document.querySelector('.text-cotainer');
        this.direction = 'right';
        this.createPlayer();
    }

    // 움직임 제한
    limit() {
        if (this.vector.x < 0) {
            this.vector.x = 0;
        } else if (this.vector.x > this.size - 1) {
            this.vector.x = this.size - 1;
        }

        if (this.vector.y < 0) {
            this.vector.y = 0;
        } else if (this.vector.y > this.size - 1) {
            this.vector.y = this.size - 1;
        }
    }

    // 플레이어 생성
    createPlayer() {
        const playerCell = this.container.querySelector(`#x${this.vector.x}y${this.vector.y}`);
        const belowCell = this.container.querySelector(`#x${this.vector.x}y${this.vector.y + 1}`);
        playerCell.classList.add('cell', 'player');
        belowCell.classList = 'cell';
        belowCell.classList.add('Wood', 'block');
    }

    // 플레이어 움직임
    updatePlayer() {
        const oldPlayerCell = this.container.querySelector('.player');
        oldPlayerCell.classList.remove('player');

        const newPlayerCell = this.container.querySelector(`#x${this.vector.x}y${this.vector.y}`);
        newPlayerCell.classList.add('player');
    }

    hasBlockDiagonalBelow(deltaX) {
        const diagonalBelowCell = this.container.querySelector(`#x${this.vector.x + deltaX}y${this.vector.y + 1}`);
        return diagonalBelowCell.classList.contains('cell') && diagonalBelowCell.classList.length === 1;
    }    

    // 블록 채굴
    mineBlock() {
        let targetX = this.vector.x;
        let targetY = this.vector.y;
    
        // 캐는 방향에 따라 타겟 위치 업데이트
        switch (this.direction) {
            case 'up':
                targetY--;
                break;
            case 'down':
                targetY++;
                break;
            case 'left':
                targetX--;
                break;
            case 'right':
                targetX++;
                break;
            default:
                console.log('캐는 방향이 올바르지 않습니다.');
                return;
        }
    
        const targetCell = this.container.querySelector(`#x${targetX}y${targetY}`);
        if (!targetCell.classList.contains('block')) {
            console.log('캘 수 있는 블록이 없습니다.');
            return;
        }
    
        // 블록의 타입 가져오기
        const blockType = targetCell.classList[1];
    
        // 채굴레벨을 높이기
        if (!this.canMineBlock(blockType)) {
            let random = Math.floor(Math.random() * 10) + 1; // 1에서 10까지의 랜덤 숫자 생성
            if (random <= 2) {
                this.level++;
                console.log(`레벨업! 현재 레벨: ${this.level}`);
            } else {
                console.log('캘 수 없는 블록입니다.');
                return;
            }
        }
        
        // 채굴한 아이템의 저장
        let existingBlock = this.inventory.find(item => item.type === blockType);
        if (existingBlock) {
            existingBlock.count++;
        } else if(blockType !== 'tool') {
            this.inventory.push({ type: blockType, count: 1 });
        }
    
        targetCell.classList.remove('block', blockType);
    }
    
    // 레벨에 따른 채굴 가능 블록
    canMineBlock(blockType) {
        const minableBlocksByLevel = {
            1: ['Wood', 'Stone', 'tool'],
            2: ['Wood', 'Stone', 'Iron', 'tool'],
            3: ['Wood', 'Stone', 'Iron', 'Silver', 'tool'],
            4: ['Wood', 'Stone', 'Iron', 'Silver', 'Gold', 'tool'],
            5: ['Wood', 'Stone', 'Iron', 'Silver', 'Gold', 'Diamond', 'tool']
        };
    
        return minableBlocksByLevel[this.level].includes(blockType);
    }
    
    move(event) {
        console.log(this.direction);
        switch (event.key) {
            case 'w':
                this.direction = 'up';
                const upBlock = this.container.querySelector(`#x${this.vector.x}y${this.vector.y-1}`);
                if (!upBlock.classList.contains('tool')) {
                    console.log('tool이 없어 못움직입니다.');
                    return;
                }
                this.vector.y--;
                break;
            case 's':
                this.direction = 'down';
                const downCell = this.container.querySelector(`#x${this.vector.x}y${this.vector.y+1}`);
                if (downCell.classList.contains('block')) {
                    console.log('아래에 블록이 있어 움직일 수 없습니다.');
                    return;
                }
                this.vector.y++;
                break;
            case 'a':
                this.direction = 'left';
                if (this.hasBlockDiagonalBelow(-1)) {
                    console.log('대각선 아래에 블록이 있어 움직일 수 없습니다.');
                    return;
                }
                const leftCell = this.container.querySelector(`#x${this.vector.x - 1}y${this.vector.y}`);
                if (leftCell.classList.contains('block')) {
                    console.log('아래의 블록이 없어 움직일 수 없습니다.');
                    return;
                }
                this.vector.x--;
                break;
            case 'd':
                this.direction = 'right';
                if (this.hasBlockDiagonalBelow(1)) {
                    console.log('대각선 아래에 블록이 있어 움직일 수 없습니다.');
                    return;
                }
                const rightCell = this.container.querySelector(`#x${this.vector.x + 1}y${this.vector.y}`);
                if (rightCell.classList.contains('block')) {
                    console.log('오른쪽에 블록이 있어 움직일 수 없습니다.');
                    return;
                }
                this.vector.x++;
                break;
            case 'e':
                this.mineBlock();
                return;
            default:
                break;
        }
        this.limit();
        this.updatePlayer();
    }
    

    // tool의 생성
    clickBlock(event) {
        const clickedCell = event.target;
        if (!clickedCell.classList.contains('cell')) {
            return;
        }
    
        const stoneItem = this.inventory.find(item => item.type === 'Stone');
        if (stoneItem.count <= 0) {
            console.log('인벤토리에 Stone이 부족합니다.');
            return;
        }
    
        stoneItem.count--;
    
        clickedCell.classList.add('tool');
    
        console.log('Stone 블록을 생성하였습니다.');
        console.log(this.inventory);
    }
    
}
