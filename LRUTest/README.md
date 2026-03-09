# LRUTest
# 개요
- 이 프로그램은 LRU (Least Recently Used) 캐시 알고리즘의 간단한 구현 및 테스트 용도의 프로그램이다.
- C로 구현된 프로그램으로 이중 연결 리스트 (Doubly LinkedList)와 해시테이블 (Hash table) 자료구조를 활용한다.
        
## 빌드 방법
### 디렉토리 구조
```bash
├── HashTable
│   ├── hash.h
│   └── hash.c
├── List
│   ├── list.h
│   ├── list.c
│   └── main.c     # LinkedList 동작 확인용 코드
├── LRUCache
│   └── main.c
├── main
└── Makefile
```

- `Makefile`을 이용하여 빌드하는데, 디렉토리에서 다음과 같은 명령어를 작성한다.
  
  ```bash
  make
  ```

- 빌드 시 `gcc`, `make`가 필요하므로, 오류가 나거나 설치가 안되어 있다면 설치한다.
  - 설치 방법
      ```bash
      # gcc, g++, make를 한 번에 설치한다.
      sudo apt install build-essential
      ```
  - 정상 설치 여부는 다음 명령어를 통하여 확인한다.
      ```bash
      gcc --version
      make --version
      ```

---
