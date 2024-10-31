document.addEventListener('DOMContentLoaded', () => {
    const cardContainer = document.querySelector('#cardContainer');
    const title = document.querySelector('h5');
    const iconChat = document.querySelector('.icon-chat');
    const commentForm = document.querySelector('.comment-form');
    const createForm = commentForm.querySelector('.create-form');
    const stars = createForm.querySelectorAll('.star');
    const editButtons = document.querySelectorAll('.edit-button');
    const deleteButtons = document.querySelectorAll('.delete-button');
    let selectedStarId = null;
    let isUser = false;

    // 요소 확인
    if (!cardContainer) {
        console.error('카드 컨테이너 요소를 찾을 수 없습니다.');
        return;
    }

    // 채팅 아이콘 클릭 이벤트 처리
    if (iconChat) {
        iconChat.addEventListener('click', () => {
            toggleElement(commentForm);
        });
    }

    editButtons.forEach(button => {
        button.addEventListener('click', () => {
            const commentElement = button.closest('.comment');
            const updateForm = commentElement.querySelector('.update-form');
            toggleElement(updateForm);
        });
    });

    deleteButtons.forEach(button => {
        button.addEventListener('click', () => {
            const commentElement = button.closest('.comment');
            const ratingId = commentElement.dataset.ratingId;
            const reviewId = commentElement.dataset.reviewId;
            deleteCommentAndRating(ratingId, reviewId);
        });
    });

    // 요소를 보이게 또는 숨기게 하는 함수
    function toggleElement(element) {
        if (element) {
            element.style.display =
                (element.style.display === 'none' || element.style.display === '') ?
                    'block' : 'none';
        }
    }

    // 댓글 폼의 별점 처리
    stars.forEach((star, index) => {
        star.addEventListener('mouseover', () => {
            const starIndex = index + 1;

            stars.forEach(star => star.classList.remove('over'));

            for (let i = 0; i < starIndex; i++) {
                stars[i].classList.add('over');
            }
        });

        star.addEventListener('mouseout', () => {
            stars.forEach(star => star.classList.remove('over'));
        });

        star.addEventListener('click', () => {
            const starIndex = index + 1;

            stars.forEach(star => star.classList.remove('select', 'over'));

            for (let i = 0; i < starIndex; i++) {
                stars[i].classList.add('select');
            }
            selectedStarId = star.dataset.value;
        });
    });

    // 댓글 제출 버튼 클릭 이벤트
    commentForm.querySelector('#submitCommentButton').addEventListener('click', () => {
        const comment = commentForm.querySelector('#commentTextArea').value;
        createCommentAndRating(selectedStarId, comment);
    });

    // 업데이트 폼의 별점 처리
    document.querySelectorAll('.comment .update-form').forEach(container => {
        const stars = container.querySelectorAll('.star');
        let selectedStarValue = null;

        stars.forEach((star, index) => {
            star.addEventListener('mouseover', () => {
                const starIndex = index + 1;

                stars.forEach(star => star.classList.remove('over'));

                for (let i = 0; i < starIndex; i++) {
                    stars[i].classList.add('over');
                }
            });

            star.addEventListener('mouseout', () => {
                stars.forEach(star => star.classList.remove('over'));
            });

            star.addEventListener('click', () => {
                const starIndex = index + 1;

                stars.forEach(star => star.classList.remove('select', 'over'));

                for (let i = 0; i < starIndex; i++) {
                    stars[i].classList.add('select');
                }
                selectedStarValue = star.dataset.value;
            });
        });

        container.querySelector('#submitUpdateButton').addEventListener('click', () => {
            const commentText = container.querySelector('#commentUpdateTextArea').value;
            const reviewId = container.closest('.comment').dataset.reviewId;
            updateCommentAndRating(reviewId, selectedStarValue, commentText);
        });
    });

    // 댓글과 평점 생성
    function createCommentAndRating(rating, comment) {
        $.ajax({
            url: '/comment/save',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                rating: rating,
                review: comment,
                userId: userId,
                groupId: groupId
            }),
            success: function (data) {
                alert('댓글과 평점이 성공적으로 생성되었습니다.');
                window.location.reload(); // 페이지 새로 고침
            },
            error: function (xhr, status, error) {
                alert('댓글과 평점 생성 중 오류 발생.');
                console.error('Status:', status);
                console.error('Error:', error);
                console.error('Response:', xhr.responseText);
            }
        });
    }

    // 댓글과 평점 수정
    function updateCommentAndRating(reviewId, selectedStarValue, commentText) {
        $.ajax({
            url: '/comment/update',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({
                reviewId: reviewId,
                rating: selectedStarValue,
                review: commentText
            }),
            success: function (data) {
                alert('댓글과 평점이 성공적으로 수정되었습니다.');
                window.location.reload(); // 페이지 새로 고침
            },
            error: function (xhr, status, error) {
                alert('댓글과 평점 수정 중 오류 발생.');
                console.error('Status:', status);
                console.error('Error:', error);
                console.error('Response:', xhr.responseText);
            }
        });
    }

    // 댓글과 평점 삭제
    function deleteCommentAndRating(ratingId, reviewId) {
        const msg = '정말로 삭제하시겠습니까?'
        if(confirm(msg)) {
            alert('댓글이 성공적으로 삭제되었습니다.');

            $.ajax({
                url: '/comment/delete',
                type: 'DELETE',
                contentType: 'application/json',
                data: JSON.stringify({
                    ratingId: ratingId,
                    reviewId: reviewId
                }),
                success: function (data) {
                    window.location.reload(); // 페이지 새로 고침
                },
                error: function (xhr, status, error) {
                    alert('댓글과 평점 삭제 중 오류 발생.');
                    console.error('Status:', status);
                    console.error('Error:', error);
                    console.error('Response:', xhr.responseText);
                }
            });
        }
    }

    // 사용자 데이터 가져오기
    function userData(userId) {
        $.ajax({
            url: `/get/user/${userId}`,
            type: 'GET',
            dataType: 'json',
            success: (data) => {
                title.textContent = `${data.username}님의 일정`;
            },
            error: (xhr, status, error) => {
                console.error('데이터 가져오기 중 오류 발생:', error);
            }
        });
    }

    // 이벤트 데이터 가져오기
    function planData() {
        $.ajax({
            url: `/plan/get/event/${groupId}`,
            type: 'GET',
            dataType: 'json',
            success: (data) => {
                createCardsFromData(data);
            },
            error: (xhr, status, error) => {
                console.error('데이터 가져오기 중 오류 발생:', error);
            }
        });
    }

    // 카드 생성 함수
    function createCardsFromData(data) {
        data.forEach(item => {
            const newCard = createCardElement(item);
            cardContainer.appendChild(newCard);
            if (!isUser) {
                userData(item.userId);
                isUser = true;
            }
        });
    }

    // 카드 요소 생성 함수
    function createCardElement({ userId, category, title = '', description = '' }) {
        const newCard = document.createElement('div');
        newCard.className = 'card';
        newCard.dataset.id = userId;
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

        return newCard;
    }

    // 카테고리 번역 함수
    function getCategoryTranslation(category, toEnum = true) {
        const categoryMap = {
            '업무': 'WORK', '개인 개발': 'PERSONAL_DEVELOPMENT', '운동': 'EXERCISE',
            '여행': 'TRAVEL', '사회 활동': 'SOCIAL_ACTIVITY', '가족': 'FAMILY',
            '취미': 'HOBBY', '건강': 'HEALTH', '교육': 'EDUCATION',
            '쇼핑': 'SHOPPING', '집안일': 'HOUSEWORK', '금융': 'FINANCE',
            '오락': 'ENTERTAINMENT', '약속': 'APPOINTMENT', '기타': 'OTHER'
        };

        if (toEnum) {
            return categoryMap[category] || 'OTHER';
        }
        return Object.keys(categoryMap).find(key => categoryMap[key] === category) || '기타';
    }

    // 초기 데이터 로드
    planData();
});
