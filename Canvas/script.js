const canvas = document.getElementById('canvas');

// 2d에서의 다양한 기능을 가져옵니다.
const ctx = canvas.getContext('2d');

// 생삭, 크기, 스타일 값 가져오기
const colorPicker = document.querySelector('input[name="color"]');
const brushSize = document.querySelector('input[name="range"]');
const brushStyle = document.querySelector('select[name="select"]');

// canvas 크기 설정
canvas.width = canvas.offsetWidth;
canvas.height = canvas.offsetHeight;
let canvasStates = [];
let isDrawing = false; // 변수 초기화

// 색상, 크기, 스타일 설정
colorPicker.addEventListener('input', () => {
    ctx.strokeStyle = colorPicker.value;
});

brushSize.addEventListener('input', () => {
    ctx.lineWidth = brushSize.value;
});

brushStyle.addEventListener('input', () => {
    ctx.lineCap = brushStyle.value;
});

function saveState() {
    canvasStates.push(canvas.toDataURL());
}

// 선 그리기 시작하는 부분
canvas.addEventListener('mousedown', (e) => {
    saveState();
    isDrawing = true;
    ctx.beginPath();
    ctx.moveTo(e.offsetX, e.offsetY);

});

// 선 그리기
canvas.addEventListener('mousemove', (e) => {
    if (!isDrawing) return;
    ctx.lineTo(e.offsetX, e.offsetY);
    ctx.stroke();
});

// 마우스 클릭 해제
canvas.addEventListener('mouseup', () => {
    isDrawing = false;
});

canvas.addEventListener('mouseout', () => {
    isDrawing = false;
});

// 저장하기
document.getElementById('save').addEventListener("click", () => {
    const image = canvas.toDataURL();
    const link = document.createElement("a");
    link.href = image;
    link.download = "MyDesignSocks";
    link.click();
});

document.getElementById('back').addEventListener('click', () => {
    if (canvasStates.length > 0) {
        const img = new Image();
        const lastState = canvasStates.pop();
        img.src = lastState;
        img.onload = () => {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            ctx.drawImage(img, 0, 0);
        };
    }
});


// 초기화
document.getElementById('clear').addEventListener('click', () => {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
});
