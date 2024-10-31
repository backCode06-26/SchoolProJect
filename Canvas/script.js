const canvas = document.getElementById('canvas');

// 2d에서의 다양한 기능을 가져옵니다.
const ctx = canvas.getContext('2d');

// 생삭, 크기, 스타일 값 가져오기
const colorPicker = document.querySelector('input[name="color"]');
const brushSize = document.querySelector('input[name="range"]');
const brushStyle = document.querySelector('select[name="select"]');


// canvas 크기 설정, 
// 설정안하면 마우스끝이 아닌 다른 곳에 그림을 그려집니다.
canvas.width = canvas.offsetWidth;
canvas.height = canvas.offsetHeight;

// 색상, 크기, 스타일 설정
colorPicker.addEventListener('input', () => {
    console.log(ctx.strokeStyle);
    ctx.strokeStyle = colorPicker.value;
});

brushSize.addEventListener('input', () => {
    console.log(ctx.lineWidth);
    ctx.lineWidth = brushSize.value;
});

brushStyle.addEventListener('input', () => {
    console.log(ctx.lineCap);
    ctx.lineCap = brushStyle.value;
});

// 선 그리기 시작하는 부분
canvas.addEventListener('mousedown', (e) => {
    isDrawing = true;
    ctx.beginPath();
    ctx.moveTo(e.offsetX, e.offsetY);
});

// 선 그리기 끝나는 부분, 선을 이어서 그러줌
canvas.addEventListener('mousemove', (e) => {
    if (!isDrawing) return;
    ctx.lineTo(e.offsetX, e.offsetY);
    ctx.stroke();
});

// 마우스 클릭해제, 마우스가 설정한 부분에서 나갈때
// 그림을 그리지 못하게 제약
canvas.addEventListener('mouseup', () => {
    isDrawing = false;
});

canvas.addEventListener('mouseout', () => {
    isDrawing = false;
})

document.getElementById('sava').addEventListener("click", () => {
    const image = canvas.toDataURL();
    const link = document.createElement("a");
    link.href = image;
    link.download = "MyDesignSocks";
    link.click();
});

// 초기화
document.getElementById('clear').addEventListener('click', () => {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
});
