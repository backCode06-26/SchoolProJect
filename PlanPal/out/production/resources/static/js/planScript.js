document.addEventListener('DOMContentLoaded', () => {
    const draggables = document.querySelectorAll('.draggable');
    const copyZone = document.querySelector('#copyZone');
    const deleteZone = document.querySelector('#deleteZone');
    const saveAllBtn = document.querySelector('#saveButton');

    let draggedElement = null;

    const categoryMap = {
        '업무': 'WORK', '개인 개발': 'PERSONAL_DEVELOPMENT', '운동': 'EXERCISE',
        '여행': 'TRAVEL', '사회 활동': 'SOCIAL_ACTIVITY', '가족': 'FAMILY',
        '취미': 'HOBBY', '건강': 'HEALTH', '교육': 'EDUCATION',
        '쇼핑': 'SHOPPING', '집안일': 'HOUSEWORK', '금융': 'FINANCE',
        '오락': 'ENTERTAINMENT', '약속': 'APPOINTMENT', '기타': 'OTHER'
    };

    function getCategoryTranslation(category, toEnum = true) {
        if (toEnum) {
            return categoryMap[category] || 'OTHER';
        }
        return Object.keys(categoryMap).find(key => categoryMap[key] === category) || '기타';
    }

    function handleDragStart(event) {
        draggedElement = event.target;
        draggedElement.classList.add('dragging');
    }

    function handleDragEnd() {
        draggedElement.classList.remove('dragging');
        draggedElement = null;
    }

    function handleDragOver(event) {
        event.preventDefault();
    }

    function handleDrop(event, zone) {
        event.preventDefault();

        if (draggedElement) {
            if (zone === copyZone && !draggedElement.classList.contains('copy')) {
                const category = draggedElement.dataset.category;
                const newCard = createCardElement({
                    id: Date.now(),
                    category: getCategoryTranslation(category),
                    title: '제목없음',
                    description: '시간: 00:00 | 장소: '
                });

                copyZone.appendChild(newCard);
                createFormOverlay(newCard);
            } else if (zone === copyZone) {
                const afterElement = getDragAfterElement(copyZone, event.clientY);
                if (!afterElement) {
                    copyZone.appendChild(draggedElement);
                } else {
                    copyZone.insertBefore(draggedElement, afterElement);
                }
            } else if (zone === deleteZone && confirm("정말로 삭제하시겠습니까?")) {
                copyZone.removeChild(draggedElement);
                draggedElement = null;
            }
        }
    }

    draggables.forEach(draggable => {
        draggable.addEventListener('dragstart', handleDragStart);
        draggable.addEventListener('dragend', handleDragEnd);
    });

    copyZone.addEventListener('dragover', handleDragOver);
    deleteZone.addEventListener('dragover', handleDragOver);

    copyZone.addEventListener('drop', event => handleDrop(event, copyZone));
    deleteZone.addEventListener('drop', event => handleDrop(event, deleteZone));

    saveAllBtn.addEventListener('click', saveAllData);

    function saveAllData() {
        const cards = copyZone.querySelectorAll('.card.copy');
        const cardData = [];

        cards.forEach(card => {
            const title = card.querySelector('.card-title')?.textContent.trim() || '';
            const details = card.querySelector('.card-details')?.textContent.trim() || '';
            const category = card.dataset.category || '';
            const eventId = card.dataset.id || '';

            let timePart = '';
            let locationPart = '';
            if (details) {
                const detailsParts = details.split('|');
                timePart = detailsParts[0]?.split(': ')[1]?.trim() || '';
                locationPart = detailsParts[1]?.split(': ')[1]?.trim() || '';
            }

            const timeForm = (timePart == "00:00") ? '0001-01-01T00:00:00.000+09:00' : timePart;

            cardData.push({
                groupId: groupId,
                userId: userId,
                title: title,
                description: details,
                eventDate: timeForm,
                location: locationPart,
                category: category
            });
        });

        console.log('Saving data:', cardData);

        $.ajax({
            url: '/plan/save',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(cardData),
            success: (response) => {
                console.log('저장 성공:', response);
                window.location.href = '/';
            },
            error: (xhr, status, error) => {
                console.error('저장 중 오류가 발생했습니다.', { xhr, status, error });
            }
        });
    }

        function fetchData() {
            $.ajax({
                url: '/plan/get/event/' + groupId,
                method: 'GET',
                success: (response) => {
                    console.log('데이터 가져오기 성공:', response);
                    createCardsFromData(response);
                },
                error: (xhr, status, error) => {
                    console.error('데이터 가져오기 중 오류 발생:', { xhr, status, error });
                }
            });
        }

    function createCardsFromData(data) {
        data.forEach(item => {
            const newCard = createCardElement(item);
            copyZone.appendChild(newCard);
            createFormOverlay(newCard);
        });
    }

    function createCardElement({ id, category, title = '', description = '' }) {
        const newCard = document.createElement('div');
        newCard.className = 'card draggable copy';
        newCard.draggable = true;
        newCard.dataset.id = id;
        newCard.dataset.category = category;

        const categoryName = getCategoryTranslation(category, false);

        newCard.innerHTML = `
            <div class="content">
                <h3 class="card-title">${title || '제목없음'}</h3>
                <p class="card-details">${description || '시간: 0000-00-00 | 장소: '}</p>
                <p class="card-details">카테고리: ${categoryName}</p>
            </div>
            <i class="fa-solid fa-bars toggle-form-btn"></i>
        `;

        newCard.addEventListener('dragstart', handleDragStart);
        newCard.addEventListener('dragend', handleDragEnd);

        return newCard;
    }

    function createFormOverlay(newCard) {
        let existingOverlay = document.getElementById(`form-overlay-${newCard.dataset.id}`);
        if (existingOverlay) {
            existingOverlay.style.display = 'none';
        } else {
            const formOverlay = document.createElement('div');
            formOverlay.className = 'form-overlay';
            formOverlay.id = `form-overlay-${newCard.dataset.id}`;
            formOverlay.innerHTML = `
                <div class="form-container">
                    <div class="form-group">
                        <label for="titleInput-${newCard.dataset.id}">제목:</label>
                        <input type="text" id="titleInput-${newCard.dataset.id}" class="form-control title-input" placeholder="제목을 입력하세요">
                    </div>
                    <div class="form-group">
                        <label for="timeInput-${newCard.dataset.id}">시간:</label>
                        <input type="time" id="timeInput-${newCard.dataset.id}" class="form-control time-input">
                    </div>
                    <div class="form-group">
                        <label for="locationInput-${newCard.dataset.id}">장소:</label>
                        <input type="text" id="locationInput-${newCard.dataset.id}" class="form-control location-input" placeholder="장소를 입력하세요">
                    </div>
                    <div class="form-group">
                        <label for="contentInput-${newCard.dataset.id}">설명:</label>
                        <textarea id="contentInput-${newCard.dataset.id}" class="form-control content-input" rows="3" placeholder="설명을 입력하세요"></textarea>
                    </div>
                    <div class="btn-container">
                        <button id="submitForm-${newCard.dataset.id}" class="btn btn-primary">제출</button>
                    </div>
                </div>
            `;

            formOverlay.addEventListener('click', (event) => {
                if (event.target === formOverlay) {
                    formOverlay.style.display = 'none';
                }
            });

            newCard.querySelector('.toggle-form-btn').addEventListener('click', () => {
                formOverlay.style.display = 'flex';
                document.body.appendChild(formOverlay);

                const submitFormBtn = formOverlay.querySelector(`#submitForm-${newCard.dataset.id}`);
                submitFormBtn.addEventListener('click', () => {
                    const title = formOverlay.querySelector(`#titleInput-${newCard.dataset.id}`).value;
                    const time = formOverlay.querySelector(`#timeInput-${newCard.dataset.id}`).value;
                    const location = formOverlay.querySelector(`#locationInput-${newCard.dataset.id}`).value;
                    const content = formOverlay.querySelector(`#contentInput-${newCard.dataset.id}`).value;

                    const date = new Date();
                    const dateTime = formatDateTime(date, time);

                    newCard.querySelector('.card-title').textContent = title || '제목없음';
                    const cardDetails = newCard.querySelector('.card-details');
                    cardDetails.innerHTML = `시간: ${dateTime} | 장소: ${location || ''}`;

                    formOverlay.style.display = 'none';
                });
            });
        }
    }

    function formatDateTime(date, time = '00:00') {
        const pad = (num, size = 2) => num.toString().padStart(size, '0');

        const year = date.getFullYear();
        const month = pad(date.getMonth() + 1);
        const day = pad(date.getDate());

        const [hours = '00', minutes = '00'] = time.split(':').map(num => pad(num, 2));

        const formattedDateTime = `${year}-${month}-${day}T${hours}:${minutes}:00.000+09:00`;
        return formattedDateTime;
    }

    function getDragAfterElement(container, y) {
        const draggableElements = [...container.querySelectorAll('.draggable:not(.dragging)')];

        return draggableElements.reduce((closest, child) => {
            const box = child.getBoundingClientRect();
            const offset = y - box.top - box.height / 2;
            if (offset < 0 && offset > closest.offset) {
                return { offset: offset, element: child };
            } else {
                return closest;
            }
        }, { offset: Number.NEGATIVE_INFINITY }).element;
    }

    // 데이터 가져오기
    fetchData();
});
