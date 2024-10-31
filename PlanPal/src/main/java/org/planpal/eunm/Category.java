package org.planpal.eunm;

public enum Category {
    WORK("업무"),
    PERSONAL_DEVELOPMENT("개인 개발"),
    EXERCISE("운동"),
    TRAVEL("여행"),
    SOCIAL_ACTIVITY("사회 활동"),
    FAMILY("가족"),
    HOBBY("취미"),
    HEALTH("건강"),
    EDUCATION("교육"),
    SHOPPING("쇼핑"),
    HOUSEWORK("집안일"),
    FINANCE("금융"),
    ENTERTAINMENT("오락"),
    APPOINTMENT("약속"),
    OTHER("기타");

    private final String displayName; // 카테고리 표시 이름

    // 생성자
    Category(String displayName) {
        this.displayName = displayName;
    }

    // 카테고리의 표시 이름을 반환하는 메소드
    public String getDisplayName() {
        return displayName;
    }

    // 표시 이름으로 카테고리를 찾는 메소드
    public static Category fromDisplayName(String displayName) {
        for (Category category : values()) {
            if (category.getDisplayName().equals(displayName)) {
                return category; // 찾은 카테고리 반환
            }
        }
        return OTHER; // 기본값으로 '기타' 반환
    }
}
