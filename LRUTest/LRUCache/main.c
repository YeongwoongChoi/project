#include <stdio.h>
#include <malloc.h>
#include <string.h>
#include "list.h"
#include "hash.h"

typedef struct {
	char* name;
	int age;
} Student;

typedef struct {
	int capacity;
	HashTable* t;			// char* -> Node*
	List* l;
	/* list of Node* (prev + next + CacheItem*)
	* (CacheItem = key + Student*)
	* */
} LRUCache;

typedef struct {
	char* key;
	void* value;
} CacheItem;

void* lru_get(LRUCache* c, char* key) {
	if (!c || !key)
		return NULL;
	Node* target = get(c->t, key);
	if (!target)
		return NULL;

	CacheItem* item = target->value;
	detach_node(c->l, target);
	push_node(c->l, target);
	
	return item->value;
}

void lru_put(LRUCache* c, char* key, void* value) {
	if (!c || !key)
		return;

	Node* target = get(c->t, key);
	if (target) {
		CacheItem* item = target->value;
		item->value = value;
		detach_node(c->l, target);
		push_node(c->l, target);
	}
	else {
		if (c->l->size >= c->capacity) {
			Node* oldest = c->l->tail;
			CacheItem* oldest_item = oldest->value;
			remove_key(c->t, oldest_item->key);
			pop_back(c->l);
		}
		CacheItem* newest_item = (CacheItem*)calloc(1, sizeof(CacheItem));
		newest_item->key = strdup(key);
		newest_item->value = value;
		Node* newest = push_front(c->l, newest_item);
		put(c->t, key, newest);
	}
}

LRUCache* init_cache() {
	LRUCache* cache = (LRUCache*)calloc(1, sizeof(LRUCache));
	if (!cache) {
		fprintf(stderr, "Couldn't create cache.\n");
		return NULL;
	}
	cache->l = create_list();
	cache->t = init_table(20);
	cache->capacity = 3;
	return cache;
}

Student* create_student(char* name, int age) {
    Student* s = (Student*)calloc(1, sizeof(Student));
    s->name = strdup(name);
    s->age = age;
    return s;
}

Student* test_data[5];
char* keys[5] = { "20240001", "20240002", "20240003", "20240004", "20240005" };

void setup_test_data() {
    test_data[0] = create_student("Kim Min-su", 20);
    test_data[1] = create_student("Lee Young-hee", 21);
    test_data[2] = create_student("Park Chul-su", 22);
    test_data[3] = create_student("Choi Ji-won", 23);
    test_data[4] = create_student("Jung Eui-jin", 24);
}

void print_list(List* l) {
	Node* curr = l->head;
	while (curr) {
		CacheItem* item = curr->value;
		Student* s = item->value;
		printf("\nKey: %s, Student{name=%s, age=%d}", item->key, s->name, s->age);
		curr = curr->next;
	}
	printf("\n");
}

int main() {
	LRUCache* cache = init_cache();
	setup_test_data();
	if (!cache)
		return 1;

	printf("--- Scenario 1: Fill Cache (Capacity 3) ---\n");
	for (int i = 0; i < 3; i++)
		lru_put(cache, keys[i], test_data[i]);

	print_list(cache->l);

	printf("\n--- Scenario 2: Cache Hit (Move to Front) ---\n");
	lru_get(cache, keys[0]);
	printf("Get: %s (Now this is MRU)\n", keys[0]);

	print_list(cache->l);

	printf("\n--- Scenario 3: Eviction (LRU Removal) ---\n");
	lru_put(cache, keys[3], test_data[3]);
	printf("Put: %s (Evicts %s)\n", keys[3], keys[1]);

	print_list(cache->l);

	printf("\n--- Scenario 4: Verify Eviction ---\n");
	if (!lru_get(cache, keys[1]))
		printf("Success: %s was correctly evicted.\n", keys[1]);
	else
		printf("Fail: %s still exists in cache.\n", keys[1]);

	print_list(cache->l);
	destruct_list(cache->l, free);
	destruct_table(cache->t, NULL);

	free(cache);

	return 0;
}
