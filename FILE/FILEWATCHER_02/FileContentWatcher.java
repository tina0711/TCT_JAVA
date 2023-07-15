package test.com;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class FileContentWatcher {
    // 파일의 이전 크기를 저장하는 변수
    private static long previousSize = 0;

    public void fileWatcher() throws IOException, InterruptedException {
        // 감시할 파일 경로
        String filePath = "./LOGFILE_A.TXT";

        // WatchService 생성
        WatchService watchService = FileSystems.getDefault().newWatchService();

        // 파일을 감시할 WatchKey 생성
        Path file = Paths.get(filePath).getParent();
        file.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        // 감시 시작
        System.out.println("파일 내용 감지를 시작합니다...");
        while (true) {
            WatchKey key = watchService.take(); // 감시 이벤트를 기다림

            // WatchKey의 이벤트를 처리
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // 파일 수정 이벤트인 경우
                if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    // 수정된 파일 경로 가져오기
                    Path modifiedPath = ((WatchEvent<Path>) event).context();
                    Path modifiedFilePath = file.resolve(modifiedPath);
                    // 파일 경로를 기반으로 원하는 작업 수행
                    processFile(modifiedFilePath);
                }
            }

            // WatchKey를 리셋하여 감시 계속
            boolean valid = key.reset();
            if (!valid) {
                // 감시 중지
                break;
            }
        }
    }

    private static void processFile(Path filePath) {
        try {
            // 파일 크기 가져오기
        	
            long fileSize = Files.size(filePath);
            System.out.println("[fileSize]" + fileSize);
            // 파일의 내용이 추가된 경우에만 처리
            if (fileSize > previousSize) {
                // 추가된 내용의 크기 계산
                long addedSize = fileSize - previousSize;
                System.out.println("[previousSize]" + previousSize);

                // 파일의 마지막에서부터 추가된 내용만 읽어오기
                try (java.io.RandomAccessFile file = new java.io.RandomAccessFile(filePath.toString(), "r")) {
                    file.seek(previousSize); // 이전 크기까지 위치로 이동
                    byte[] addedContent = new byte[(int) addedSize];
                    file.readFully(addedContent);

                    // 추가된 내용 출력
                    String addedText = new String(addedContent);
                    System.out.println("파일 내용이 변경되었습니다: " + filePath.toString());
                    System.out.println("추가된 내용:");
                    System.out.println(addedText);
                }
            }

            // 이전 크기 업데이트
            previousSize = fileSize;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
