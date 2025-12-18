plugins {
    java // 자바 기능을 쓰겠다고 선언
}

repositories {
    mavenCentral() // 라이브러리를 가져올 창고 설정
}

dependencies {
    // 수동으로 다운로드했던 JUnit을 이제 여기서 자동으로 가져옵니다
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform() // 테스트 실행 시 JUnit 5를 사용하도록 설정
}