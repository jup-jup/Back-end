package com.jupjup.www.jupjup.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;

//	•	0: 초 (0초)
//	•	0: 분 (0분)
//	•	3: 시간 (오전 3시)
//	•	*: 일 (매일)
//	•	*: 월 (매월)
//	•	?: 요일 (무시)

@Component
@Slf4j
public class LoggingScheduler {
    // 로그 파일이 저장된 디렉토리 경로
    private static final String LOG_DIRECTORY = "logs/logfile.log";
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void removeLog() {
        try {
            Files.walkFileTree(Paths.get(LOG_DIRECTORY), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    LocalDateTime fileTime = LocalDateTime.ofInstant(attrs.lastModifiedTime().toInstant(), ZoneId.systemDefault());
                    LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);

                    if (fileTime.isBefore(oneDayAgo)) {
                        Files.delete(file);
                        System.out.println("Deleted log file: " + file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
