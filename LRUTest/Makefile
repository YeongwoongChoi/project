CC=gcc
INCLUDES=-IHashTable -IList
CFLAGS=-Wall -O2 $(INCLUDES)
SRCS=LRUCache/main.c List/list.c HashTable/hash.c
TARGET=main

all:
	$(CC) $(CFLAGS) $(SRCS) -o $(TARGET)

clean:
	rm -f $(TARGET)
